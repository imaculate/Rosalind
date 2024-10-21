import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Ksim {
    // java -Xmx6g Ksim
    // On cloudVM java -Xmx256g  Ksim < rosalind_ksim.txt > test1.txt
    public static int MAX_SCORE = 1000000, batchSize = 20, k, sLen, tLen;
    public static String s, t;

    static class ScoreThread implements Runnable {
        int startIdx;
        public ScoreThread(int startIdx)
        {
            this.startIdx = startIdx;
        }

        public void run()
        {
            try
            {
                work();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void work()
        {
            // System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            int lastIdx = Math.min(startIdx + sLen + k + 1, tLen);

            String sub = t.substring(startIdx, lastIdx);
            int subLen = sub.length();

            int idx = subLen;

            if (subLen < sLen)
            {
                int[][] scores = editDistanceKband(s, sub, k);
                
                while (scores[sLen][idx] != MAX_SCORE)
                {
                    if (scores[sLen][idx] <= k)
                    {
                        System.out.println(startIdx + 1 +  " " + idx);
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
                        System.out.println(startIdx + 1 +  " " + idx);
                    }
                    idx --;
                }
            }
        }
    }

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        k = scanner.nextInt();
        // System.out.println("Bandwith is: "+ k);
        s = scanner.next();
        // System.out.println("Motif is: "+ s);
        t = scanner.next();
        // System.out.println("Sequence is: "+ t);
        scanner.close();

        sLen = s.length();
        tLen = t.length();
        int maxS = tLen - sLen + k;  
        int numThreads = maxS + 1;
        System.out.println("Processing: " + numThreads + " threads");
        int numBatches = Math.ceilDiv(numThreads, batchSize);
        Thread[] threads = new Thread[batchSize];
        for (int b = 0; b < numBatches; b++)
        {
            int start = b * batchSize;
            int end = Math.min(numThreads, start + batchSize);

            // System.out.println("Starting batch: " + b);
            for (int i = start; i < end; i++)
            {
                threads[i - start]
                = new Thread(new ScoreThread(i));
                threads[i- start].start();
            }
            System.out.println("Waiting for batch: " + b);
            for (int i = start; i < end; i++)
            {
                try {             
                    threads[i -start].join(); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Done");
    }

    public static int[][] editDistanceKband(String s1, String s2, int bandwidth)
    {
        int l1 = s1.length(), l2 = s2.length();
        int[][] scores = new int[l1 + 1][l2 + 1];
        int diff = l1 - l2;

        for (int i = 0; i <= l1; i ++)
        {
            Arrays.fill(scores[i], MAX_SCORE);
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
        return Math.abs(start - end) <= k;
    }

}
