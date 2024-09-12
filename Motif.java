import java.util.Scanner;

public class Motif {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // initial label
        StringBuilder sb = new StringBuilder();
        String line = "", s = "", t = "";

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if (line.startsWith(">"))
            {
                s = sb.toString();
                sb.setLength(0);
            }
            else
            {
                sb.append(line);
            }
        }

        t = sb.toString();

        scanner.close();

        findSub(s, t);
        /*for (String s: in)
        {
            System.out.println(s);
        }*/

    }

    public static void findSub(String s, String t)
    {
        int sI = 0, tI = 0;
        while (tI < t.length())
        {
            //System.out.println("Finding " + t.charAt(tI));
            while (sI < s.length() && s.charAt(sI) != t.charAt(tI)) 
            {
                sI++;
            }
            //if (sI < s.length()) System.out.println("Finding " + s.charAt(sI));
            System.out.print((sI+1) + " ");
            tI++;
            sI++;
        }
    }

}
