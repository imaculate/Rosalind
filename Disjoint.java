import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Disjoint {
    static String dna = "";
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        dna = scanner.nextLine();
        List<String> dnaList = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            dnaList.add(scanner.nextLine());
        }

        scanner.close();

        int N = dnaList.size();
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
               System.out.print(canInterweave(dnaList.get(i), dnaList.get(j)) + " ");
            }
            System.out.println("");
        }
    }

    public static int canInterweave(String d1, String d2)
    {
        int d1L = d1.length(), d2L = d2.length();
        Queue<int[]> q = new LinkedList<int[]>();
        q.add(new int[] {0,0,0});

        while (!q.isEmpty())
        {
            int[] pos = q.poll();
            for (int[] mv: generateMoves(pos))
            {
                int[] nPos = new int[] {pos[0] + mv[0], pos[1] + mv[1], pos[2] + mv[2]};
                if (isValidMove(nPos, mv, d1, d2))
                {
                    if (nPos[1] == d1L&& nPos[2] == d2L) return 1;
                    q.add(nPos);
                }
            }
        }
        return 0;
    }

    public static List<int[]> generateMoves(int[] start)
    {
        List<int[]> result = new ArrayList<>();
        result.add(new int[]{1, 0, 1});
        result.add(new int[]{1, 1, 0});

        if (start[1] == 0 && start[2] == 0)
        {
            result.add(new int[]{1, 0, 0});
        }
        return result;
    }

    public static boolean isValidMove(int[] nPos, int[] mv, String d1, String d2)
    {
        if (nPos[1] > d1.length() || nPos[2] > d2.length() || nPos[0] > dna.length()) return false;

        Set<Character> set = new HashSet<>();
        for (int i = 0; i < 3; i++)
        {
            if (mv[i] == 0) continue;
            char c = '*';
            if (i == 0) c = dna.charAt(nPos[i] - 1);
            if (i == 1) c = d1.charAt(nPos[i] - 1);
            if (i == 2) c = d2.charAt(nPos[i] - 1);
            set.add(c);
        }

        return set.size() == 1;
    }



}
