import java.math.BigInteger;
import java.util.Scanner;

public class Alleles {
    
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt(), n = scanner.nextInt();

        int all = (int)Math.pow(2 , k);
        //System.out.println("All: " + all);
        // prob that i kids are mixed = (all Choose i) * (0.25 ^ i) * (0.75 ^ (all -i))
        // sum them all up
        double result = 0;
        for (int i  = n; i <= all ; i++)
        {
            double t = choose(all, i) * Math.pow(0.25, i) * Math.pow(0.75, (all-i));
            //System.out.println("Partial: " + t);
            result += t;
        }

        System.out.println(result);
    }

    public static long choose(int n, int k)
    {
        BigInteger num = BigInteger.valueOf(1), dum = BigInteger.valueOf(1);
        for (int i = 0; i < k; i++)
        {
            dum = dum.multiply(BigInteger.valueOf(i + 1));
            num = num.multiply(BigInteger.valueOf(n - i));
        }
        long result =  num.divide(dum).longValue();
        //System.out.println(n + " choose " + k + " is: " + result);
        return result;
    }
    
}
