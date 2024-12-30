import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Quartet {
     public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String[] spec = scanner.nextLine().trim().split(" ");
        List<String> table = new ArrayList<>();

        while (scanner.hasNextLine())
        {
            table.add(scanner.nextLine());
        }
        scanner.close();

        int N = spec.length;
        Set<Integer[]> result = new TreeSet<>((Integer[] a, Integer[] b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            if (a[1] != b[1]) return a[1] - b[1];
            if (a[2] != b[2]) return a[2] - b[2];
            return a[3] -b[3];
        });
                for (String s : table)
                {
                    List<Integer> i0 = new ArrayList<>(), i1 = new ArrayList<>();
                    for (int i = 0; i< N; i++)
                    {
                        char c = s.charAt(i);
                        if (c == '0')
                        {
                            i0.add(i);
                        }
                        else if (c == '1')
                        {
                            i1.add(i);
                        }
                    }
        
                    if (i0.size() < 2 || i1.size() < 2) continue;
        
                    for (Integer[] zeros : generate(i0))
                    {
                        for (Integer[] ones : generate(i1))
                        {
                            // {elephant, dog} {rabbit, robot}
                            if (zeros[0] < ones[0])
                            {
                                result.add(new Integer[] {zeros[0], zeros[1], ones[0], ones[1]});
                            }
                            else
                            {
                                result.add(new Integer[] {ones[0], ones[1], zeros[0], zeros[1]});
                            }
                        }
                    }
                }
        
                for (Integer[] res: result)
                {
                    System.out.println("{" + spec[res[0]] + ", " + spec[res[1]] + "} {" + spec[res[2]] + ", " + spec[res[3]] + "}");
                }
            }

    public static List<Integer[]> generate(List<Integer> list)
    {
        List<Integer[]> result = new ArrayList<>();
        if (list.size() == 2)
        {
            result.add(list.toArray(new Integer[2]));
            return result;
        }

        int N = list.size();
        for (int i = 0; i < N; i++)
        {
            for (int j = i+1; j < N; j++)
            {
                result.add(new Integer[] {list.get(i), list.get(j)});
            }
        }
        return result;
    }

}
