import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Order {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] symbols = line.split(" ");
        int n = scanner.nextInt();
        generate(symbols, n, "");
     }

     public static void generate(String[] symbols, int n, String sb)
    {
        // System.out.println("String is now: " + sb + " at index:" + index);
        if (sb.length() == n)
        {
            System.out.println(sb);
            return;
        }

        for (int i = 0; i < symbols.length; i++)
        {
            generate(symbols, n, sb + symbols[i]);
        }
    }
}
