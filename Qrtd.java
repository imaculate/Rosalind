import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

class Node {
    String val;
    BitSet fingerprint = new BitSet();
    List<Node> children = new ArrayList<>();
    BitSet[] subs = new BitSet[3];

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
            fingerprint.or(child.fingerprint);
        }

        if (!val.equals("*"))
        {
            fingerprint.set(sMap.get(val));
        }


        if (!children.isEmpty())
        {
            subs[0] = children.get(0).fingerprint;
            subs[1] = children.get(1).fingerprint;
            if (fingerprint.cardinality() == sMap.size())
            {
                subs[2] = children.get(2).fingerprint;
            }
            else
            {
                subs[2] = (BitSet)subs[0].clone();
                subs[2].or(subs[1]);
                subs[2].flip(0, sMap.size());
            }
        }
    }
}


public class Qrtd {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String[] taxa = scanner.nextLine().split(" ");
        String t1 = scanner.nextLine(), t2 = scanner.nextLine();
        scanner.close();

        int N = taxa.length;
        System.out.println("N is: " + N);
        Map<String, Integer> sMap = new HashMap<>();
        int i = 0;
        for (String s: taxa)
        {
            sMap.put(s, i++);
        }

        //System.out.println("sMap is " + sMap);
        List<BitSet[]> f1 = new ArrayList<>(), f2 = new ArrayList<>();
        parseTree(t1.trim(), sMap, f1);
        parseTree(t2.trim(), sMap, f2);


        long countTotal = countQuartets(N);
        System.out.println("Quartet counts: " + countTotal);
        // long countShared = countSharedQuartets(table1, table2, N);
        long countShared = countSharedQuartets(f1, f2);
        System.out.println("Shared Quartet counts: "  + countShared);
        long res = 2 * countTotal -  countShared;
        System.out.println(res);
    }

    public static long countQuartets(int N)
    {
        return ((long)N * (N-1) * (N-2) * (N-3))/24L;
    }

    public static long countSharedQuartets(List<BitSet[]> f1, List<BitSet[]> f2)
    {
        long count = 0;
        BitSet tmp = new BitSet();
        for (BitSet[] v1: f1)
        {
            for (BitSet[] v2: f2)
            {
                tmp = (BitSet)v1[0].clone();
                tmp.and(v2[0]);
                int s1s1 = tmp.cardinality();

                tmp = (BitSet)v1[0].clone();
                tmp.and(v2[1]);
                int s1s2 = tmp.cardinality();

                tmp = (BitSet)v1[0].clone();
                tmp.and(v2[2]);
                int s1s3 = tmp.cardinality();

        
                tmp = (BitSet)v1[1].clone();
                tmp.and(v2[0]);
                int s2s1 = tmp.cardinality();

                tmp = (BitSet)v1[1].clone();
                tmp.and(v2[1]);
                int s2s2 = tmp.cardinality();

                tmp = (BitSet)v1[1].clone();
                tmp.and(v2[2]);
                int s2s3 = tmp.cardinality();
        
                tmp = (BitSet)v1[2].clone();
                tmp.and(v2[0]);
                int s3s1 = tmp.cardinality();

                tmp = (BitSet)v1[2].clone();
                tmp.and(v2[1]);
                int s3s2 = tmp.cardinality();

                tmp = (BitSet)v1[2].clone();
                tmp.and(v2[2]);
                int s3s3 = tmp.cardinality();
            
                count += countCrosses(s1s1, s2s2, s3s3);
                count += countCrosses(s1s1, s2s3, s3s2);
                count += countCrosses(s1s2, s2s1, s3s3);
                count += countCrosses(s1s2, s2s3, s3s1);
                count += countCrosses(s1s3, s2s2, s3s1);
                count += countCrosses(s1s3, s2s1, s3s2);

            }
        }
        return count;
    }

    public static long countCrosses(int n1, int n2, int n3)
    {
        long count = 0L;
        if(n1 < 1 || n2 < 1 || n3 < 1) return count;
        if (n1 >= 2) count += n1 * (n1 - 1) / 2L * n2 * n3;
        if (n2 >= 2) count += n2 * (n2 - 1) / 2L * n1 * n3;
        if(n3 >= 2) count += n3 * (n3 - 1) / 2L * n2 * n1;

        return count;
    }

    public static boolean isNodeChar(char c)
    {
        return c == '_' || Character.isLetter(c);
    }

    public static Node parseTree(String tree, Map<String, Integer> sMap, List<BitSet[]> subs)
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
                n.computeFingerprint(sMap);
                //System.out.println("Computed fingerprint: " + n.fingerprint);

                subs.add(n.subs);
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
