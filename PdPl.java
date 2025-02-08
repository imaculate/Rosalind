import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class PdPl {

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt())
        {
            list.add(scanner.nextInt());
        }

        scanner.close();

        int T = list.size();
        int N = (int)(1 + Math.sqrt(1 + 8*T))/2;

        

    }

}
