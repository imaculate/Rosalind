import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

class Node {
    String val;
    List<Node> children = new ArrayList<>();

    public Node(String val)
    {
        this.val = val;
    }

    public boolean isLeaf()
    {
        return children.isEmpty();
    }
}

public class Alphy {
    static Map<String, List<String>> graph = new HashMap<>();
    static int INF = 1000000;

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String line = "";
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if (line.startsWith(">")) break;
            sb.append(line);
        }


        Node root = parseTree(sb.toString());
        sb.setLength(0);
        Map<String, String> leaves = new HashMap<>();
        while (scanner.hasNextLine())
        {
            String spec = line.substring(1);
            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }
            leaves.put(spec, sb.toString());
            sb.setLength(0);
        }

        scanner.close();

        Map<String, String> seqs = new HashMap<>();
        for (String s: graph.keySet())
        {
            if (!leaves.containsKey(s)) seqs.put(s, "");
        }

        int sLen = leaves.values().iterator().next().length();
        int totalScore = 0;
        for (int pos = 0; pos < sLen; pos++)
        {
            Map<String, Character> chars = extractPosition(leaves, pos);
            totalScore += minimizeScore(chars, leaves, root);
            for (Map.Entry<String, String> entry: seqs.entrySet())
            {
                seqs.put(entry.getKey(), entry.getValue() + chars.get(entry.getKey()));
            }
        }

        System.out.println(totalScore);
        for (Map.Entry<String, String> e: seqs.entrySet())
        {
            System.out.println(">" + e.getKey());
            System.out.println(e.getValue());
        }

        
    }

    public static int minimizeScore(Map<String, Character> chars, Map<String, String> leaves, Node root)
    {
        List<Character> bases = List.of('A', 'C', 'G', 'T', '-');
        Map<String, List<Integer>> scores = new HashMap<>(); // minimum scores over the basess
        Map<String, List<Map<String, Integer>>> pnts = new HashMap<>();

        List<String> toProcess = new ArrayList<>();
        for (String s: graph.keySet())
        {
            if (leaves.containsKey(s))
            {
                scores.put(s, bases.stream().map(ch -> (ch == chars.get(s)) ? 0 : INF).toList());
            }
            else
            {
                toProcess.add(s);
            }
        }

        while (!toProcess.isEmpty())
        {
            for (String s: new ArrayList<>(toProcess))
            {
                if (!graph.get(s).stream().allMatch(child -> scores.containsKey(child))) continue;
                scores.put(s, new ArrayList<>());
                pnts.put(s, new ArrayList<>());

                for (char base: bases)
                {
                    int totalScore = 0;
                    Map<String, Integer> pnt = new HashMap<>();
                    for (String child: graph.get(s))
                    {
                        List<Integer> score = new ArrayList<>(scores.get(child));
                        for (int i = 0; i < bases.size(); i++)
                        {
                            if (bases.get(i) != base) score.set(i, 1 + score.get(i));
                        }
                        // System.out.println("For base" + base + ",  child " + child + " of " + s + " scores are " + score.toString());
                        int minScore = Collections.min(score);
                        totalScore += minScore;
                        pnt.put(child, score.indexOf(minScore));
                        //System.out.println("Setting minIndex: " + score.indexOf(minScore));
                    }
                    scores.get(s).add(totalScore);
                    pnts.get(s).add(pnt);
                }

                toProcess.remove(s);
            }
        }

        int score = Collections.min(scores.get(root.val));
        int idx = scores.get(root.val).indexOf(score);
        backTrack(idx, root.val, pnts, bases, chars);
        return score;
    }

    public static void backTrack(int idx, String node, Map<String, List<Map<String, Integer>>> pnts, List<Character> bases, Map<String, Character> chars)
    {
        // System.out.println("For child " + child + " of " + s + " scores are " + score.toString());
        chars.put(node, bases.get(idx));
        for (Map.Entry<String, Integer> pnt : pnts.get(node).get(idx).entrySet())
        {
            // pnt is child
            if (pnts.containsKey(pnt.getKey())) backTrack(pnt.getValue(), pnt.getKey(), pnts, bases, chars);
        }
    }

    public static Map<String, Character> extractPosition(Map<String, String>leaves, int pos)
    {
        Map<String, Character> map = new HashMap<>();
        for (String s:  graph.keySet())
        {
            if (leaves.containsKey(s)) map.put(s, leaves.get(s).charAt(pos));
        }
        return map;
    }

    public static boolean isNodeChar(char c)
    {
        return c == '_' || Character.isLetter(c);
    }

    public static Node parseTree(String tree)
    {
        
        int N = tree.length();
        List<Node> curr = new ArrayList<>();
        Stack<List<Node>> stack = new Stack<>();
        int i = 0;
        while (i < N-1)
        {
            char c = tree.charAt(i);
            if (c == '(')
            {
                stack.push(curr);
                curr = new ArrayList<>();
                i++;
            }
            else if (c == ')')
            {
                i++;
                int j = i;
                String s = "*";
                if (isNodeChar(tree.charAt(i)))
                {
                    while (j < N-1 && isNodeChar(tree.charAt(j))) j++;
                    s = tree.substring(i, j);
                }
    
                Node n = new Node(s);
                n.children = curr;
                graph.put(s, curr.stream().map(nt -> nt.val).toList());
                curr = stack.pop();
                curr.add(n);
                i = j;//skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
            else
            { // comma or node character
                int j = i;
                String s = "*";
                if (isNodeChar(c))
                {
                    while (j < N-1 && isNodeChar(tree.charAt(j))) j++;
                    s = tree.substring(i, j);
                }
                graph.put(s, new ArrayList<>());
                curr.add(new Node(s));
                i = j; // skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
        }

        return curr.get(0);
    }


}
