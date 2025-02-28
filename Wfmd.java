import java.math.BigInteger;
import java.util.Scanner;

public class Wfmd {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt(), m = scanner.nextInt(), G = scanner.nextInt(), K = scanner.nextInt(), T = 2*N;
        scanner.close();

        double p = 1 - (double)(m)/T;
        double[] curr = new double[T+1];
        for (int i = 1; i <= T; i++)
        {
            curr[i] = choose(T, i).doubleValue() * Math.pow(p, i) * Math.pow(1-p, T-i);
        }

        for (int g = 1; g < G; g++)
        {
            double[] next = new double[T+1];
            // exactly t
            for (int t = 1; t <= T; t++)
            {
                for (int i = 1; i <= T; i++)
                {
                    double q = (double)(i)/ T, qI = choose(T, t).doubleValue() * Math.pow(q, t) * Math.pow(1-q, T-t);
                    next[t] += curr[i] * qI;
                }
            }
            curr = next;
        }

        double sum = 0;
        for (int i = K; i <= T; i++)
        {
            sum += curr[i];
        }

        System.out.println(Math.round(sum * 1000.0)/ 1000.0);

        
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
