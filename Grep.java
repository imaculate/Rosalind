import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Grep {
    static Set<String> result = new HashSet<>();
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            list.add(scanner.nextLine());
        }
        scanner.close();

        String start = list.get(0);
        int k = start.length() - 1;
        Map<String, List<String>> map = new HashMap<>();
        for (String s: list)
        {
            map.computeIfAbsent(s.substring(0, k), t -> new ArrayList<>()).add(s.substring(1));
        }
        
        dfs(map, start, list.size(), k);

        for (String s: result)
        {
            System.out.println(s);
        }

    }

    public static void dfs(Map<String, List<String>> map, String prefix, int iter, int k)
    {
        String next = prefix.substring(prefix.length() - k);
        if (iter <= 0)
        {
            // System.out.println("Adding: " + prefix);
            result.add(prefix.substring(0, prefix.length() - k - 1));
            return;
        }


        if (!map.containsKey(next)) return;
        List<String> sus = new ArrayList<>(map.get(next));
        for (String suffix: sus)
        {
            map.get(next).remove(suffix);
            dfs(map, prefix + suffix.charAt(k-1), iter -1, k);
            map.get(next).add(suffix);
        }

    }

}
