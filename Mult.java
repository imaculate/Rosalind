import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class Mult {
    public static int MIN_SCORE = -1000000;
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // initial label
        StringBuilder sb = new StringBuilder();
        String[] dna = new String[4];
        String line = "";
        int idx = 0;

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if (line.startsWith(">"))
            {
                dna[idx++] = sb.toString();
                sb.setLength(0);
            }
            else
            {
                sb.append(line);
            }
        }

        dna[idx++] = sb.toString();

        // System.out.println("Processing " + Arrays.toString(dna));

        scanner.close();

        int dims = dna.length;
        int l1 = dna[0].length(), l2 = dna[1].length(), l3 = dna[2].length(), l4 = dna[3].length();
        int[][][][] table = new int[l1 + 1][l2 + 1][l3 + 1][l4 + 1], backTrack = new int[l1 + 1][l2 + 1][l3 + 1][l4 + 1];


        int dirsCount = (int)Math.pow(2, dims);
        int[][] dirs = new int[dirsCount][dims];
        for (int i = 1; i < dirsCount; i++)
        {
            int ix = 3, curr = i;
            while (curr > 0)
            {
                dirs[i][ix] = -1 * (curr & 1);
                curr >>= 1;
                ix--;
            }
        }

        List<Integer> options = null;
        for (int i = 0; i <= l1; i++)
        {
            for (int j = 0; j <= l2; j++)
            {
                for (int k = 0; k <= l3; k++)
                {
                    for (int l = 0; l <= l4; l++)
                    {
                        if (i == 0 && j == 0 && k == 0 && l == 0) continue;

                        int[] pos = new int[] {i,j,k,l};
                        int[] scores = new int[dirsCount];
                        scores[0] = MIN_SCORE;
                        for (int dx = 1; dx < dirsCount; dx++)
                        {
                            int[] dir = dirs[dx];
                            List<Character> chars = new ArrayList<Character>();
                            boolean validMove = true;
                            for (int ix = 0; ix < dims; ix++)
                            {
                                int prev = pos[ix] + dir[ix];
                                if (prev < 0)
                                {
                                    validMove = false;
                                    break;
                                }
                                char c = (dir[ix] == -1) ? dna[ix].charAt(prev): '-';
                                chars.add(c);
                            }
                            scores[dx] = (validMove) ? table[i + dir[0]][j + dir[1]][k + dir[2]][l + dir[3]] + score(chars) : MIN_SCORE;  
                        }
                        //System.out.println("Scores at " + Arrays.toString(pos) + " are: " + Arrays.toString(scores));
                        options = Arrays.stream(scores).boxed().toList();
                        table[i][j][k][l] = Collections.max(options);
                        backTrack[i][j][k][l] = options.indexOf(table[i][j][k][l]);
                    }
                }
            }
        }

        //System.out.println("Table is ready");

        System.out.println(table[l1][l2][l3][l4]);
        StringBuilder[] sbArr = new StringBuilder[dims];
        for (int i = 0; i < dims; i++)
        {
            sbArr[i] = new StringBuilder();
        }
        int i = l1, j = l2, k = l3, l = l4, move = backTrack[l1][l2][l3][l4];
        while (i > 0 || j > 0 || k > 0 || l > 0)
        {
            int[] dir = dirs[move];
            sbArr[0].append((dir[0] == -1) ? dna[0].charAt(i-1): '-');
            sbArr[1].append((dir[1] == -1) ? dna[1].charAt(j-1): '-');
            sbArr[2].append((dir[2] == -1) ? dna[2].charAt(k-1): '-');
            sbArr[3].append((dir[3] == -1) ? dna[3].charAt(l-1): '-');
            
            i += dir[0];
            j += dir[1];
            k += dir[2];
            l += dir[3];

            move = backTrack[i][j][k][l];

        }

        for (StringBuilder sbA: sbArr)
        {
            System.out.println(sbA.reverse().toString());
        }
        
    }

    public static int score(List<Character> chars)
    {
        int score = 0, N = chars.size();
        for (int i = 0; i < N; i++)
        {
            for (int j = i+1; j < N; j++)
            {
                Character a = chars.get(i), b = chars.get(j);
                score += (a == b) ? 0 : -1;
            }
        }
        return score;
    }

}
