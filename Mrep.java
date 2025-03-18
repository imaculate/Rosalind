import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Mrep {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine())
        {
            sb.append(scanner.nextLine());
        }
        scanner.close();

        SuffixTree tree = new SuffixTree();
        tree.addWord(sb.toString());
        int N = sb.length();

        // System.out.println(tree.word);

        Map<Integer, List<String>> repeats = new HashMap<>();
        for (int i = 1; i < tree.nodes.size(); i++)
        {
            SuffixTreeNode node = tree.nodes.get(i);
            String prefix = tree.nodeWord(node);
            int descendants = tree.totalDescendants(node);
            if (descendants >= 2 && prefix.length() >= 20)
            {
                repeats.computeIfAbsent(descendants, k -> new ArrayList<>()).add(prefix);
            }
        }

        List<String> result = new ArrayList<>();
        for (List<String> pres: repeats.values())
        {
            if (pres.size() == 1)
            {
                result.addAll(pres);
            }
            else
            {
                for (String pre: pres)
                {
                    Stream<String> sub = pres.stream().filter(k -> !k.equals(pre)).filter(k -> k.contains(pre));
                    if (sub.count() > 0) continue;
                    result.add(pre);
                }
            }
        }

        for (String s: result)
        {
            System.out.println(s);
        }
    }


}
