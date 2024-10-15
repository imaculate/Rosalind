import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LocalAlignmentAffine {
    static int GAP_START = 11;
    static int GAP_EXTEND = 1;
    static int[][] blosum62 = new int[][]
    {{4,  0, -2, -1, -2,  0, -2, -1, -1, -1, -1, -2, -1, -1, -1,  1,  0,  0, -3, -2},
    {0,  9, -3, -4, -2, -3, -3, -1, -3, -1, -1, -3, -3, -3, -3, -1, -1, -1, -2, -2},
    {-2, -3,  6,  2, -3, -1, -1, -3, -1, -4, -3,  1, -1,  0, -2,  0, -1, -3, -4, -3},
    {-1, -4,  2,  5, -3, -2,  0, -3,  1, -3, -2,  0, -1,  2,  0,  0, -1, -2, -3, -2},
    {-2, -2, -3, -3,  6, -3, -1,  0, -3,  0,  0, -3, -4, -3, -3, -2, -2, -1,  1,  3},
    { 0, -3, -1, -2, -3,  6, -2, -4, -2, -4, -3,  0, -2, -2, -2,  0, -2, -3, -2, -3},
    {-2, -3, -1,  0, -1, -2,  8, -3, -1, -3, -2,  1, -2,  0,  0, -1, -2, -3, -2,  2},
    {-1, -1, -3, -3,  0, -4, -3,  4, -3,  2,  1, -3, -3, -3, -3, -2, -1,  3, -3, -1},
    {-1, -3, -1,  1, -3, -2, -1, -3,  5, -2, -1,  0, -1,  1,  2,  0, -1, -2, -3, -2},
    {-1, -1, -4, -3,  0, -4, -3,  2, -2,  4,  2, -3, -3, -2, -2, -2, -1,  1, -2, -1},
    { -1, -1, -3, -2,  0, -3, -2,  1, -1,  2,  5, -2, -2,  0, -1, -1, -1,  1, -1, -1},
    {-2, -3,  1,  0, -3, 0,  1, -3,  0, -3, -2,  6, -2,  0,  0,  1,  0, -3, -4, -2},
    {-1, -3, -1, -1, -4, -2, -2, -3, -1, -3, -2, -2,  7, -1, -2, -1, -1, -2, -4, -3},
    {-1, -3, 0,  2, -3, -2,  0, -3,  1, -2,  0, 0, -1,  5, 1,  0, -1, -2, -2, -1},
    {-1, -3, -2,  0, -3, -2,  0, -3,  2, -2, -1,  0, -2,  1,  5, -1, -1, -3, -3, -2},
    { 1, -1,  0, 0, -2,  0, -1, -2,  0, -2, -1,  1, -1,  0, -1,  4,  1, -2, -3, -2},
    {0, -1, -1, -1, -2, -2, -2, -1, -1, -1, -1,  0, -1, -1, -1,  1,  5,  0, -2, -2},
    {0, -1, -3, -2, -1, -3, -3,  3, -2,  1,  1, -3, -2, -2, -3, -2,  0,  4, -3, -1},
    {-3, -2, -4, -3,  1, -2, -2, -3, -3, -2, -1, -4, -4, -2, -3, -3, -2, -3, 11,  2},
    {-2, -2, -3, -2,  3, -3,  2, -1, -2, -1, -1, -2, -3, -1, -2, -2, -2, -1,  2,  7}
    };

    static Map<Character, Integer> charToIndex = new HashMap<Character, Integer>()
    {{
        put('A', 0);
        put('C', 1);
        put('D', 2);
        put('E', 3);
        put('F', 4);
        put('G', 5);
        put('H', 6);
        put('I', 7);
        put('K', 8);
        put('L', 9);
        put('M', 10);
        put('N', 11);
        put('P', 12);
        put('Q', 13);
        put('R', 14);
        put('S', 15);
        put('T', 16);
        put('V', 17);
        put('W', 18);
        put('Y', 19);
    }};

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // initial label
        StringBuilder sb = new StringBuilder();
        String line = "", s = "", t = "";

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if (line.startsWith(">"))
            {
                s = sb.toString();
                sb.setLength(0);
            }
            else
            {
                sb.append(line);
            }
        }

        t = sb.toString();
        scanner.close();

        int sLen = s.length(), tLen = t.length();
        int[][] M = new int[sLen + 1][tLen + 1], in = new int[sLen + 1][tLen + 1], del = new int[sLen + 1][tLen + 1];
        int[][][] dirs = new int[3][sLen + 1][tLen + 1]; // M, in, del


        // no initialization because a starting gap can  be leftout of the substring
        M[0][0] = 0;
        in[0][0] = -32000;
        del[0][0] = -32000;
        List<Integer> options = null;
        int maxScore = -1, maxI = 0, maxJ = 0, maxOp = 0;

        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                int subScore = blosum62[charToIndex.get(s.charAt(i-1))][charToIndex.get(t.charAt(j-1))];
                options = new ArrayList<>(List.of(M[i-1][j-1] + subScore, in[i-1][j-1] + subScore, del[i-1][j-1] + subScore, 0));
                M[i][j] = Collections.max(options);
                dirs[0][i][j] = options.indexOf(M[i][j]);

                options = new ArrayList<>(List.of(M[i-1][j] - GAP_START, in[i-1][j] - GAP_EXTEND, del[i-1][j] - GAP_START, 0));
                in[i][j] = Collections.max(options);
                dirs[1][i][j] = options.indexOf(in[i][j]);

        
                options = new ArrayList<>(List.of(M[i][j-1] - GAP_START, in[i][j-1] - GAP_START, del[i][j-1] - GAP_EXTEND, 0));
                del[i][j] = Collections.max(options);
                dirs[2][i][j] = options.indexOf(del[i][j]);

                options = new ArrayList<>(List.of(M[i][j], in[i][j], del[i][j], 0));
                int currMax = Collections.max(options);
                if (currMax > maxScore)
                {
                    maxScore = currMax;
                    maxI = i;
                    maxJ = j;
                    maxOp = options.indexOf(currMax);
                }
            }
        }

        /*System.out.println("Matching Scores: ");
        for (int[] arr: M)
        {
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("Insert scores: ");
        for (int[] arr: in)
        {
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("Delete scores: ");
        for (int[] arr: del)
        {
            System.out.println(Arrays.toString(arr));
        }*/
        
        
        System.out.println("The score is: " + maxScore);

        int i = maxI, j = maxJ, currOp = maxOp, dir = dirs[currOp][i][j];
        while ((i * j > 0) && (dir != 3))
        {
            if (dir == 0)
            {
                options = new ArrayList<>(List.of(M[i-1][j-1], in[i-1][j-1], del[i-1][j-1], 0));
                i--;
                j--;

            }
            else if (dir == 1)
            {
                options = new ArrayList<>(List.of(M[i-1][j] - GAP_START, in[i-1][j] - GAP_EXTEND, del[i-1][j] - GAP_START, 0));
                i--;
            }
            else
            {
                options = new ArrayList<>(List.of(M[i][j-1] - GAP_START, in[i][j-1] - GAP_START, del[i][j-1] - GAP_EXTEND, 0));
                j--;
            }

            currOp = options.indexOf(Collections.max(options));
            dir = dirs[currOp][i][j];
        }

        System.out.println(s.substring(i, maxI));
        System.out.println(t.substring(j, maxJ));
    }

}
