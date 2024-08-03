import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Offspring {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        double result = scanner.nextInt() + scanner.nextInt() + scanner.nextInt() + (0.75 * scanner.nextInt()) + (0.5 * scanner.nextInt());
        result *= 2;
        System.out.println(result);
    }
}
