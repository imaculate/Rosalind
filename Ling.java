import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class SuffixTreeNode
{
    SuffixTreeNode parent;
    List<SuffixTreeNode> children = new ArrayList<>();
    int val;

    public SuffixTreeNode(int val, SuffixTreeNode parent)
    {
        this.val = val;
        this.parent = parent;
    }

    public void addChild(SuffixTreeNode child)
    {
        children.add(child);
    }

    public void removeChild(SuffixTreeNode child)
    {
        children.remove(child);
    }

    public void updateParent(SuffixTreeNode newParent)
    {
        this.parent = newParent;
    }
}

class SuffixTree
{
    class InsertResult
    {
        int edgeStart;
        SuffixTreeNode parent;
        boolean overlap;

        public InsertResult(int edgeStart, SuffixTreeNode parent, boolean overlap)
        {
            this.edgeStart = edgeStart;
            this.parent = parent;
            this.overlap = overlap;
        }

    }
    List<SuffixTreeNode> nodes = new ArrayList<>();
    Map<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> edges = new HashMap<>();
    Map<SuffixTreeNode, Integer> descendants = new HashMap<>();
    String word = "";
    int N = 0;

    public SuffixTree()
    {
        nodes.add(new SuffixTreeNode(0, null));
    }

    public InsertResult insertPosition(int start, SuffixTreeNode parent)
    {
        for (SuffixTreeNode child: parent.children)
        {
            Map.Entry<Integer, Integer> edge = edges.get(new AbstractMap.SimpleEntry<>(parent.val, child.val));
            int edgeStart = edge.getKey(), edgeEnd = edge.getValue();
            if (word.substring(start, Math.min(N, start + edgeEnd - edgeStart)).equals(word.substring(edgeStart, edgeEnd)))
            {
                return insertPosition(start + edgeEnd - edgeStart, child);
            }
            else if (word.charAt(edgeStart) == word.charAt(start))
            {
                return new InsertResult(start, child, true);
            }
        }

        return new InsertResult(start, parent, false);
    }

    public void addNode(SuffixTreeNode parent, int edgeStart, int edgeEnd, SuffixTreeNode child)
    {
        if (child == null) child = new SuffixTreeNode(nodes.size(), parent);
        nodes.add(child);
        parent.addChild(child);
        // System.out.println("In addNode: Adding edge from: " + parent.val + " to " + child.val);
        edges.put(new AbstractMap.SimpleEntry<>(parent.val, child.val), new AbstractMap.SimpleEntry<>(edgeStart, edgeEnd));
    }


    public void addWord(String word)
    {
        if (word.charAt(word.length() - 1) != '$') word += '$';
        this.word = word;
        this.N = word.length();

        for (int i = 0; i < N; i++)
        {
            // System.out.println("Inserting: " + word.charAt(i) + " at position " + i);
            InsertResult res = insertPosition(i, nodes.get(0));
            if (res.overlap)  
            {
                Map.Entry<Integer, Integer> edgeKey = new AbstractMap.SimpleEntry<>(res.parent.parent.val, res.parent.val);
                Map.Entry<Integer, Integer> edge = edges.get(edgeKey);
                // if (edge == null) System.out.println("No edge from: " + res.parent.parent.val + " to " + res.parent.val);
                int pEdgeStart = edge.getKey(), pEdgeEnd = edge.getValue();
                int insertLen = 0;
                while (word.substring(res.edgeStart, res.edgeStart + insertLen).equals(word.substring(pEdgeStart, pEdgeStart + insertLen))) insertLen += 1;
                
                SuffixTreeNode newNode = new SuffixTreeNode(nodes.size(), res.parent.parent);
                newNode.addChild(res.parent);
                addNode(res.parent.parent, pEdgeStart, pEdgeStart + insertLen - 1, newNode);

                // Update the parent node since a new node is inserted above it
                // System.out.println("Removing edge from: " + edgeKey.getKey() + " to " + edgeKey.getValue());
                edges.remove(edgeKey);
                res.parent.parent.removeChild(res.parent);
                res.parent.updateParent(newNode);
                edgeKey = new AbstractMap.SimpleEntry<>(res.parent.parent.val, res.parent.val);
                // System.out.println("Adding edge from: " + edgeKey.getKey() + " to " + edgeKey.getValue());
                edges.put(edgeKey, new AbstractMap.SimpleEntry<>(pEdgeStart + insertLen -1, pEdgeEnd));

                // Add new child node
                addNode(res.parent.parent, res.edgeStart + insertLen - 1, N, null);

            }
            else
            {
                addNode(res.parent, res.edgeStart, N, null);
            }
        }
    }

    public int totalDescendants(SuffixTreeNode baseNode)
    {
        if(!descendants.containsKey(baseNode))
        {
            descendants.put(baseNode, baseNode.children.size() + baseNode.children.stream().mapToInt(c -> totalDescendants(c)).sum());
        }

        return descendants.get(baseNode);
    }

    // return prefix 
    public String nodeWord(SuffixTreeNode node)
    {
        StringBuilder curr = new StringBuilder();
        Map.Entry<Integer, Integer> edge = null;
        while (node.val != 0)
        {
            edge = edges.get(new AbstractMap.SimpleEntry<>(node.parent.val, node.val));
            curr.insert(0, word.substring(edge.getKey(), edge.getValue()));
            node = node.parent;
        }

        return curr.toString().replace("$", "");
    }

    public void printEdges()
    {
        for (Map.Entry<Integer, Integer> edge: edges.values())
        {
            System.out.println(word.substring(edge.getKey(), edge.getValue()));
        }
    }
    
}

public class Ling {
    // runs on Azure VM
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
        int N = sb.length();
        System.out.println("Word of length: " + N + " being added to tree");
        long start = System.currentTimeMillis();
        tree.addWord(sb.toString());

        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Integer, Integer> edge: tree.edges.values())
        {
            if (edge.getValue() == N+1)
            {
                sum = sum.add(BigDecimal.valueOf(N - edge.getKey()));
                // (edge.getKey(), N)
            }
            else
            {
                sum = sum.add(BigDecimal.valueOf(edge.getValue() - edge.getKey()));
            }
        }

        int kStop = (int)Math.floor(Math.log(N+1)/Math.log(4));
        BigDecimal m = BigDecimal.valueOf(0);
        BigDecimal fP = BigDecimal.valueOf(4);
        for (int k = 1; k <= kStop; k++)
        {
            m = m.add(fP);
            fP = fP.multiply(BigDecimal.valueOf(4));
        }

        for (int k = kStop + 1; k <= N; k++)
        {
            m = m.add(BigDecimal.valueOf(N - k + 1));
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("Took: " + duration/60000 + " minutes");

        

        System.out.println(sum.divide(m, 5,  RoundingMode.CEILING));

    }

}
