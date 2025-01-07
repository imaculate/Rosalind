import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class Trie {

    static int gIndex = 1;

    static class Node
    {
        Character c;
        int index;
        Map<Character, Node> children = new HashMap<>();
        Node parent;

        public Node (char c, int index, Node parNode)
        {
            this.c = c;
            this.index = index;
            this.parent = parNode;
        }

        public void insert(String word)
        {
            if (word.length() == 0) return;
            char ch = word.charAt(0);
            if (!children.containsKey(ch))
            {
                children.put(ch, new Node(ch, gIndex++, this));
            }
            
            children.get(ch).insert(word.substring(1));
        }

        public void dfs()
        {
            System.out.println(parent.index + " " + index + " " + c);
            for (Node child: children.values())
            {
                child.dfs();
            }
        }

    }

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> dnaList = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            dnaList.add(scanner.nextLine());
        }

        scanner.close();

        Trie.Node root = new Trie.Node('*', gIndex++, null);
        for (String s: dnaList)
        {
            // System.out.println("Inserting " + s);
            root.insert(s);
        }

        for (Node child: root.children.values())
        {
            child.dfs();
        }
    }
    
    
}

