import java.util.Scanner;

public class Subsets {
    static int MODULO = 1000000;
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.close();

        int result = 1;
        if (N <= 20)
        {
            result = (1 << N) % MODULO;
        }
        else
        {
            while (N > 0)
            {
                result = (result << 1) % 1000000;
                N-= 1;
            }
        }
        System.out.println(result);
    }

}
