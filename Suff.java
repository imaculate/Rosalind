import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Adapted from https://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-6/
class SuffixTreeNode
{
    Map<Character, SuffixTreeNode> children = new HashMap<>();
    SuffixTreeNode suffixLink  = null;
    int start = 0, end = 0, suffixIndex = -1;
    public SuffixTreeNode(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    public int edgeLength()
    {
        return end - start + 1;
    }
}


class SuffixTree
{
    String text;
    int len = -1;
    SuffixTreeNode root;
    static int remainingSuffixCount;
    static int leafEnd = -1;
    static int splitEnd;

    SuffixTreeNode activeNode;
    char activeEdge;
    int activeLength = 0;
    SuffixTreeNode lastNewNode = null;
    

    public SuffixTree(String text)
    {
        this.text = text;
        this.len = text.length();
        this.root = new SuffixTreeNode(-1, -1);
        this.activeNode = root;
    }

    public boolean walkDown(SuffixTreeNode currNode)
    {
        if (activeLength < currNode.edgeLength()) return false;
        activeEdge = text.charAt(len - remainingSuffixCount + 1);
        activeLength -= currNode.edgeLength();
        activeNode = currNode;
        return true;
    }

    public void build()
    {
        
        for (int i = 0; i < len; i++)
        {
            extendSuffixTree(i);
        }
        
        /*int labelHeight = 0;
        setSuffixIndexByDFS(root, labelHeight);
        freeSuffixTreeByPostOrder(root);*/
    }

     public void extendSuffixTree(int pos)
    {
        leafEnd = pos;
        remainingSuffixCount++;
        lastNewNode = null;

        while (remainingSuffixCount > 0) {

            if (activeLength == 0) activeEdge = text.charAt(pos);

            if (!activeNode.children.containsKey(activeEdge)) {
                activeNode.children.put(activeEdge, new SuffixTreeNode(pos, leafEnd));

                if (lastNewNode != null) {
                    lastNewNode.suffixLink = activeNode;
                    lastNewNode = null;
                }
            }
            else
            {
                SuffixTreeNode next = activeNode.children.get(activeEdge);
                if (walkDown(next)) continue;

                if (text.charAt(next.start + activeLength) == text.charAt(pos)) {
                    if (lastNewNode != null && activeNode != root) {
                        lastNewNode.suffixLink = activeNode;
                        lastNewNode = null;
                    }

                    activeLength++;
                    break;
                }

                splitEnd = next.start + activeLength - 1;
                SuffixTreeNode split = new SuffixTreeNode(next.start, splitEnd);
                activeNode.children.put(activeEdge, split);

                split.children.put(text.charAt(pos), new SuffixTreeNode(pos, leafEnd));
                next.start += activeLength;
                split.children.put(activeEdge, next);

                if (lastNewNode != null) lastNewNode.suffixLink = split;
                lastNewNode = split;
            }

            remainingSuffixCount--;
            if (activeNode == root && activeLength > 0)
            {
                activeLength--;
                activeEdge = text.charAt(pos - remainingSuffixCount + 1);
            }
            else if (activeNode != root)
            {
                activeNode = activeNode.suffixLink;
                if (activeNode == null) activeNode = root;
            }
        }
    }

    public void setSuffixIndexByDFS(SuffixTreeNode curr, int labelHeight)
    {
        if (curr == null) return;

        if (curr.start != -1) System.out.println(text.substring(curr.start, curr.end + 1));
        int leaf = 1;
        for (SuffixTreeNode child: curr.children.values()) {
            if (leaf == 1 && curr.start != -1) {
                System.out.println(" [" + curr.suffixIndex + "]");
            }

            leaf = 0;
            setSuffixIndexByDFS(child, labelHeight + child.edgeLength());
        }

        if (leaf == 1) {
            curr.suffixIndex = len - labelHeight;
            System.out.println(" [" + curr.suffixIndex + "]");
        }
    }

    public void freeSuffixTreeByPostOrder(SuffixTreeNode curr)
    {
        if (curr == null) return;

        for (SuffixTreeNode child: curr.children.values()) {
            freeSuffixTreeByPostOrder(child);
        }

        if (curr.suffixIndex == -1) curr.end = -1;
    }

    public void printSuffixes(SuffixTreeNode curr)
    {
        if (curr.start != -1) System.out.println(text.substring(curr.start, curr.end+1));
        
        for (SuffixTreeNode child: curr.children.values()) {
            printSuffixes(child);
        }
    }


    
}

public class Suff {

    public static void main(String[] args)
    {
        try (Scanner scanner = new Scanner(System.in)) {
            StringBuilder sb = new StringBuilder();

            while (scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }

            SuffixTree tree = new SuffixTree(sb.toString());
            tree.build();
            tree.printSuffixes(tree.root);
        }
    }
    

}
