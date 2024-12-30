import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Gasm {
    static Map<Character, Character> comps =  Map.of(
    'A', 'T',
    'T', 'A',
    'G', 'C',
    'C', 'G'
);
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        Set<String> set = new HashSet<>();
        while (scanner.hasNextLine())
        {
            set.add(scanner.nextLine());
        }
        scanner.close();


        Set<String> rvs = new HashSet<>();
        for (String s: set)
        {
            rvs.add(revComplement(s));
        }

        set.addAll(rvs);
        System.out.println(superString(set));
    }

    public static String revComplement(String s)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--)
        {
            sb.append(comps.get(s.charAt(i)));
        }
        return sb.toString();
    }

    public static String superString(Set<String> set)
    {
        int sLen = ((String) set.toArray()[0]).length();
        
        StringBuilder sb = new StringBuilder();

        // k is length of overlap
        for (int k = sLen-1; k > 1; k--)
        {
            Map<String, String> map = new HashMap();
            sb.setLength(0);
            for (int start = 0; start < sLen - k; start++)
            {
                for (String s: set)
                {
                    map.put(s.substring(start, start + k), s.substring(start + 1, start + 1 + k));
                }
            }

            String startString = map.keySet().iterator().next(), prefix = startString;
            while (true) {
                if (!map.containsKey(prefix)) break;
                String suffix = map.get(prefix);
                sb.append(suffix.charAt(k-1));
                // System.out.println(prefix + " -> " + suffix);
                if (suffix.equals(startString)) return sb.toString();
                prefix = suffix;
            }
        }
        return sb.toString();
    }

    public static boolean cyclicIncluded(String s1, String s2)
    {
        return s1.repeat(2).contains(s2);
    }


}
