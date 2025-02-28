import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Foun {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt(), m = scanner.nextInt(), T = 2*N;
        List<Integer> list = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        scanner.close();
        int K = list.size();

        double[][] prev = new double[K][T+1];
        for (int j = 0; j < K; j++)
        {
            double p = (double)list.get(j)/ T, sum = 1.0;
            for (int c = 1; c <= T; c++)
            {
                prev[j][c] = choose(T, c).doubleValue() * Math.pow(p, c) * Math.pow(1-p, T-c);
                sum -= prev[j][c];
            }
            System.out.print(Math.log10(sum) + " ");
        }
        System.out.println("");

        for (int i = 1; i < m; i++)
        {
            double[][] next = new double[K][T+1];
            for (int j = 0; j < K; j++)
            {

                double sum = 1.0;
                for (int t = 1; t <= T; t++)
                {
                    for (int c = 1; c <= T; c++)
                    {
                        double q = (double)(c)/ T, qI = choose(T, t).doubleValue() * Math.pow(q, t) * Math.pow(1-q, T-t);
                        next[j][t] += prev[j][c] * qI;
                    }
                    sum -= next[j][t];
                }
                System.out.print(Math.log10(sum) + " ");

            }
            prev = next;
            System.out.println("");
        }

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
