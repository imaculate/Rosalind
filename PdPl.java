import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class PdPl {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt())
        {
            list.add(scanner.nextInt());
        }

        scanner.close();

        int T = list.size();
        Map<Integer, Integer> dists = new HashMap<>();
        for (Integer n: list)
        {
            dists.put(n, 1 + dists.getOrDefault(n, 0));
        }

        int N = (int)(1 + Math.sqrt(1 + 8*T))/2;

        TreeSet<Integer> res = new TreeSet<>();
        res.add(0);
        int width = Collections.max(list);

        while (dists.size() > 0)
        {
            int next = Collections.max(dists.keySet());
            // System.out.println("Retrieving: " + next);
            Map<Integer, Integer> nextDists = computeDiffs(next, res);
            if (reducedCounts(nextDists, dists))
            {
                // System.out.println("Adding: " + next);
                res.add(next);
                dists = diff(dists, nextDists);
            }
            else
            {
                // System.out.println("Adding: " + (width - next));
                res.add(width - next);
                dists = diff(dists, computeDiffs(width - next, res));
            }
        }

        for (Integer r: res)
        {
            System.out.print(r + " ");
        }
    }

    public static Map<Integer, Integer> diff(Map<Integer, Integer> m1, Map<Integer, Integer> m2)
    {
        Map<Integer, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> e: m1.entrySet())
        {
            int diff = e.getValue() - m2.getOrDefault(e.getKey(), 0);
            if (diff == 0) continue;
            map.put(e.getKey(), diff);
        }

        return map;
    }

    public static Map<Integer, Integer> computeDiffs(int d, Set<Integer> curr)
    {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer n: curr)
        {
            int diff = Math.abs(d - n);
            map.put(diff, 1 + map.getOrDefault(diff, 0));
        }

        return map;
    }

    public static boolean reducedCounts(Map<Integer, Integer> m1, Map<Integer, Integer> m2)
    {
        for (Map.Entry<Integer, Integer> entry: m1.entrySet())
        {
            if (entry.getValue() > m2.getOrDefault(entry.getKey(), 0)) return false;
        }
        return true;
    }

}
