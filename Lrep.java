import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Node
{
    String label = "";
    List<Node> children = new ArrayList<>();
    int start, end;

    public Node(String label)
    {
        this.label = label;
    }

}
public class Lrep {

    static int bestLen = 0;
    static String superString = "";
    static List<int[]> bestst = new ArrayList<>(), st = new ArrayList<>();


    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String line = "";
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            sb.append(line);
            if (line.endsWith("$")) break;
        }

        superString = sb.toString().trim();


        int k = Integer.parseInt(scanner.nextLine().trim());
        Map<String, Node> map = new HashMap<>();
        Set<Node> set = new HashSet<>();

        while (scanner.hasNextLine())
        {
            String[] info = scanner.nextLine().split(" ");
            Node pNode = map.computeIfAbsent(info[0], n -> new Node(info[0]));
            Node sNode = map.computeIfAbsent(info[1], n -> new Node(info[1]));

            sNode.start = Integer.parseInt(info[2]) - 1;
            sNode.end = sNode.start + Integer.parseInt(info[3]);
            pNode.children.add(sNode);
            set.add(sNode);
        }

        Set<Node> all = new HashSet<>(map.values());
        all.removeAll(set);
        Node root = all.iterator().next();

        find(root, 0, k);
        sb.setLength(0);
        for (int[] range: bestst)
        {
            sb.append(superString.substring(range[0], range[1]));
        }
        System.out.println(sb.toString());
    }

    public static int find(Node root, int l, int k)
    {
        int ss = 0;
        if (root.end == superString.length())
        {
            // System.out.println(root.label + " with sub " + superString.substring(root.start, root.end) + " reached the end");
            return 1;
        }

        st.add(new int[] { root.start, root.end });
        int curr = l + root.end - root.start;
        // System.out.println("Processing " + root.label + " with ln: " + curr );
        for(Node child: root.children)
        {
            ss += find(child, curr, k);
        }

        //System.out.println(root.label + " with sub at " + superString.substring(root.start, root.end) + " and len " + curr + " has " + ss + " occurences");
        if(ss >= k && curr > bestLen)
        {
            bestst = new ArrayList<>(st);
            bestLen = curr;
        }
            
        st.remove(st.size() - 1);
        return ss;
    }

}
