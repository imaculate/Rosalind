import java.util.Scanner;

public class Cunr {
    static int MODULO = 1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.close();

        int result = 1, start = 2*N - 5;
        for (int i = start; i >= 1; i-=2 )
        {
            result = (result * i) % MODULO;
        }

         System.out.println(result);
    }
}
