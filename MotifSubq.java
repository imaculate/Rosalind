import java.util.Scanner;

public class MotifSubq {
    // Longest common sunsequence
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // initial label
        StringBuilder sb = new StringBuilder();
        String line = "", s = "", t = "";

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if (line.startsWith(">"))
            {
                s = sb.toString();
                sb.setLength(0);
            }
            else
            {
                sb.append(line);
            }
        }

        t = sb.toString();

        scanner.close();
        System.out.println("Computing LCS:");
        String[][] table = new String[s.length()][t.length()];

        System.out.println(longestCommonSubq(s, t, 0, 0, table));

    }

    // ALternatively find length and work backwards
    public static String longestCommonSubq(String s, String t, int i, int j, String[][] table)
    {
        if (i == s.length()|| j == t.length()) 
        {
            return "";
        }

        if (table[i][j] != null) return table[i][j];

        if (s.charAt(i) == t.charAt(j))
        {
            return table[i][j] = s.charAt(i) + longestCommonSubq(s, t, i+1, j+1, table);
        }

        String s1 = longestCommonSubq(s, t, i, j+1, table), t1 = longestCommonSubq(s, t, i+1, j, table);
        return table[i][j] = s1.length() > t1.length() ? s1 : t1;
    }


}
