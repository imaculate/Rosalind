import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProteinSpectrum {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<Double> list = new ArrayList();

        Map<Character, Double> map = new HashMap<Character, Double>()
        {{
            
            put('A', 71.03711);
            put('C', 103.00919);
            put('D', 115.02694);
            put('E', 129.04259);
            put('F', 147.06841);
            put('G', 57.02146);
            put('H', 137.05891);
            put('I', 113.08406);
            put('K', 128.09496);
            put('L', 113.08406);
            put('M', 131.04049);
            put('N', 114.04293);
            put('P', 97.05276);
            put('Q', 128.05858);
            put('R', 156.10111);
            put('S', 87.03203);
            put('T', 101.04768);
            put('V', 99.06841);
            put('W', 186.07931);
            put('Y', 163.06333);
        }};

        while (scanner.hasNextDouble())
        {
            list.add(scanner.nextDouble());
        }

        for (int i = 1; i < list.size(); i++)
        {
            double d = list.get(i) - list.get(i-1);
            for (Character c: map.keySet())
            {
                if (Math.abs(map.get(c) - d) < 0.0001)
                {
                    System.out.print(c);
                    break;
                }
            }
        }

    }
    
}
