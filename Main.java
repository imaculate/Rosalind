// "static void main" must be defined in a public class.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        StringBuilder s = new StringBuilder();
        // Ctrl + Z ends input
        while (scanner.hasNext())
        {
            s.append(scanner.nextLine());
        }
        int size = s.length(), i = 1, len = 0;
        int[] lps = new int[size];
        while (i < size)
        {
            //System.out.println("LPS at :" + i + " is " + lps[i]);
            if (s.charAt(i) == s.charAt(len))
            {
                len++;
                lps[i] = len;
                i++;
            }
            else
            {
                if (len != 0)
                {
                    
                    len = lps[len-1];
                }
                else
                {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        for (int lp: lps)
        {
            System.out.print(lp + " ");
        }
        
    }
}