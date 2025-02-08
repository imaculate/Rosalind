import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.w3c.dom.views.DocumentView;

public class Prsm {

    static Map<Character, Double> map = new HashMap<Character, Double>()
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
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());
        List<String> prots = new ArrayList<String>();
        for (int i = 0; i < N; i++)
        {
            prots.add(scanner.nextLine());
        }

        List<Double> rSet = new ArrayList<>();
        while (scanner.hasNextDouble())
        {
            rSet.add(scanner.nextDouble());
        }
        scanner.close();

        int maxF = 0;
        String maxP = "";
        for (String prot: prots)
        {
            int m = maxMultiplicity(spectrum(prot), rSet);
            if (m > maxF)
            {
                maxF = m;
                maxP = prot;
            }
        }

        System.out.println(maxF);
        System.out.println(maxP);
    }

    public static List<Double> spectrum(String s)
    {
        List<Double> res = new ArrayList<>();
        int N = s.length();
        double d = 0.0;
        for (int i  = 0; i < N; i++)
        {
            d += map.get(s.charAt(i));
            res.add(d);
        }

        d = 0.0;
        for (int i  = N-1; i > 0; i--)
        {
            d += map.get(s.charAt(i));
            res.add(d);
        }

        return res;
    }

    public static int maxMultiplicity(List<Double> S1, List<Double> S2)
    {
        Map<Double, Integer> freqs = new HashMap<>();
        int maxF = 0;
        double peak = 0;

        for (double d1: S1 )
        {
            for (double d2: S2)
            {
                double d = Math.round((d1 - d2)* 100000.0)/100000.0;
                int f = 1 + freqs.getOrDefault(d, 0);
                freqs.put(d, f);
    
                if (f > maxF)
                {
                    maxF = f;
                    peak = d;
                }
            }
        }
        return maxF;
    }

}
