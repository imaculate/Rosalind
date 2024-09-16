import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LocalAlignment {
    static int GAP_START = 11;
    static int GAP_EXTEND = 1;

    static int[][] pam250 = new int[][]
    {
       { 2, -2,  0,  0, -3,  1, -1, -1, -1, -2, -1,  0,  1,  0, -2,  1,  1,  0, -6, -3},
 {-2, 12, -5, -5, -4, -3, -3, -2, -5, -6, -5, -4, -3, -5, -4,  0, -2, -2, -8,  0},
  {0, -5,  4,  3, -6,  1,  1, -2,  0, -4, -3,  2, -1,  2, -1,  0,  0, -2, -7, -4},
  {0, -5,  3,  4, -5,  0,  1, -2,  0, -3, -2,  1, -1,  2, -1,  0,  0, -2, -7, -4},
 {-3, -4, -6, -5,  9, -5, -2,  1, -5,  2,  0, -3, -5, -5, -4, -3, -3, -1,  0,  7},
  {1, -3,  1,  0, -5,  5, -2, -3, -2, -4, -3,  0,  0, -1, -3,  1,  0, -1, -7, -5},
 {-1, -3,  1,  1, -2, -2,  6, -2,  0, -2, -2,  2,  0, 3,  2, -1, -1, -2, -3,  0},
 {-1, -2, -2, -2,  1, -3, -2,  5, -2,  2,  2, -2, -2, -2, -2, -1,  0,  4, -5, -1},
 {-1, -5,  0,  0, -5, -2,  0, -2,  5, -3,  0,  1, -1,  1,  3,  0,  0, -2, -3, -4},
 {-2, -6, -4, -3,  2, -4, -2,  2, -3,  6,  4, -3, -3, -2, -3, -3, -2,  2, -2, -1},
 {-1, -5, -3, -2 , 0 ,-3 ,-2, 2 , 0, 4,  6, -2, -2, -1,  0, -2, -1,  2, -4, -2},
  {0, -4,  2,  1, -3,  0,  2, -2,  1, -3, -2,  2,  0,  1,  0,  1,  0, -2, -4, -2},
  {1, -3, -1, -1, -5,  0,  0, -2, -1, -3, -2,  0,  6,  0,  0,  1,  0, -1, -6, -5},
  {0, -5,  2,  2, -5, -1,  3, -2,  1, -2, -1,  1,  0,  4,  1, -1, -1, -2, -5, -4},
 {-2, -4, -1, -1, -4, -3,  2, -2,  3, -3,  0, 0,  0,  1,  6,  0, -1, -2,  2, -4},
  {1,  0,  0,  0, -3,  1, -1, -1,  0, -3, -2,  1,  1, -1,  0,  2,  1, -1, -2, -3},
  {1, -2,  0,  0, -3,  0, -1,  0,  0, -2, -1,  0,  0, -1, -1,  1,  3,  0, -5, -3},
  {0, -2, -2, -2, -1, -1, -2,  4, -2,  2,  2, -2, -1, -2, -2, -1,  0,  4, -6, -2},
 {-6, -8, -7, -7,  0, -7, -3, -5, -3, -2, -4, -4, -6, -5,  2, -2, -5, -6, 17, 0},
 {-3,  0, -4, -4,  7, -5,  0, -1, -4, -1, -2, -2, -5, -4, -4, -3, -3, -2,  0, 10}};


    static Map<Character, Integer> charToIndex = new HashMap<Character, Integer>()
    {{
        put('A', 0);
        put('C', 1);
        put('D', 2);
        put('E', 3);
        put('F', 4);
        put('G', 5);
        put('H', 6);
        put('I', 7);
        put('K', 8);
        put('L', 9);
        put('M', 10);
        put('N', 11);
        put('P', 12);
        put('Q', 13);
        put('R', 14);
        put('S', 15);
        put('T', 16);
        put('V', 17);
        put('W', 18);
        put('Y', 19);
    }};

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

        int sLen = s.length(), tLen = t.length();
        int[][] scores = new int[sLen + 1][tLen + 1], dirs = new int[sLen + 1][tLen + 1];

        for (int i = 0; i <= sLen; i++)
        {
            scores[i][0] = -5 * i;
        }

        for (int j = 0; j <= tLen; j++)
        {
            scores[0][j] = -5 * j;
        }

        int maxScore = -1, maxI = 0, maxJ = 0;
        for (int i = 1; i <= sLen; i++)
        {
            for (int j = 1; j <= tLen; j++)
            {
                
                int subScore = scores[i-1][j-1] + pam250[charToIndex.get(s.charAt(i-1))][charToIndex.get(t.charAt(j-1))];
                List<Integer> options = new ArrayList<>(List.of(scores[i-1][j] - 5, scores[i][j-1] - 5, subScore, 0));
                scores[i][j] = Collections.max(options);
                dirs[i][j] = options.indexOf(scores[i][j]);


                if (scores[i][j] > maxScore)
                {
                    maxScore = scores[i][j];
                    maxI = i;
                    maxJ = j;
                }

                maxScore = Math.max(maxScore, scores[i][j]);
            }
        }

        System.out.println("Scores: ");
        /*for (int[] arr: dirs)
        {
            System.out.println(Arrays.toString(arr));
        }*/
        

        System.out.println("The score is: " + maxScore);
        int i = maxI, j = maxJ, dir = dirs[i][j];
        while (dir != 3 && (i*j) != 0)
        {
            if (dir == 0)
            {
                i--;
            }
            else if (dir == 1)
            {
                j--;
            }
            else if(dir == 2)
            {
                i--;
                j--;
            }
            dir = dirs[i][j];

        }

        System.out.println(s.substring(i, maxI));
        System.out.println(t.substring(j, maxJ));


    }
}
