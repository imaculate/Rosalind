import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Conv {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        double[] S1 = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(s -> Double.parseDouble(s)).toArray(), S2 = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(s -> Double.parseDouble(s)).toArray();
        scanner.close();

        Map<Double, Integer> map = new HashMap<>();
        int maxF = 0;
        double peak = 0;

        for (double d1: S1 )
        {
            for (double d2: S2)
            {
                double d = Math.round((d1 - d2)* 100000.0)/100000.0;
                int f = 1 + map.getOrDefault(d, 0);
                map.put(d, f);
    
                if (f > maxF)
                {
                    maxF = f;
                    peak = d;
                }
            }
        }

        System.out.println(maxF);
        System.out.println(peak);
    }

}
