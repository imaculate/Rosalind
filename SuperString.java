import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SuperString {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String line = "";
        List<String> in = new ArrayList<>();
        while (scanner.hasNextLine()) {
            while (scanner.hasNextLine()) 
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }

            if (!sb.isEmpty()) { in.add(sb.toString()); }
            sb.setLength(0);
        }

        scanner.close();

        System.out.println(superSet(in));

    }



    public static String superSet(List<String> dnaList)
    {
        int N = dnaList.size();
        int[][] shifts = new int[N][];
        for (int i = 0; i < N; i++)
        {
            String dna = dnaList.get(i);
            shifts[i] = buildKmpShift(dna);
        }

        int[][] matrix = overlapMatrix(dnaList, shifts);
    
        List<Integer> order = topologicalSort(matrix);
        System.out.println("Topo order is: " + order);
        StringBuilder sb = new StringBuilder();
        sb.append(dnaList.get(order.get(N-1)));
        int prev = 0, curr = 0, overlap = 0;
        // reverse order
        for (int i = N-2; i >= 0; i--)
        {
            prev = order.get(i+1);
            curr = order.get(i);
            overlap = matrix[prev][curr];
            //System.out.println("DNA at " + prev + " and DNA at " + curr + " have overlap of " + overlap);
            sb.append(dnaList.get(curr).substring(overlap));
        }

        return sb.toString();
    }

    public static int[][] overlapMatrix(List<String> dnaList, int[][] shifts)
    {
        int N = shifts.length;
        int[][] matrix = new int[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (i == j) continue;
                String d1 = dnaList.get(i);
                String d2 = dnaList.get(j);

                int overlap = kmpOverlap(d1, d2, shifts[j]);
                if (overlap > d1.length()/2 || overlap > d2.length()/2)
                {
                    matrix[i][j] = overlap;
                }
            }
        }

        /*System.out.println("Overlap matrix:");
        for (int[] sub: matrix)
        {
            System.out.println(Arrays.toString(sub));
        }*/

        return matrix;
    }

    public static int kmpOverlap(String s1, String s2, int[] rightShifts)
    {
        int i = 0, j = 0, shift = 0, N1 = s1.length();
        while (i < N1)
        {
            if (j == s2.length())
            {
                shift += rightShifts[j - 1];
                j -= rightShifts[j - 1];
            }
            else if (s1.charAt(i) == s2.charAt(j))
            {
                i++;
                j++;
            }
            else if (j == 0)
            {
                i++;
                shift++;
            }
            else
            {
                shift += rightShifts[j - 1];
                j -= rightShifts[j - 1];
            }
        }
        return N1 - shift;
    }

    public static int[] buildKmpShift(String s)
    {
        int N = s.length();
        int[] z = buildkmpZ(s), shifts = new int[N];
        // consider using z matrix
        for (int i = 0; i< N; i++)
        {
            shifts[i] = i+1;
        }

        for (int i = 1; i< N; i++)
        {
            shifts[i + z[i] - 1] = Math.min(i, shifts[i+ z[i] -1]);
        }
        return shifts;

    }


    // Not applicable here:
    public static int[] buildkmpPrefix(String s)
    {
        int N = s.length();
        int[] result = new int[N];
        for (int i = 1; i < N; i++)
        {
            int j = result[i-1];
            while (j > 0 && s.charAt(i) != s.charAt(j))
            {
                j = result[j-1];
            }
            if (s.charAt(i) == s.charAt(j)) j++;
            result[i] = j;
        }
        System.out.println("KMPrefix array is: " + Arrays.toString(result));
        return result;
    }

    public static int[] buildkmpZ(String s)
    {
        int N = s.length();
        int[] z = new int[N];
        int left = 0, right = 0, length = 0;
        for (int i = 1; i < N; i++)
        {
            length = 0;
            if (i < right)
            {
                /*
                # Being a z-box, we have:
                # pattern[l, r) = pattern[0, r - l)
                #
                # Since l < i < r, we have:
                # pattern[i, r) = pattern[i - l, r - l)
                #
                # We have already computed z[i - l], so we have
                # pattern[i - l, i - l + z[i - l]) = pattern[0, z[i - l])
                #
                # Therefore we can conclude the below, as a starting point before the search begins
                */
                length = Math.min(z[i - left], right - i);
            }

            // Extend z-box to the right
            while ((i + length < N) && (s.charAt(i + length) == s.charAt(length)))
            {
                length++;
            }

            z[i] = length;
            if (i + length > right)
            {
                left = i;
                right = i + length;
            }
        }
        System.out.println("KMpz array is: " + Arrays.toString(z));
        return z;
    }


    public static List<Integer> topologicalSort(int[][] matrix)
    {
        int N = matrix.length;
        boolean[] visited = new boolean[N];
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < N; i++)
        {
            dfs(i, matrix, visited, order);
        }
        return order;
    }

    public static void dfs(int i, int[][] matrix, boolean[] visited, List<Integer> order)
    {
        if (visited[i]) return;

        visited[i] = true;
        for (int j = 0; j < matrix.length; j++)
        {
            if (matrix[i][j] != 0 && !visited[j])
            {
                dfs(j, matrix, visited, order);
            }
        }

        order.add(i);
    }
}