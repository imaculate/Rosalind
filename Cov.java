import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Cov {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            list.add(scanner.nextLine());
        }
        scanner.close();

       
        System.out.println(superString(list));
    }

    public static String superString(List<String> list)
    {
        Map<String, String> map = new HashMap<>();// prefix <-> suffix
        int sLen = list.get(0).length();
        for (String s: list)
        {
            map.put(s.substring(0, sLen - 1), s.substring(1));
        }

        StringBuilder sb = new StringBuilder();

        String prefix  = (String)map.keySet().toArray()[0];
        while (sb.length() < list.size())
        {
            String suffix = map.get(prefix);
            sb.append(suffix.charAt(sLen - 2)); // append last character
            prefix = map.get(prefix);
        }

        return sb.toString();
    }
}
