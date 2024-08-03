import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

public class LCS {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        List<String> list = new ArrayList<String>();
        while (scanner.hasNext())
        {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }
            
            String dna = sb.toString();
            list.add(dna);
        }

        String shortest = list.get(0);
        for (String s: list)
        {
            if (s.length() < shortest.length())
            {
                shortest = s;
            }
        }

        int sLen = shortest.length();
        Set<String> motif = new HashSet();
        // collect all possible substrings of shortest dna
        for (int i = 0; i < sLen; i++)
        {
            for (int j  = i+1; j <= sLen; j++)
            {
                motif.add(shortest.substring(i, j));
            }
        }
        for (String s : list)
        {
            Set<String> to_remove = new HashSet();
            for (String m : motif)
            {
                if (!s.contains(m))
                {
                    to_remove.add(m);
                }
            }
            motif.removeAll(to_remove);
        }

        // Get the longest substring
        String lcs = "";
        for (String m: motif)
        {
            if (m.length() > lcs.length())
            {
                lcs = m;
            }
        }

        System.out.println(lcs);

        
    }
    
}
