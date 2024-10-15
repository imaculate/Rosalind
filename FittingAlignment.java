import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FittingAlignment {
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
        int[][] scores = new int[sLen + 1][tLen + 1], dirs = new int[sLen + 1][tLen + 1];
        
        // no col or row init to maximize prefix alignments


        List<Integer> options = null;

        // no init since prefix gaps are cheap

        int maxScore = -32000, maxI = 0, maxJ = 0;
        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                options = new ArrayList<Integer>(List.of(scores[i-1][j-1] + ((s.charAt(i-1) == t.charAt(j-1))? 1: -1), scores[i-1][j] - 1, scores[i][j-1] - 1));
                scores[i][j] = Collections.max(options);
                dirs[i][j] = options.indexOf(scores[i][j]);

                // only max of last row
                if ((j == tLen) && (scores[i][j] > maxScore))
                {
                    maxScore = scores[i][j];
                    maxI = i;
                    maxJ = j;
                }

            }
        }

        System.out.println("Completed computing scores");
        /*for (int[] arr: scores)
        {
            System.out.println(Arrays.toString(arr));
        }*/


        // trim the prefix gaps
        int i = maxI, j = maxJ;
        System.out.println(maxScore);
        StringBuilder sbS = new StringBuilder(s.substring(0, i)), sbT = new StringBuilder(t);


        //backtrack from the highest score
        int dir = dirs[i][j];
        while (i*j != 0)
        {
            if (dir == 1)
            {
                sbT.insert(j, '-');
                i--;
            }
            else if (dir == 2)
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
        while (j > 0)
        {
            sbS.insert(i, '-');
            j--;
        }

        // Append the prefix gaps

        System.out.println(sbS.substring(i, sbS.length()).toString());
        System.out.println(sbT.substring(j, sbT.length()).toString());
    }
}
