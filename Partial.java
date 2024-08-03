import java.util.Scanner;

public class Partial {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        long result = 1;
        for (int i = n; i > n-k; i--)
        {
            result = (result * i) % 1000000;
        }
        System.out.println(result);
     }
}
