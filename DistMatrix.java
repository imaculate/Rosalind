import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DistMatrix {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String line = "";
        List<String> list = new ArrayList<>();
        scanner.nextLine(); 
        while (scanner.hasNextLine())
        {
            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }
            list.add(sb.toString());
            sb.setLength(0);
        }
        scanner.close();

        int N = list.size();
        double[][] table = new double[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = i+1; j < N; j++)
            {
                table[i][j] = table[j][i] = compute(list.get(i), list.get(j));
            }
        }

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                System.out.printf("%.5f ", table[i][j]);
            }
            System.out.println();
        }
    }

    public static double compute(String s, String t)
    {
        int N = s.length(), dist = 0;
        for (int i = 0; i < N; i++)
        {
            if (s.charAt(i) != t.charAt(i)) dist++;
        }
        return (double)dist/N;
    }
}
