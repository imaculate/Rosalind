import java.util.Scanner;

public class ShortestSuperSeq {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine(), t = scanner.nextLine();
        scanner.close();
        
        int sLen = s.length(), tLen = t.length();
        String[][] table = new String[sLen][tLen];
        String lcs = longestCommonSubq(s, t, 0, 0, table);
        System.out.println("Computed LCS: " + lcs);

        int s1 = 0, t1 = 0, l1 = 0, lLen = lcs.length();
        StringBuilder sb = new StringBuilder();

        System.out.println("Computing Shortest Common Superseq:");
        while (s1 < sLen || t1 < tLen)
        {
            while (s1 < sLen && (l1 >= lLen || s.charAt(s1) != lcs.charAt(l1)))
            {
                sb.append(s.charAt(s1));
                s1++;
            }

            while (t1 < tLen && (l1 >= lLen || t.charAt(t1) != lcs.charAt(l1)))
            {
                sb.append(t.charAt(t1));
                t1++;
            }

            if (l1 < lLen)
            {
                sb.append(lcs.charAt(l1));
                l1++;
                s1++;
                t1++;
            }
        }

        System.out.println(sb.toString());
    }

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
