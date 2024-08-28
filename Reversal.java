import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Reversal {
    static long[] factorials = new long[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800};
    static Map<Long, Long> parents = computeParents(10);
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = 10;
        System.out.println("Ready for input");
        int[] arr1 = new int[N], arr2  = new int[N];
        List<Integer> dists = new ArrayList();
        while (scanner.hasNextInt()) {
            for (int i = 0; i < 10; i++)
            {
                arr1[i] = scanner.nextInt();
            }

            for (int i = 0; i < 10; i++)
            {
                arr2[i] = scanner.nextInt();
            }
            dists.add(revDist(arr1, arr2));
            
        }

        for (Integer d: dists)
        {
            System.out.print(d + " ");
        }
    }

    public static int revDist(int[] arr1, int[] arr2)
    {
        if (Arrays.equals(arr1, arr2)) return 0;
        int N = arr1.length;
        Map<Integer, Integer> vToIndex = new HashMap();
        for (int i = 0; i < N; i++)
        {
            vToIndex.put(arr2[i], i+1);
        }

        int[] normPerm = new int[N];
        for (int i = 0; i < N; i++)
        {
            normPerm[i] = vToIndex.get(arr1[i]);
        }

        int dist = 0;
        long code = encode(normPerm);
        
        while (code != 0)
        {
            code = parents.get(code);
            dist++;
        }

        return dist;
    }

    public static Map<Long, Long> computeParents(int N)
    {
        Map<Long, Long> map = new HashMap<>();
        int[] perm = IntStream.rangeClosed(1, N).toArray();
        System.out.println("Precomputing parents starting with : " + Arrays.toString(perm));
        Queue<Long> perms = new LinkedList<>();
        perms.add(encode(perm));
        while (!perms.isEmpty())
        {
            long key = perms.poll();
            perm = decode(key, 10);
            for (int i  = 0; i < N; i++)
            {
                for (int j = i+1; j < N; j++)
                {
                    int[] rev = reversePerm(perm, i, j);
                    // reverseInPlace(perm, i, j);
                    long childKey = encode(rev);
                    if (!map.containsKey(childKey))
                    {
                        perms.add(childKey);
                        map.put(childKey, key);
                    }
                    // reverseInPlace(perm, i, j);
                }
            }
        }
        return map;
    }

    // Lehmer code: https://github.com/mateuszchudyk/lehmer/blob/master/lehmer.py
    public static long encode(int[] perm)
    {
        int N = perm.length, count = 0;
        int[] p = new int[N];
        for (int i = 0; i < N; i++)
        {
            for (int j = i+1; j < N; j++)
            {
                if (perm[j] < perm[i])
                {
                    count++;
                }
            }
            p[i] = count;
            count = 0;
        }

        long result = 0;
        for (int i = 0; i < N; i++)
        {
            result += factorials[N-i-1] * p[i];
        }
        return result;
    }

    public static int[] decode(long key, int N)
    {
        int[] perm = new int[N];
        for (int i = 0; i < N; i++)
        {
            perm[i] = (int)((key % factorials[N-i]) / factorials[N-i-1]);
        }

        boolean[] used = new boolean[N];
        int count = 0;
        for (int i = 0; i < N; i++)
        {
            count = 0;
            for (int j = 0; j < N; j++)
            {
                if (!used[j])
                {
                    count++;
                }
                if (count == perm[i] + 1)
                {
                    perm[i] = j;
                    used[j] = true;
                    break;
                }
            }
        }

        return perm;
    }


    public static int reversalDist(int[] arr1, int[] arr2)
    {
        if (Arrays.equals(arr1, arr2)) return 0;
        int N = arr1.length;
        Map<Integer, Integer> vToIndex = new HashMap();
        for (int i = 0; i < N; i++)
        {
            vToIndex.put(arr2[i], i+1);
        }

        int[] normPerm = new int[N];
        for (int i = 0; i < N; i++)
        {
            normPerm[i] = vToIndex.get(arr1[i]);
        }
        int dist = 0;

        for (int j = N; j > 1; j--)
        {
            int i = 0;
            for (i = 0; i < N; i++)
            {
                if (normPerm[i] == j) break;
            }

            if ((j - i) > 1)
            {
                System.out.println("Reversing " + Arrays.toString(normPerm) + " at positions " + i + " and " + (j-1));
                normPerm = reversePerm(normPerm, i, j-1);
                dist++;
            }
        }

        return dist;
    }

    public static void reverseInPlace(int[] perm, int i, int j)
    {
        int mid = (i+j)/2;
        for (int k = i; k <= mid; k++)
        {
            int temp = perm[k];
            perm[k] = perm[j + i - k];
            perm[j + i - k] = temp;
        }
    }

    public static int[] reversePerm(int[] perm, int i, int j)
    {
        int[] p = Arrays.copyOf(perm, perm.length);
        for (int k = i; k <= j; k++)
        {
            p[k] = perm[j + i - k];
        }
        return p;
    }

    /*public static int breakpointCount(int[] arr)
    {
        int count = (arr[0] != 1) ? 1 : 0;
        for (int i = 1; i < arr.length; i++)
        {
            if (Math.abs(arr[i] - arr[i-1]) != 1)
            {
                count++;
            }
        }
        count += (arr[arr.length - 1] != arr.length) ? 1 : 0;
        return count;
    }

    public static List<Integer> breakpointIndices(int[] arr)
    {
        List<Integer> list = new ArrayList();
        int len = arr.length;
        if (arr[0] != 1) list.add(0);
        for (int i = 1; i < len; i++)
        {
            if (Math.abs(arr[i] - arr[i-1]) != 1)
            {
                list.add(i);
            }
        }

        if(arr[len-1] != len) list.add(len);
        return list;
    }

    public static List<int[]> pairs(List<Integer> in)
    {
        int len = in.size();
        List<int[]> result = new ArrayList();
        for (int i = 0; i < len; i++)
        {
            for (int j = i+1; j < len; j++)
            {
                result.add(new int[] {i, j});
            }
        }
        return result;
    }


    // greedy breakpoint bfs
    public static int dist(int[] arr1, int[] arr2)
    {
        if (Arrays.equals(arr1, arr2)) return 0;
        int N = arr1.length;
        Map<Integer, Integer> vToIndex = new HashMap();
        for (int i = 0; i < N; i++)
        {
            vToIndex.put(arr2[i], i+1);
        }

        int[] normPerm = new int[N];
        for (int i = 0; i < N; i++)
        {
            normPerm[i] = vToIndex.get(arr1[i]);
        }

        List<int[]> currPerms = new ArrayList();
        currPerms.add(normPerm);
        int minBreaks = breakpointCount(normPerm);
        System.out.println("Breakpoint count of " + Arrays.toString(normPerm) + " is " + minBreaks);
        int dist = 0;
        Set<int[]> newPerms = new TreeSet<int[]>((int[] p1, int[] p2) -> Arrays.compare(p1, p2));

        while (true)
        {
            dist += 1;
            //System.out.println("Working set size is: " + currPerms.size());
            for (int[] perm: currPerms)
            {
                List<int[]> pairs = pairs(breakpointIndices(perm));
                //System.out.println("Exploring " + pairs.size() + " reverse permutations");
                for (int[] pair: pairs)
                {
                    int[] tempPerm = reversePerm(perm, pair[0], pair[1]-1);
                    int tempBreaks = breakpointCount(tempPerm);

                    if (tempBreaks == 0) return dist;

                    if (tempBreaks < minBreaks)
                    {
                        System.out.println("Breakpoint count of " + Arrays.toString(tempPerm) + " is " + tempBreaks + ", updating");
                        minBreaks = tempBreaks;
                        newPerms.clear();
                        newPerms.add(tempPerm); 
                    }
                    else if (tempBreaks == minBreaks)
                    {
                        //System.out.println("Breakpoint count of " + Arrays.toString(tempPerm) + " is " + tempBreaks + ", adding to working set");
                        newPerms.add(tempPerm);
                    }
                }
            }

            currPerms = new ArrayList<>(newPerms);
            newPerms.clear();
        }
    }*/
}