import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Kmer {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // rid the label

        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        scanner.close();

        for (Integer count: kmerComposition(sb.toString()))
        {
            System.out.print(count + " ");
        }

    }

    public static int[] kmerComposition(String s)
    {
        char[] chars = new char[] {'A', 'C', 'G', 'T'};
        int k = chars.length, rIndex = 0;

        int[] result = new int[(int)Math.pow(k,k)];
        for (int i = 0; i < k; i++)
        {
            for (int j = 0; j < k; j++)
            {
                for (int m = 0; m < k; m++)
                {
                    for (int n = 0; n < k; n++)
                    {
                        String mer = String.valueOf(chars[i]) + chars[j] + chars[m] + chars[n];
                        int count = 0, index = s.indexOf(mer);
                        while (index != -1)
                        {
                            count++;
                            index++;
                            index = s.indexOf(mer, index);
                        }
                        //System.out.println("Kmer: " + mer + " has count: " + count);

                        result[rIndex] = count;
                        rIndex++;
                    }
                }
            }
        }
        return result;
    }

}
