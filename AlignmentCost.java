import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlignmentCost {
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

        System.out.println("Computing scores");
        int[][] scores = globalScore(s, t);
        int[][] revScores = globalScore(new StringBuilder(s).reverse().toString(), new StringBuilder(t).reverse().toString());

        int sum = 0;
        int sLen = s.length(), tLen = t.length();
        for (int i = 0; i < sLen; i++)
        {
            for (int j = 0; j < tLen; j++)
            {
                sum += scores[i][j] + revScores[i][j] + ((s.charAt(i) == t.charAt(j))? 1: -1);
            }
        }

        System.out.println(scores[sLen][tLen]);
        System.out.println(sum);
    }

    public static int[][] globalScore(String s, String t)
    {
        int sLen = s.length(), tLen = t.length();
        int[][] scores = new int[sLen + 1][tLen + 1];
        

        for (int i = 0; i <= sLen; i++)
        {
            scores[i][0] = -i;
        }

        for (int j = 1; j <= tLen; j++)
        {
            scores[0][j] = -j;
        }

        List<Integer> options = null;
        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                options = new ArrayList<Integer>(List.of(scores[i-1][j-1] + ((s.charAt(i-1) == t.charAt(j-1))? 1: -1), scores[i-1][j] - 1, scores[i][j-1] - 1));
                scores[i][j] = Collections.max(options);
            }
        }

        return scores;
    }
}
