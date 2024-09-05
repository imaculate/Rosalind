import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ration {
    static Map<Character, Character> transMap = Map.of('A', 'G', 'G', 'A', 'C', 'T', 'T','C');
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String line = "";
        List<String> in = new ArrayList<>();
        while (scanner.hasNextLine()) {
            while (scanner.hasNextLine()) 
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }

            if (!sb.isEmpty()) { in.add(sb.toString()); }
            sb.setLength(0);
        }

        scanner.close();

        System.out.println(transRatio(in));

    }

    public static double transRatio(List<String> list)
    {
        String s1 = list.get(0), s2 = list.get(1);
        System.out.println("Find ratio between: " + s1 + " and " + s2);
        int N = s1.length();
        double transitions = 0, transversions = 0;

        for (int i = 0; i < N; i++)
        {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if (c1 == c2) continue;

            if (isTransition(c1, c2))
            {
                //System.out.println("Transition between: " + c1 + " and " + c2);
                transitions++;
            }
            else
            {
                //System.out.println("Transversion between: " + c1 + " and " + c2);
                transversions++;
            }
        }
        return transitions/transversions;
    }

    public static boolean isTransition(char c1, char c2)
    {
        return c1 == transMap.get(c2);
    }
}
