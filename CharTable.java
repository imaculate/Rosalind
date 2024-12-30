import java.util.ArrayList;
import java.util.Arrays;
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

public class CharTable {
    static List<String> taxa = new ArrayList<>();


    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine())
        {
            sb.append(scanner.nextLine());
        }

        scanner.close();
        taxa.clear();
        Node root = parseTree(sb.toString().trim());
        Collections.sort(taxa);
        Map<String, Integer> sMap = new HashMap<>();
        int i = 0;
        for (String s: taxa)
        {
            sMap.put(s, i++);
        }

        List<String> result = new ArrayList<>();
        process(root, sMap, taxa.size(), result);

        for (String s: result)
        {
            System.out.println(s);
        }
        
    }

    public static char[] process(Node root, Map<String, Integer> sMap, int N, List<String> result)
    {

        char[] clades = new char[N];
        Arrays.fill(clades, '0');

        int countOnes = 0;
        for (Node child: root.children)
        {
            char[] baby = process(child, sMap, N, result);
            for (int i  = 0; i < N; i++)
            {
                if (baby[i] == '1')
                {
                    clades[i] = '1';
                    countOnes++;
                }
            }
            // assimilate
        }

        if (!root.val.equals("*")) 
        {
            clades[sMap.get(root.val)] = '1';
            countOnes++;
            // System.out.println("Clades at " + root.val + " is " + new String(clades));
        }

        //System.out.println("Clades for " + root.val + " " + clades);

        if (countOnes > 1 && countOnes < N - 1)
        {
            result.add(new String(clades));
        }
        return clades; 
        
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
                    taxa.add(s);
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
                    taxa.add(s);
                }
                
                curr.add(new Node(s));
                i = j; // skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
        }

        return curr.get(0);
    }
}
