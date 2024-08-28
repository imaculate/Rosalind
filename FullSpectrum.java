import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FullSpectrum {
    public static void main(String[] args)
    {

        Map<Double, Character> map = new HashMap<Double, Character>()
        {{
            
            put(71.03711, 'A');
            put(103.00919, 'C');
            put(115.02694, 'D');
            put(129.04259, 'E');
            put(147.06841, 'F');
            put(57.02146, 'G');
            put(137.05891, 'H');
            put(113.08406, 'I');
            put(128.09496, 'K');
            put(113.08406, 'L');
            put(131.04049, 'M');
            put(114.04293,'N');
            put(97.05276, 'P');
            put(128.05858, 'Q');
            put(156.10111, 'R');
            put(87.03203, 'S');
            put(101.04768,'T');
            put(99.06841, 'V');
            put(186.07931,'W');
            put(163.06333, 'Y');
        }};

        final Scanner scanner = new Scanner(System.in);
        List<Double> list = new ArrayList<Double>();
        while (scanner.hasNextDouble())
        {
            list.add(scanner.nextDouble());
        }

        int n = (list.size() - 3) / 2;
        double parent = list.get(0);
        

    }

    public List<Double> helper(int n, double parent, List<Double> in, int start, List<String> peptide)
    {
        if (peptide.size() == n) return peptide;
        int end = 0;
        List<Character> cList = null;

        for (int i = start; i < n-1; i++)
        {
            for (int j = i+1; j < n; j++)
            {
                cList = map.getOrDefault(round(in.get(j)-in.get(i), 5), null);
                if (cList != null )
                {
                    end = j;
                    break;
                }
            }
        }

        List<String> next = new ArrayList<>();
        if (cList != null)
        {
            for (char c : cList)
            {
                for (String s : peptide)
                {
                    next.add(s.append(c));
                }
            }

            return helper(n, in, end+1, next);
        }
    }
    
}
