import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class Node {
    String val;
    int toParent;
    List<Node> children = new ArrayList<>();

    public Node(String val)
    {
        this.val = val;
    }
}

public class DistWTree {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String tree = "", nodes = "";

        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            tree = scanner.nextLine();
            nodes = scanner.nextLine();
            
            list.add(process(tree, nodes));
            if (scanner.hasNextLine()) scanner.nextLine();
        }
        for (Integer result: list)
        {
            System.out.print(result + " ");
        }
        scanner.close();
    }

    public static int process(String tree, String nodes)
    {
        Node root = parseTree(tree);
        String[] nn = nodes.split(" ");
        return find(root, nn[0].trim(), nn[1].trim());
    }

    public static int find(Node root, String node1, String node2)
    {
        //System.out.println("Finding " + node1 + " and " + node2 + " at node: " + root.val);
        int r1 = root.val.equals(node1) ? 0 : -1;
        int r2 = root.val.equals(node2) ? 0 : -1;
        int x1 = -1, x2 = -1, dist1 = -1, dist2 = -1;
        //System.out.println("R1 and R2 are: " + r1 + " " + r2);

        for (Node chiNode: root.children)
        {
            x1 = (r1 == -1) ? dist(chiNode, node1) : -1;
            x2 = (r2 == -1) ? dist(chiNode, node2) : -1;
            //System.out.println("Distances from child: " + chiNode.val + "; " + x1 + " " + x2);
            if (x1 != -1 && x2 != -1)
            {
                return find(chiNode, node1, node2);
            }

            if (r1 != -1 && x2 != -1) return chiNode.toParent + x2;
            if (r2 != -1 && x1 != -1) return chiNode.toParent + x1;

            dist1 = (x1 != -1) ? x1 + chiNode.toParent : dist1;
            dist2 = (x2 != -1) ? x2 + chiNode.toParent : dist2;
            //System.out.println("After " + chiNode.val + " distances are now " + dist1 + " " + dist2);
        }

        //System.out.println("Distances: " + dist1 + " " + dist2);
        if (dist1 != -1 && dist2 != -1) return dist1 + dist2;
        return -1;
    }

    public static int dist(Node root, String node)
    {
        if (root.val.equals(node)) return 0;
        for (Node chiNode: root.children)
        {
            int dist = dist(chiNode, node);
            if (dist != -1)
            {
                return chiNode.toParent + dist;
            }
        }
        return -1;

    }

    public static boolean isNodeChar(char c)
    {
        return c == '_' || Character.isLetter(c);
    }

    public static Node parseTree(String tree)
    {
        tree = tree.trim();
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
                if (isNodeChar(tree.charAt(i)))
                {
                    while (j < N-1 && isNodeChar(tree.charAt(j))) j++;
                }
                Node n = (i != j) ? new Node(tree.substring(i, j)) : new Node("*");

                i = j = j+1; // skip the ':'
                while (j < N-1 && Character.isDigit(tree.charAt(j))) j++;
                if (j > i) n.toParent = Integer.parseInt(tree.substring(i, j));

                n.children = curr;
                curr = stack.pop();
                curr.add(n);
                i = j;//skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
            else
            { // comma or node character
                int j = i;
                if (isNodeChar(c))
                {
                    while (j < N-1 && isNodeChar(tree.charAt(j))) j++;
                }
                Node n = (i != j) ? new Node(tree.substring(i, j)) : new Node("*");

                i = j = j+1; // skip the ':'
                while (j < N-1 && Character.isDigit(tree.charAt(j))) j++;
                if (j > i) n.toParent = Integer.parseInt(tree.substring(i, j));

                curr.add(n);
                i = j; // skip comma
                if (i < N-1 && tree.charAt(i) == ',') i++;
            }
        }

        return curr.get(0);
    }

}
