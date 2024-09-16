import java.util.Arrays;
import java.util.Scanner;

public class Alignment {
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
        System.out.println("Computing edit distance for lengths: " + sLen + " and " + tLen);
        int[][] table = new int[sLen + 1][tLen + 1];

        for (int i = 0; i <= sLen; i++)
        {
            table[i][0] = i; 
        }

        for (int j = 0; j <= tLen; j++)
        {
            table[0][j] = j; 
        }

        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                int dist = 1 + Math.min(table[i][j-1], table[i-1][j]); // insertion / deletion
                table[i][j] = Math.min(dist, table[i-1][j-1] + ((s.charAt(i-1) == t.charAt(j-1))? 0: 1));
            }
        }

        /*for (int[] arr: table)
        {
            System.out.println(Arrays.toString(arr));
        }*/

        System.out.println(table[sLen][tLen]);
        StringBuilder sbS = new StringBuilder(), sbT = new StringBuilder();
        int i = sLen, j = tLen;
        while (i > 0 || j > 0)
        {
            if (j > 0 && table[i][j] == 1 + table[i][j-1])
            {
                sbT.append(t.charAt(j-1));
                sbS.append('-');
                j--;

            }
            else if (i > 0 && table[i][j] == 1 + table[i-1][j])
            {
                sbS.append(s.charAt(i-1));
                sbT.append('-');
                i--;
            }
            else
            {
                sbS.append(s.charAt(i-1));
                sbT.append(t.charAt(j-1));
                i--;
                j--;
            }

        }

        System.out.println(sbS.reverse().toString());
        System.out.println(sbT.reverse().toString());
    }
}
