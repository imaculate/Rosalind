import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Sgra {

    static TreeMap<Double, Character> map = new TreeMap<Double, Character>()
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
            put(114.04293, 'N');
            put(97.05276, 'P');
            put(128.05858, 'Q');
            put(156.10111, 'R');
            put(87.03203, 'S');
            put(101.04768, 'T');
            put(99.06841, 'V');
            put(186.07931, 'W');
            put(163.06333, 'Y');
        }};

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<Double> list = new ArrayList<>();
        while (scanner.hasNextDouble())
        {
            list.add(scanner.nextDouble());
        }

        scanner.close();

        String peptide = "";
        int start = 0, T = list.size();
        while (start < T)
        {
            char ch = '*';
            int stop = -1;
            outer: for (int i = start; i < T; i++)
            {
                for (int j = i+1; j < T; j++)
                {
                    double d = list.get(j) - list.get(i);
                    Double key = map.floorKey(d);
                    if (key != null && Math.abs(key - d) < 0.0001)
                    {
                        ch = map.get(key);
                        stop = j;
                        break outer;
                    }

                    key = map.ceilingKey(d);
                    if (Math.abs(key - d) < 0.0001)
                    {
                        ch = map.get(key);
                        stop = j;
                        break outer;
                    }
                }
            }

            if (ch == '*') break;

            peptide = peptide + ch;
            start = stop;
        }

        System.out.println(peptide);
    }
}
