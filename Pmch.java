import java.math.BigInteger;
import java.util.Scanner;

public class Pmch {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String l = scanner.nextLine(), s = scanner.nextLine();

        scanner.close();
        int gc = 0, au = 0;
        for (char c : s.toCharArray())
        {
            int gcg = (c == 'G' || c == 'C') ? 1 : 0;
            gc += gcg;
            au += 1- gcg;
        }

        gc /= 2;
        au /= 2;

        BigInteger res = factorial(au).multiply(factorial(gc));
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
