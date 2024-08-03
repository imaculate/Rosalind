import java.util.Scanner;

public class LIS {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++)
        {
            nums[i] = scanner.nextInt();
        }

        //LIS
        int[][] table = new int[n][2];
        int[] longest = new int[2];

        // LDS
        int[][] table2 = new int[n][2];
        int[] longest2 = new int[2];

        for (int i = 0; i < n; i++)
        {
            table[i][1] = -1;
            table2[i][1] = -1;
        }

        for (int i = n-2; i >= 0; i--)
        {
            for (int j = i+1; j < n; j++)
            {
                if (nums[j] > nums[i])
                {
                    if (table[i][0] < 1 + table[j][0])
                    {
                        table[i][0] = 1 + table[j][0];
                        table[i][1] = j;
                    }
                    if (table[i][0] > longest[0]){
                        longest[0] = table[i][0];
                        longest[1] = i;

                    }
                }

                if (nums[j] < nums[i])
                {
                    if (table2[i][0] < 1 + table2[j][0])
                    {
                        table2[i][0] = 1 + table2[j][0];
                        table2[i][1] = j;
                    }
                    if (table2[i][0] > longest2[0]){
                        longest2[0] = table2[i][0];
                        longest2[1] = i;

                    }
                }
            }
        }

        // LIS
        for (int i = longest[1] ; i != -1 ; i = table[i][1])
        {
            System.out.print(nums[i] + " ");
        }
        System.out.println();

        // LDS
        for (int i = longest2[1] ; i != -1 ; i = table2[i][1])
        {
            System.out.print(nums[i] + " ");
        }


    }
    
}
