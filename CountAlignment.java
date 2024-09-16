import java.util.Arrays;
import java.util.Scanner;

public class CountAlignment {
    static int MODULO = 134217727;

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
        int[][] counts = new int[sLen + 1][tLen + 1];

        for (int i = 0; i <= sLen; i++)
        {
            table[i][0] = i;
            counts[i][0] = 1; 
        }

        for (int j = 0; j <= tLen; j++)
        {
            table[0][j] = j;
            counts[0][j] = 1; 
        }

        int localCount = 0, localMin = Integer.MAX_VALUE;
        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                localCount = counts[i-1][j-1];
                localMin = table[i-1][j-1] + (s.charAt(i-1) == t.charAt(j-1) ? 0 : 1);

    
                if (1 + table[i-1][j] <= localMin)
                {
                    localCount = (1 + table[i-1][j] == localMin) ? (localCount + counts[i-1][j]) : counts[i-1][j];
                    localMin = 1 + table[i-1][j];
                }

                if (1 + table[i][j-1] <= localMin)
                {
                    localCount = (1 + table[i][j-1] == localMin) ? (localCount + counts[i][j-1]) : counts[i][j-1];
                    localMin = 1 + table[i][j-1];
                }

                table[i][j] = localMin;
                counts[i][j] = (counts[i][j] + localCount) % MODULO;
            }
        }

        /*System.out.println("Distances: ");
        for (int[] arr: table)
        {
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("Counts: ");
        for (int[] arr: counts)
        {
            System.out.println(Arrays.toString(arr));
        }*/

        System.out.println("The edit distance is: " + table[sLen][tLen]);
        System.out.println("The optimal alignment count is: " + counts[sLen][tLen]);

    }

}
