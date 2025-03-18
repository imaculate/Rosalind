import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Scanner;

public class Ling {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine())
        {
            sb.append(scanner.nextLine());
        }
        scanner.close();

        SuffixTree tree = new SuffixTree();
        int N = sb.length();
        System.out.println("Word of length: " + N + " being added to tree");
        tree.addWord(sb.toString());

        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Integer, Integer> edge: tree.edges.values())
        {
            if (edge.getValue() == N+1)
            {
                sum = sum.add(BigDecimal.valueOf(N - edge.getKey()));
                // (edge.getKey(), N)
            }
            else
            {
                sum = sum.add(BigDecimal.valueOf(edge.getValue() - edge.getKey()));
            }
        }

        int kStop = (int)Math.floor(Math.log(N+1)/Math.log(4));
        BigDecimal m = BigDecimal.valueOf(0);
        BigDecimal fP = BigDecimal.valueOf(4);
        for (int k = 1; k <= kStop; k++)
        {
            m = m.add(fP);
            fP = fP.multiply(BigDecimal.valueOf(4));
        }

        for (int k = kStop + 1; k <= N; k++)
        {
            m = m.add(BigDecimal.valueOf(N - k + 1));
        }

        

        System.out.println(sum.divide(m, 5,  RoundingMode.CEILING));

    }

}
