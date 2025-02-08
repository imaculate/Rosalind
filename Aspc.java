import java.math.BigInteger;
import java.util.Scanner;

public class Aspc {
    static int MODULO = 1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(), M = scanner.nextInt();
        scanner.close();

        BigInteger result = BigInteger.ZERO;
        for (int i = M; i <= N; i++)
        {
            result = result.add(choose(N, i));
        }

        System.out.println(result.mod(BigInteger.valueOf(MODULO)));
    }

    public static BigInteger choose(int N, int k)
    {
        BigInteger num = BigInteger.ONE, dom = BigInteger.ONE;
        for (int i = 1; i <= k; i++)
        {
            num = num.multiply(BigInteger.valueOf(N-k + i));
            dom = dom.multiply(BigInteger.valueOf(i));
        }

        return num.divide(dom);
    }

}
