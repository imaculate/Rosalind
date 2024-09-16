import java.util.Scanner;

public class MaxGap {
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
        System.out.println("Computing longest common subsequence for lengths: " + sLen + " and " + tLen);
        int[][] table = new int[sLen + 1][tLen + 1];

        /*for (int i = 0; i <= sLen; i++)
        {
            table[i][0] = i; 
        }

        for (int j = 0; j <= tLen; j++)
        {
            table[0][j] = j; 
        }*/

        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                if (s.charAt(i-1) == t.charAt(j-1))
                {
                    table[i][j] = 1 + table[i-1][j-1];
                }
                else
                {
                    table[i][j] = Math.max(table[i][j-1], table[i-1][j]);
                }
            }
        }

        System.out.println(sLen + tLen -2 * table[sLen][tLen]);

        /*for (int[] arr: table)
        {
            System.out.println(Arrays.toString(arr));
        }*/

        // So: max #Gaps = |seq1| + |seq2| - 2*|longest subsequence|
    }
}
