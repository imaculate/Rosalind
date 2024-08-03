import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Permutation {
    
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int count = 1;
        for (int i = n; i >= 2; i--)
        {
            count *= i;
        }
        System.out.println(count);

        generate(n, new ArrayList<Integer>(), 0);
    }

    public static void generate(int n, List<Integer> sofar, int bitmask)
    {
        if (sofar.size() == n)
        {
            System.out.println(sofar.stream().map(i->i.toString()).collect(Collectors.joining(" ")));
            return;
        }

        for (int i = 1; i <= n; i++)
        {
            int bitpos = 1 << i;
            if ((bitmask & bitpos) == 0)
            {
                //List<Integer> copy = new ArrayList<>(sofar);
                sofar.add(i);
                generate(n, sofar, bitmask | bitpos);
                sofar.remove(sofar.size() - 1);
            }
        }
    }
}
