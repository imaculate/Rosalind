import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Afrq {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<Double> list = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            list.add(scanner.nextDouble());
        }
        scanner.close();

        for (double d: list)
        {
            System.out.print(Math.round(process(d) * 1000.0)/1000.0 + " ");
        }
    }

    public static double process(double d)
    {
        double s = Math.sqrt(d);
        return d + 2*s* (1-s);
    }
}
