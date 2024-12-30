import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Cstr {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> taxa = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            taxa.add(scanner.nextLine());
        }
        scanner.close();

        int N = taxa.size();
        Set<String> set = new HashSet<>();
        String first = taxa.get(0);
        for (int pos = 0; pos < first.length(); pos++)
        {
            char c = first.charAt(pos);
            char[] curr = new char[N];
            Arrays.fill(curr, '0');
            int count = 0;
            for (int i = 0; i < N; i++)
            {
                if (taxa.get(i).charAt(pos) == c)
                {
                    curr[i] = '1';
                    count++;
                }   
            }

            if (count > 1 && count < N-1)
            {
                set.add(new String(curr));
            }
        }

        for (String s: set)
        {
            System.out.println(s);
        }
    }

}
