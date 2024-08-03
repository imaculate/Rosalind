import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Graph {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine(), label = "";
        Map<String, List<String>> prefixMap = new HashMap<>(), suffixMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        while (scanner.hasNext())
        {
            label = s.substring(1);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                s = scanner.nextLine();
                if (s.startsWith(">")) break;
                sb.append(s);
            }
            
            String dna = sb.toString();
            map.put(dna, label);
            prefixMap.computeIfAbsent(dna.substring(0, 3), x -> new ArrayList<String>()).add(dna);
            suffixMap.computeIfAbsent(dna.substring(sb.length() - 3), x -> new ArrayList<String>()).add(dna);
        }

        for (String suffix: suffixMap.keySet())
        {
            if (!prefixMap.containsKey(suffix)) continue;
            for (String suf: suffixMap.get(suffix))
            {
                for (String pre: prefixMap.get(suffix))
                {
                    if (pre.equals(suf)) continue;
                    System.out.println(map.get(suf) + " " + map.get(pre));

                }
            }
        }
    }
    
}
