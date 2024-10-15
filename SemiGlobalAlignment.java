import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SemiGlobalAlignment {
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
        System.out.println("Computing score and alignment for lengths: " + sLen + " and " + tLen);
        int[][] table = new int[sLen + 1][tLen + 1], dirs = new int[sLen + 1][tLen + 1];
        List<Integer> options = null;

        // no init since prefix gaps are cheap

        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                options = new ArrayList<Integer>(List.of(table[i-1][j] - 1, table[i][j-1] - 1, table[i-1][j-1] + ((s.charAt(i-1) == t.charAt(j-1))? 1: -1)));
                table[i][j] = Collections.max(options);
                dirs[i][j] = options.indexOf(table[i][j]);
            }
        }

        System.out.println("Completed computing table");
        /*for (int[] arr: table)
        {
            System.out.println(Arrays.toString(arr));
        }*/


        // prune out suffix gaps
        int i = 0, j = 0, lastColMax = 0, lastColMaxIndex = 0, lastRowMax = 0, lastRowMaxIndex = 0;
        for (i = sLen; i >=0; i--)
        {
            if (table[i][tLen] > lastColMax)
            {
                lastColMax = table[i][tLen];
                lastColMaxIndex = i;
            }
        }

        for (j = tLen; j >= 0; j--)
        {
            if (table[sLen][j] > lastRowMax)
            {
                lastRowMax = table[sLen][j];
                lastRowMaxIndex = j;
            }
        }

        if (lastRowMax > lastColMax)
        {
            i = sLen;
            j = lastRowMaxIndex;
        }
        else
        {
            i = lastColMaxIndex;
            j = tLen;
        }

        int maxScore = table[i][j];
        System.out.println(maxScore);
        StringBuilder sbS = new StringBuilder(s), sbT = new StringBuilder(t);  

        // Append the suffix gaps
        for (int x = i; x < sLen; x++)
        {
            sbT.append('-');
        }

        for (int x = j; x < tLen; x++)
        {
            sbS.append('-');
        }


        //backtrack from the highest score

        int dir = dirs[i][j];
        while (i*j != 0)
        {
            if (dir == 0)
            {
                sbT.insert(j, '-');
                i--;
            }
            else if (dir == 1)
            {
                sbS.insert(i, '-');
                j--;
            }
            else
            {
                i--;
                j--;
            }
            dir = dirs[i][j];
        }

        System.out.println("Prefix gaps from "+ i + " and " + j);

        // Append the prefix gaps
        for (int x = 0; x < i; x++)
        {
            sbT.insert(0, '-');
        }

        for (int x = 0; x < j; x++)
        {
            sbS.insert(0, '-');
        }

        System.out.println(sbS.toString());
        System.out.println(sbT.toString());
    }
}
