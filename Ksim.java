import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ksim {
    public static int MAX_SCORE = 1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        // System.out.println("Bandwith is: "+ k);
        String s = scanner.next();
        // System.out.println("Motif is: "+ s);
        String t = scanner.next();
        // System.out.println("Sequence is: "+ t);
        scanner.close();


        int sLen = s.length(), tLen = t.length();
        //System.out.println("Computing substrings of " + t + " at most " + k + " different from " + s);

        int lastIdx = 0;
        String sub = "";
        int subLen = 0;

        int maxS = tLen - sLen + k;
        
        for (int i = 0; i <= maxS; i++)
        {
            lastIdx = Math.min(i + sLen + k + 1, tLen);

            sub = t.substring(i, lastIdx);
            subLen = sub.length();

            int idx = subLen;

            if (subLen < sLen)
            {
                int[][] scores = editDistanceKband(s, sub, k);
                
                while (scores[sLen][idx] != MAX_SCORE)
                {
                    if (scores[sLen][idx] <= k)
                    {
                        System.out.println( i + 1 +  " " + idx);
                    }
                    idx--;
                }
            }
            else
            {
                int[][] scores = editDistanceKband(sub, s, k);
                while (scores[idx][sLen] != MAX_SCORE)
                {
                    if (scores[idx][sLen] <= k)
                    {
                        System.out.println(i + 1 +  " " + idx);
                    }
                    idx --;
                }
            }
        }
    }

    public static int[][] editDistanceKband(String s1, String s2, int bandwidth)
    {
        int l1 = s1.length(), l2 = s2.length();
        int[][] scores = new int[l1 + 1][l2 + 1];
        int diff = l1 - l2;

        for (int i = 0; i <= l1; i ++)
        {
            for (int j = 0; j <= l2; j ++)
            {
                scores[i][j] = MAX_SCORE;
            }
        }

        for (int i = 0; i <= bandwidth + diff; i++)
        {
            scores[i][0] = i;
        }

        for (int j = 1; j <= bandwidth; j++)
        {
            scores[0][j] = j;
        }
	
        for (int i = 1; i <= l1; i++)
        {
            for (int h = - bandwidth - diff; h <= bandwidth; h++) {
                int j = i + h;

                if (j >= 1 && j <= l2)
                {
                    scores[i][j] = scores[i - 1][j -1] + (s1.charAt(i-1) == s2.charAt(j-1) ? 0 : 1);

                    if (insideBand(i - 1, j, bandwidth + diff))
                    {
                        int tmp = scores[i - 1][j] + 1;
                        if (scores[i][j] > tmp)
                        {
                            scores[i][j] = tmp;
                        }
                    }

                    if (insideBand(i, j - 1, bandwidth))
                    {
                        int tmp = scores[i][j - 1] + 1;
                        if (scores[i][j] > tmp)
                        {
                            scores[i][j] = tmp;
                        }
                    }
                }
            }
        }
        return scores;
    }

    public static boolean insideBand(int start, int end, int k)
    {
        return (start - end >= -k) && (start - end <= k);
    }

}
