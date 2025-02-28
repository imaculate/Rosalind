import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ebin {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        List<Double> list = new ArrayList<Double>();
        while (scanner.hasNextDouble()) {
            list.add(scanner.nextDouble());
        }
        scanner.close();

        for (Double p: list)
        {
            /*double e = 0.0;
            for (int i = 1; i <= N; i++)
            {
                e += i * combs[i] * Math.pow(p, i) * Math.pow(1-p, N-i);
            }*/

            System.out.print((N* p) + " ");
        }
    }

}
