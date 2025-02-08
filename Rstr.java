import java.util.Scanner;

public class Rstr {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        double gc = scanner.nextDouble();
        scanner.nextLine();
        String seq = scanner.nextLine();

        scanner.close();
        int gc2 = 0;
        for (char c : seq.toCharArray())
        {
            if (c == 'G' || c == 'C') gc2++;
        }

        double prob = Math.pow(((1 - gc) / 2), seq.length() - gc2) * Math.pow((gc / 2), gc2);
        double comp = Math.pow(1 - prob, N); 
        double res = Math.round((1 - comp) * 1000.0)/1000.0;
        System.out.println(res);
    }
}
