import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;

public class Asmq {
     public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            list.add(scanner.nextLine());
        }

        scanner.close();
        Collections.sort(list, (a, b) -> a.length() - b.length());
        int sum = list.stream().mapToInt(s -> s.length()).sum();

        int V50 =  sum/2, V25 = sum/4, N50 = -1, N75 = -1, r = 0;
        for (String s: list)
        {
            r += s.length();
            if (r >= V25 && N75 < 0) N75 = s.length();
            if (r >= V50 && N50 < 0) N50 = s.length();

            if (N50 >= 0 && N75 >= 0) break;
        }

        System.out.println(N50 + " " + N75);

    }

}
