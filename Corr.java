import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Corr {

    static Map<Character, Character> revcMap = Map.of('A', 'T', 'T', 'A', 'C', 'G', 'G','C');
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String line = "";
        List<String> in = new ArrayList();
        while (scanner.hasNextLine()) {
            while (scanner.hasNextLine()) 
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }

            if (!sb.isEmpty()) { in.add(sb.toString()); }
            sb.setLength(0);
        }

        scanner.close();
        correct(in);

        /*for (String s: in)
        {
            System.out.println(s);
        }*/

    }

    public static void correct(List<String> list)
    {
        Map<String, Integer> counts = new HashMap<>();

        for (String s: list)
        {
            String revCS = reverseComplement(s);
            int count = counts.getOrDefault(s, 0);
            
            counts.put(s, count + 1);
            counts.put(revCS, count + 1);
        }

        for (String s: list)
        {
            if (counts.get(s) >= 2) continue;
            for (String s2 : list)
            {
                if (oneAway(s, s2) && counts.get(s2) >= 2)
                { 
                    System.out.println(s + "->" + s2);
                    break;
                }

                s2 = reverseComplement(s2);
                if (oneAway(s, s2) && counts.get(s2) >= 2)
                {
                    System.out.println(s + "->" + s2);
                    break;
                }

            }

        }


        
    }

    public static boolean oneAway(String s1, String s2)
    {
        int N = s1.length();
        if (N != s2.length()) return false;
        boolean diffFound = false;
        for (int i = 0; i < N; i++)
        {
            if (s1.charAt(i) != s2.charAt(i))
            {
                if (diffFound) return false;
                diffFound = true;
            }   
        }

        return diffFound;
    }

    public static String reverseComplement(String s)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length()-1; i >=0; i--)
        {
            sb.append(revcMap.get(s.charAt(i)));
        }
        return sb.toString();
    }
}
