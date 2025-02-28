import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Rnas {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine())
        {
            sb.append(scanner.nextLine());
        }
        
        scanner.close();
        
        System.out.println(rnas(sb.toString(), new HashMap<>()));

    }

    public static BigInteger rnas(String s, Map<String, BigInteger> cache)
    {
        if (s.length() < 1) return BigInteger.ONE;
        if (cache.containsKey(s)) return cache.get(s);

        BigInteger res = rnas(s.substring(1), cache); // exclude s(0)
        for (int m = 4; m < s.length(); m++)
        {
            if (!complementary(s.charAt(0), s.charAt(m))) continue;
            res =  res.add(rnas(s.substring(1, m), cache).multiply(rnas(s.substring(m+1), cache)));
        }
        cache.put(s,res);
        return res;
    }

    public static boolean complementary(char a, char b)
    {
        return (a == 'A' && b == 'U') ||
        (a == 'G' && b == 'C') || (a == 'C' && b == 'G') || (a == 'U' && b == 'A') || (a == 'G' && b == 'U') || (a == 'U' && b == 'G');
    }

}
