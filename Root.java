import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Root
{
    static int MODULO = 1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.close();

        BigInteger[][] cache = new BigInteger[N+1][N+1];
        Arrays.fill(cache[0], BigInteger.ZERO);
        for (int i = 1; i <= N; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                if (j == 0 || i==j) 
                {
                    cache[i][j] = BigInteger.ONE;
                }
                else
                {
                    cache[i][j] = cache[i - 1][j - 1].add(cache[i - 1][j]);
                }
            }
        }

        BigInteger[] counts = new BigInteger[N+1];
        counts[0] = BigInteger.ZERO;
        counts[1] = BigInteger.ONE;
        for (int i = 2; i <= N; i++)
        {
            counts[i] = BigInteger.ZERO;
            for (int f = 1; f <= i/2; f++)
            {
                counts[i] = counts[i].add(counts[f].multiply(counts[i-f]).multiply(getCount(i, f, cache))).mod(BigInteger.valueOf(MODULO));
            }
        }

        System.out.println(counts[N].mod(BigInteger.valueOf(MODULO)));
        
    }

    public static BigInteger getCount(int n, int i, BigInteger[][] cache)
    {
        if (n == i *2) return cache[n][i].divide(BigInteger.TWO);
        return cache[n][i];
    }

}
