import java.math.BigDecimal;
import java.util.Scanner;

public class Indc {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = 2 * scanner.nextInt();

        scanner.close();

        BigDecimal dom = BigDecimal.valueOf(2).pow(N), sum = BigDecimal.ZERO, prob = BigDecimal.ZERO;

        for (int k = 0; k < N; k++)
        {
            sum = sum.add(choose(N, k));
            prob = BigDecimal.ONE.subtract(sum.divide(dom));
            System.out.print(Math.round(Math.log10(prob.doubleValue()) * 1000.0)/1000.0);
            System.out.print(" ");
        }
    }

    public static BigDecimal choose(int N, int k)
    {
        BigDecimal num = BigDecimal.ONE, dom = BigDecimal.ONE;
        for (int i = 1; i <= k; i++)
        {
            num = num.multiply(BigDecimal.valueOf(N-k + i));
            dom = dom.multiply(BigDecimal.valueOf(i));
        }

        return num.divide(dom);
    }
}
