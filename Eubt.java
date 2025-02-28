import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

class Tree
{
    // Maps from external node to internal
    Map<String, List<String>> graph = new HashMap<>();

    public Tree(Map<String, List<String>> graph)
    {
        this.graph = graph;
    }   

    public Tree insert(String newLeaf, String p, String child)
    {
        String nextIdx = String.valueOf(graph.size());
        Map<String, List<String>> newGraph = graph.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new ArrayList<String>(List.copyOf(e.getValue()))));
    
        newGraph.put(nextIdx, List.of(newLeaf, child));
        List<String> pCh = newGraph.get(p);
        pCh.remove(child);
        pCh.add(nextIdx);
        newGraph.put(p, pCh);
        return new Tree(newGraph);
    }

    public List<Map.Entry<String, String>> getEdges()
    {
        List<Map.Entry<String, String>> edges = new ArrayList<>();

        for (Entry<String, List<String>> entry: graph.entrySet())
        {
            String parent = entry.getKey();
            for (String child: entry.getValue())
            {
                edges.add(new AbstractMap.SimpleEntry<>(parent, child));
            }
        }

        return edges;
    }

    public String newickString(String node)
    {
        StringBuilder sb = new StringBuilder();
        for (String child: graph.get(node))
        {
            if (Character.isDigit(child.charAt(0)))
            {
                sb.append(newickString(child) + ",");
            }
            else
            {
                sb.append(child + ",");
            }
        }

        // remove comma
        return "(" + sb.substring(0, sb.length() - 1).toString() + ")";
    }

}

public class Eubt {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> taxa = new ArrayList<>();
        while (scanner.hasNext())
        {
            taxa.add(scanner.next());
        }
        scanner.close();

        List<Tree> trees = new ArrayList<>(), next = new ArrayList<>();

        Map<String, List<String>> graph = new HashMap<>();
        graph.put("0", taxa.subList(0, 3));
        trees.add(new Tree(graph));
        

        for (int i = 3; i < taxa.size(); i++)
        {
            String newLeaf = taxa.get(i);
            for (Tree tree: trees)
            {
                for (Map.Entry<String, String> edge: tree.getEdges())
                {
                    next.add(tree.insert(newLeaf, edge.getKey(), edge.getValue()));
                }
            }
            trees = new ArrayList<>(next);
            next.clear();
        }

        for (Tree tree: trees)
        {
            System.out.println(tree.newickString("0") + ";");
        }
    }


}
