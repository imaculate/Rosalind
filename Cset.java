import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cset {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> set = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            set.add(scanner.nextLine());
        }
        scanner.close();

        int N = set.size();
        int[] counts = new int[N];
        for (int i = 0; i< N; i++)
        {
            for (int j = i+1; j< N; j++)
            {
                String a = set.get(i),  b = set.get(j);
                boolean b1 = intersectNonEmpty(a, b, false, false);
                boolean b2 = intersectNonEmpty(a, b, true, false);
                boolean b3 = intersectNonEmpty(a, b, false, true);
                boolean b4 = intersectNonEmpty(a, b, true, true);

                // System.out.println(String.format("Inconsistency between: %s and %s : %s, %s,%s, %s", a, b, b1, b2, b3, b4));
                // all are non empty
                if (b1 && b2 && b3 && b4)
                {
                    counts[i]++;
                    counts[j]++;
                }
            }
        }

        int maxI = 0;
        for (int i = 0; i < counts.length; i++) {
            maxI = counts[i] > counts[maxI] ? i : maxI;
        }

        System.out.println("Maximum conflicts found at:" + set.get(maxI));
        set.remove(set.get(maxI));

        for (String s: set)
        {
            System.out.println(s);
        }

    }

    public static boolean intersectNonEmpty(String a, String b, boolean invA, boolean invB)
    {
        char chA = invA ? '0' : '1', chB = invB ? '0' : '1';
        for (int i = 0; i < a.length(); i++)
        {
            if (a.charAt(i) == chA && b.charAt(i) == chB) return true;
        }

        return false;
    }
}

