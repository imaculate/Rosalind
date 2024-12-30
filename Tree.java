import java.util.Arrays;
import java.util.Scanner;

public class Tree {
    static class Dsu
    {
        int N, groups;
        int[] parents, sizes;

        public Dsu(int N)
        {
            this.N = this.groups = N;
            parents = new int[N+1];
            sizes = new int[N+1];
            Arrays.fill(sizes, 1);
            for (int i = 0; i < N; i++)
            {
                parents[i] = i;
            }
        }

        public int find(int i)
        {
            while (i != parents[i])
            {
                i = parents[i];
            }
            return i;
        }

        public void union(int i, int j)
        {
            int pi = find(i), pj = find(j);
            if (pi == pj) return;
            groups--;
            if (sizes[pi] > sizes[pj])
            {
                parents[pj] = pi;
                sizes[pi] += sizes[pj];
            }
            else
            {
                parents[pi] = pj;
                sizes[pj] += sizes[pi];
            }
        }

    }

    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        Dsu dsu = new Dsu(N);

        while (scanner.hasNextInt())
        {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            dsu.union(i, j);
        }
        scanner.close();
        System.out.println(dsu.groups - 1);
    }
}
