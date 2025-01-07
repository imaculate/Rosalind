import java.util.ArrayList;
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


public class Rsub {

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
        Map<String, String> mapping = new HashMap<>();
        while (scanner.hasNextLine())
        {
            String spec = line.substring(1);
            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }
            mapping.put(spec, sb.toString());
            sb.setLength(0);
        }
        scanner.close();

        int sLen = mapping.values().iterator().next().length();
        for (int pos = 0; pos < sLen; pos++)
        {
            extractPosition(root, mapping, pos, "", new HashMap<>(), new ArrayList<>());           
        }

    }

    public static void extractPosition(Node root, Map<String, String> mapping, int pos, String build, Map<Character, Integer> pMap, List<String> train)
    {
        char ch = mapping.get(root.val).charAt(pos);
        build += ch;
        train.add(root.val);

        int p = build.length() - 1;
        int prev = pMap.getOrDefault(ch, p);
        if (prev < p - 1)
        {
            int curr = prev + 1;
            char br = build.charAt(curr);
            while (build.charAt(curr) == br) curr++;
            if (curr == p)
            {
                // dog mouse 1 A->G->A
                // pMap.
                System.out.println(train.get(prev + 1) + " " + root.val + " "+ (pos + 1) + " " + ch + "->" + br + "->" + ch);
            }
        }

        pMap.put(ch, p);

        
        for (Node child : root.children)
        {
            extractPosition(child, mapping, pos, build, new HashMap<>(pMap), new ArrayList<>(train));
        }
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
                curr.add(new Node(s));
                i = j; // skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
        }

        return curr.get(0);
    }

}
