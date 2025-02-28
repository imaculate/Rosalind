import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cat {
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

        BigInteger r = getCatalan(sb.toString(), new HashMap<>()).mod(BigInteger.valueOf(MODULO));
        System.out.println(r);
    }

    public static BigInteger getCatalan(String s, Map<String, BigInteger> map)
    {
        int N = s.length();
        if (N/2 <= 1) return BigInteger.ONE;
        if (map.containsKey(s)) return map.get(s);
        BigInteger Cn = BigInteger.ZERO;

        for (int k = 1; k < N; k+= 2)
        {
            String sub = s.substring(1, k);
            //System.out.println("Partitioning " + s + " at " + sub);
            if (countsMatch(sub) && complementary(s.charAt(0), s.charAt(k)))
            {
                //System.out.println("Cn for: " + s + " is now: " + Cn.toString());
                Cn = Cn.add(getCatalan(sub, map).multiply(getCatalan(s.substring(k+1), map)));
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
