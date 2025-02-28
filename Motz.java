import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Motz {
    static int MODULO = 1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine())
        {
            sb.append(scanner.nextLine());
        }
        scanner.close();
        System.out.println(getMotzkin(sb.toString(), new HashMap<String, BigInteger>()).mod(BigInteger.valueOf(MODULO)));
    }

    public static BigInteger getMotzkin(String s, Map<String, BigInteger> map)
    {
        int N = s.length();
        if (N <= 1) return BigInteger.ONE;
        if (map.containsKey(s)) return map.get(s);
        BigInteger Cn = BigInteger.ZERO;
        Cn = Cn.add(getMotzkin(s.substring(1), map));
        for (int k = 1; k < N; k++)
        {
            if (complementary(s.charAt(0), s.charAt(k)))
            {
                //System.out.println("Cn for: " + s + " is now: " + Cn.toString());
                Cn = Cn.add(getMotzkin(s.substring(1, k), map).multiply(getMotzkin(s.substring(k+1), map)));
            }
        }
        map.put(s, Cn);
        return Cn;
    }

    public static boolean countsMatch(String s)
    {
        // if (s.length() == 0) return false;
        Map<Character, Integer> counts = new HashMap<>();
        for (char c: s.toCharArray())
        {
            counts.put(c, 1 + counts.getOrDefault(c, 0));
        }
        return counts.get('A') == counts.get('U') && counts.get('C') == counts.get('G');
    }

    public static boolean complementary(char a, char b){
        return ((a == 'A' && b == 'U')
        || (a == 'U' && b == 'A') || (a == 'G' && b == 'C') || (a == 'C' && b == 'G'));
    }

}
