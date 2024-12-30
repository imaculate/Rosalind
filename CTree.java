import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Node {

    List<Integer> clades = new ArrayList<>();
    List<Node> taxa = new ArrayList<>();

    public Node (List<Integer> clades)
    {
        this.clades = clades;
    }


    public String getString(String[] spec)
    {
        if (clades.size() == 1) return spec[clades.get(0)];
        //System.out.println("Non trivial clades: " + clades.toString() + ", splittable? " + !taxa.isEmpty());
        StringBuilder sb = new StringBuilder();
        if (taxa.isEmpty()) 
        {
            sb.append('(' + spec[clades.get(0)]);
            for (int i = 1; i < clades.size(); i++)
            {
                sb.append(',' + spec[clades.get(i)]);
            }
            sb.append(')');
        }
        else
        {
            sb.append('(' + taxa.get(0).getString(spec));
            for (int i = 1; i < taxa.size(); i++)
            {
                sb.append(',' + taxa.get(i).getString(spec));
            }
            sb.append(')');
        }
        return sb.toString();
    }

    public boolean split(String s)
    {
        List<Integer> left = new ArrayList<>(), right = new ArrayList<>();
        for (Integer i: clades)
        {
            if (s.charAt(i) == '0')
            {
                left.add(i);
            }
            else
            {
                right.add(i);
            }
        }

        if (left.isEmpty() || right.isEmpty()) return false;

        taxa.add(new Node(left));
        taxa.add(new Node(right));
        return true;
    }

    public void splitAll(List<String> chars, int depth)
    {

        if (depth >= chars.size()) return;

        if (split(chars.get(depth)))
        {
            for (Node taxon: taxa)
            {
                taxon.splitAll(chars, depth + 1);
            }
        }
        else
        {
            splitAll(chars, depth + 1);
        }
    }

}

public class CTree {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String[] taxa = scanner.nextLine().trim().split(" ");
        List<String> table = new ArrayList<>();

        while (scanner.hasNextLine())
        {
            table.add(scanner.nextLine());
        }
        scanner.close();

        
        Node root = process(taxa, table);
        System.out.println(root.getString(taxa) + ";");

    }

    public static Double entropy(String s)
    {
        double N = s.length();
        int count = 0;
        for (char c: s.toCharArray())
        {
            count += (c == '1') ? 1 : 0;
        }
        double f1 = count/N, f2 = 1 - f1;
        double e = -1 * (f1 * Math.log(f1) + f2 * Math.log(f2))/Math.log(2);
        //System.out.println("Entropy of " + s + " is " + e + " with f1: " + f1);
        return e;
    }

    public static Node process(String[] taxa, List<String> table)
    {
        Collections.sort(table, (s1, s2) -> entropy(s2).compareTo(entropy(s1)));

        /*System.out.println("Sorted table:");
        for (String s: table)
        {
            System.out.println(s);
        }*/
        List<Integer> cladesIdx = new ArrayList<>();
        for (int i = 0; i < taxa.length; i++)
        {
            cladesIdx.add(i);
        }

        Node root = new Node(cladesIdx);
        root.splitAll(table, 0);
        return root;
    }
    
}
