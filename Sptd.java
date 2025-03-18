import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

class Node {
    String val;
    int fingerprint = 0;
    List<Node> children = new ArrayList<>();

    public Node(String val)
    {
        this.val = val;
    }

    public boolean isLeaf()
    {
        return children.isEmpty();
    }

    public void computeFingerprint(Map<String, Integer> sMap)
    {
        for (Node child: children)
        {
            fingerprint |= child.fingerprint;
        }

        if (!val.equals("*"))
        {
            fingerprint |= (1 << (sMap.get(val)));
            //System.out.println("Fingerprint for: " + val + " is " + fingerprint);
        }
    }
}

public class Sptd {
    static int MODULO = 1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String[] taxa = scanner.nextLine().split(" ");
        String t1 = scanner.nextLine(), t2 = scanner.nextLine();
        scanner.close();

        int N = taxa.length;

        Map<String, Integer> sMap = new HashMap<>();
        int i = 0;
        for (String s: taxa)
        {
            sMap.put(s, i++);
        }


        Set<Integer> table1 = new HashSet<>(), table2 = new HashSet<>();
        parseTree(t1.trim(), sMap, table1);
        parseTree(t2.trim(), sMap, table2);
        


        Set<Integer> commonElements = new HashSet<>(table1);
        // Retain only the elements present in set2
        commonElements.retainAll(table2);
        int split = 2 * (N -3 - commonElements.size());
        System.out.println(split);
    }

    public static boolean isNodeChar(char c)
    {
        return c == '_' || Character.isLetter(c);
    }

    public static Node parseTree(String tree, Map<String, Integer> sMap, Set<Integer> table)
    {
        
        int N = tree.length();
        int taxaSize = sMap.size();
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
                n.computeFingerprint(sMap);
                if (n.fingerprint > 0 && Integer.bitCount(n.fingerprint) < taxaSize - 1) table.add(n.fingerprint);
                //graph.put(s, curr.stream().map(nt -> nt.val).toList());
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
                //graph.put(s, new ArrayList<>());
                Node n = new Node(s);
                n.computeFingerprint(sMap);
                curr.add(n);
                i = j; // skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
        }

        return curr.get(0);
    }
}
