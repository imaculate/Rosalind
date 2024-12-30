import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Dbru {
    static Map<Character, Character> comps =  Map.of(
    'A', 'T',
    'T', 'A',
    'G', 'C',
    'C', 'G'
);
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Set<String> set = new HashSet<>();
        while (sc.hasNextLine())
        {
            set.add(sc.nextLine());
        }


        sc.close();
        Set<String> rvs = new HashSet<>();
        for (String s: set)
        {
            rvs.add(revComplement(s));
        }

        set.addAll(rvs);

        for (String s: set)
        {
            System.out.println("(" + s.substring(0, s.length() - 1) + ", " + s.substring(1) +  ")");
        }

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

}
