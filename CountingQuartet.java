import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class CountingQuartet {
    static List<String> taxa = new ArrayList<>();
    static int MAX_Q = 1000000;


    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine())
        {
            sb.append(scanner.nextLine());
        }
        scanner.close();

        /*taxa.clear();
        Node root = parseTree(sb.toString().trim());
        Collections.sort(taxa);
        Map<String, Integer> sMap = new HashMap<>();
        int i = 0;
        for (String s: taxa)
        {
            sMap.put(s, i++);
        }

        List<String> table = new ArrayList<>();
        process(root, sMap, taxa.size(), table);


        Set<Integer[]> result = new TreeSet<>((Integer[] a, Integer[] b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            if (a[1] != b[1]) return a[1] - b[1];
            if (a[2] != b[2]) return a[2] - b[2];
            return a[3] -b[3];
        });

        for (String s : table)
        {
            System.out.println(s);
            List<Integer> i0 = new ArrayList<>(), i1 = new ArrayList<>();
            for (i = 0; i< N; i++)
            {
                char c = s.charAt(i);
                if (c == '0')
                {
                    i0.add(i);
                }
                else if (c == '1')
                {
                    i1.add(i);
                }
            }

            if (i0.size() < 2 || i1.size() < 2) continue;

            for (Integer[] zeros : generate(i0))
            {
                for (Integer[] ones : generate(i1))
                {
                    if (zeros[0] < ones[0])
                    {
                        result.add(new Integer[] {zeros[0], zeros[1], ones[0], ones[1]});
                    }
                    else
                    {
                        result.add(new Integer[] {ones[0], ones[1], zeros[0], zeros[1]});
                    }
                }
            }
        }*/

        System.out.println(countQ(N));


    }

    public static long countQ(long N)
    {
        // NC4
        System.out.println("Working with: " + N);
        long result = (N * (N-1) * (N-2) * (N-3))/24L;
        System.out.println("Intermediate: " + result);
        return result % MAX_Q;
    }

    public static List<Integer[]> generate(List<Integer> list)
    {
        List<Integer[]> result = new ArrayList<>();
        if (list.size() == 2)
        {
            result.add(list.toArray(new Integer[2]));
            return result;
        }

        int N = list.size();
        for (int i = 0; i < N; i++)
        {
            for (int j = i+1; j < N; j++)
            {
                result.add(new Integer[] {list.get(i), list.get(j)});
            }
        }
        return result;
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
