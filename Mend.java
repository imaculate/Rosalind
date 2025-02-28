import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class Mend {
    static class Node {
        String val;
        List<Node> children = new ArrayList<>();
        double[] probs = new double[3];
    
        public Node(String val)
        {
            this.val = val;
            if (val.length() == 2)
            {
                probs[types.indexOf(val)] = 1.0;
            }
        }
    
        public boolean isLeaf()
        {
            return children.isEmpty();
        }
    }
    
    static List<String> types = List.of("AA", "Aa", "aa");
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        scanner.close();
        Node root = parseTree(sb.toString());
        for (double d: root.probs)
        {
            System.out.print(Math.round(d * 1000.0)/ 1000.0 + " ");
        }
    }

    public static boolean isNodeChar(char c)
    {
        return c == '_' || Character.isLetter(c);
    }

    public static double[] mix(double[] a, double[] b)
    {
        double[] result = new double[3];
        result[0] = (a[0] * b[0]) + (0.5 * a[0] * b[1]) + (0.5 * a[1] * b[0]) + (0.25 * a[1] * b[1]);
        result[2] = (a[2] * b[2]) + (0.5 * a[2] * b[1]) + (0.5 * a[1] * b[2]) + (0.25 * a[1] * b[1]);
        result[1] = 1 - (result[0] + result[2]);
        return result;
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
                n.probs = mix(curr.get(0).probs, curr.get(1).probs);
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
