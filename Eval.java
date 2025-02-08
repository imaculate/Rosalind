import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Eval {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();
        String seq = scanner.nextLine();
        List<Double> gcs = new ArrayList<>();

        while (scanner.hasNextDouble()) {
            gcs.add(scanner.nextDouble());
        }

        scanner.close();
        int gc2 = 0;
        for (char c : seq.toCharArray())
        {
            if (c == 'G' || c == 'C') gc2++;
        }

        int starts = N + 1 - seq.length();
        for (Double gc: gcs)
        {
            double prob = Math.pow(((1 - gc) / 2), seq.length() - gc2) * Math.pow((gc / 2), gc2);
            double comp = starts * prob;
            double res = Math.round(comp * 1000.0)/1000.0;
            System.out.print(res + " ");
        }
    }


}
