import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Mmch {
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
        Map<Character, Integer> counts = new HashMap<Character, Integer>();
        for (char c : sb.toString().toCharArray())
        {
            counts.put(c, 1 + counts.getOrDefault(c, 0));
        }
        int a = counts.getOrDefault('A', 0), u = counts.getOrDefault('U', 0), g = counts.getOrDefault('G', 0), c = counts.getOrDefault('C', 0);

        //System.out.println("Counts: " + a + ", " + u + ", " + g + ", " + c);
        int min_au = Math.min(a , u), max_au = Math.max(a , u);
        int min_gc = Math.min(g , c), max_gc = Math.max(g ,c);

        BigInteger res = factorial(max_gc).divide(factorial(max_gc - min_gc)).multiply(factorial(max_au).divide(factorial(max_au - min_au)));
        System.out.println(res);
    }

    public static BigInteger factorial (int N)
    {
        BigInteger r = BigInteger.ONE;
        for (int i = 2; i <= N; i++)
        {
            r = r.multiply(BigInteger.valueOf(i));
        }
        return r;
    }


}