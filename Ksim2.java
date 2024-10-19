import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
// java -Xmx2g -Xss1g Ksim2

// Here we use the fact that if two strings of length n match with at most d
// mismatches, then they must share a k-mer of length k = [n/(d + 1)]
public class Ksim2 {
    public static int MAX_SCORE = 1000000, batchSize = 10, k, sLen, tLen;
    public static String s, t;

    static class Triad
    {
        int i, j, score;
        public Triad(int i, int j, int score)
        {
            this.i = i;
            this.j = j;
            this.score = score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triad other = (Triad) o;
            return (i == other.i) && (j == other.j) && (score == other.score);
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j, score);
        }
    }

    static class ScoreThread implements Runnable {
        int sStart, sEnd, tStart, tEnd;
        Set<Triad> visited = new HashSet<Triad>();
        public ScoreThread(int[] seed)
        {
            this.sStart = seed[0];
            this.sEnd = seed[1];
            this.tStart = seed[2];
            this.tEnd = seed[3];
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
            visited.clear();
            // System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            System.out.println("Extending forward");
            List<Triad> fwds = new ArrayList<Triad>();
            extendFwd(sEnd - 1, tEnd - 1, 0, fwds);
            if (fwds.isEmpty()) return;

            visited.clear();
            System.out.println("Extending backwards");
            List<Triad> revs = new ArrayList<Triad>();
            extendBack(sStart, tStart, 0, revs);
            if (revs.isEmpty()) return;


            for(Triad rev: revs)
            {
                for(Triad fwd : fwds)
                {
                    if(fwd.score + rev.score <= k)
                    {
                        System.out.println((rev.j + 1) + " "+ (fwd.j - rev.j + 1));
                    }
                }
            }
        
            
        }

        public void extendFwd(int i, int j, int score, List<Triad> result)
        {
            Triad tri = new Triad(i, j, score);
            if (visited.contains(tri)) return;
            visited.add(tri);
            
            if(score > k) return;

            if(i == sLen - 1)
            {
                result.add(tri);
            }
            if (i + 1 < sLen)
            {
                extendFwd(i+1, j, score + 1, result);
            }

            if (j + 1 < tLen)
            {
                extendFwd(i, j+1, score+1, result);
            }

            if ((i + 1 < sLen) && (j + 1 < tLen))
            {
                extendFwd(i+1, j+1, score + ((s.charAt(i+1) == t.charAt(j+1)) ? 0 : 1), result);
            }
        }

        public void extendBack(int i, int j, int score, List<Triad> result)
        {
            Triad tri = new Triad(i, j, score);
            if (visited.contains(tri)) return;
            visited.add(tri);
            
            if(score > k) return;

            if(i == 0)
            {
                result.add(tri);
            }
            if (i - 1 >= 0)
            {
                extendBack(i-1, j, score + 1, result);
            }

            if (j - 1 >= 0)
            {
                extendBack(i, j-1, score+1, result);
            }

            if ((i - 1 >= 0) && (j - 1 >= 0))
            {
                extendFwd(i-1, j-1, score + ((s.charAt(i-1) == t.charAt(j-1)) ? 0 : 1), result);
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

        int seedSize = Math.floorDiv(sLen, k+1);
        List<int[]> seeds = new ArrayList<int[]>();
        for (int sStart = 0; sStart < sLen - seedSize + 1; sStart++)
        {
            String seed = s.substring(sStart, sStart + seedSize);
            int tStart = t.indexOf(seed);
            while (tStart != -1)
            {
                seeds.add(new int[] {sStart, sStart + seedSize, tStart, tStart + seedSize});
                tStart = t.indexOf(seed, tStart + 1);
            }
        }

        int numThreads = seeds.size();
        System.out.println("Processing: " + numThreads + " seeds");
        int numBatches = Math.ceilDiv(numThreads, batchSize);
        Thread[] threads = new Thread[batchSize];
        for (int b = 0; b < numBatches; b++)
        {
            int start = b * batchSize;
            int end = Math.min(numThreads, start + batchSize);

            // System.out.println("Starting batch: " + b);
            for (int i = start; i < end; i++)
            {
                threads[i - start] = new Thread(new ScoreThread(seeds.get(i)));
                threads[i - start].start();
            }
            System.out.println("Waiting for batch: " + b);
            for (int i = start; i < end; i++)
            {
                try {             
                    threads[i - start].join(); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Done");

    }

}
