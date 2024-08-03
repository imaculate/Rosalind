import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class PMass {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        double result = 0;
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
        for (char c: line.toCharArray())
        {
            result += map.get(c);
        }

        System.out.println(result);
    }
    
}
