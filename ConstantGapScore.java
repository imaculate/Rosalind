import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConstantGapScore {
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

        for (int i = 0; i <= sLen; i++)
        {
            M[i][0] = -32000;
            del[i][0] = -32000;
            in[i][0] = -5;
        }

        for (int j = 0; j <= tLen; j++)
        {
            M[0][j] = -32000;
            in[0][j] = -32000;
            del[0][j] = -5;
        }

        M[0][0] = 0;
        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                in[i][j] = Math.max(in[i-1][j] , Math.max(M[i-1][j], del[i-1][j]) - 5);
                del[i][j] = Math.max(del[i][j-1] , Math.max(M[i][j-1], in[i][j-1]) - 5);
                M[i][j] = blosum62[charToIndex.get(s.charAt(i-1))][charToIndex.get(t.charAt(j-1))] + Math.max(M[i-1][j-1], Math.max(in[i-1][j-1], del[i-1][j-1]));

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
        
        System.out.println("The score is: " + Math.max(M[sLen][tLen], Math.max(in[sLen][tLen], del[sLen][tLen])));
    }

}
