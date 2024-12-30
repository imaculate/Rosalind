import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sets {

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());
        String lineA = sc.nextLine();
        // System.out.println("A: " + lineA);
        lineA = lineA.substring(1, lineA.length() - 1);
        String lineB = sc.nextLine();
        // System.out.println("B: " + lineB);
        lineB = lineB.substring(1, lineB.length() - 1);
        sc.close();

        Set<Integer> A = Arrays.stream(lineA.split(",")).map(t -> Integer.parseInt(t.trim())).collect(Collectors.toSet());
        Set<Integer> B = Arrays.stream(lineB.split(",")).map(t -> Integer.parseInt(t.trim())).collect(Collectors.toSet());
        Set<Integer> U = IntStream.rangeClosed(1, N).boxed().collect(Collectors.toSet());
        
        //A∪B, A∩B, A-B, B-A, A', B'
        // AUB

        union(A, B); // AUB
        intersect(A, B); // A^B
        diff(A, B);  // A- B
        diff(B, A);  // B - A
        diff(U, A); // A'
        diff(U, B); // B'
        
    }

    public static void union(Set<Integer> A, Set<Integer> B)
    {
        Set<Integer> union = new HashSet<>(A);
        union.addAll(B);
        printSet(union);
    }

    public static void intersect(Set<Integer> A, Set<Integer> B)
    {
        Set<Integer> intersect = new HashSet<>(A);
        intersect.retainAll(B);
        printSet(intersect);
    }

    public static void diff(Set<Integer> A, Set<Integer> B)
    {
        Set<Integer> diff = new HashSet<>(A);
        diff.removeAll(B);
        printSet(diff);
    }

    public static void printSet(Set<Integer> set)
    {
        String s = set.toString();
        s = "{" + s.substring(1, s.length() - 1) + "}";
        System.out.println(s);
    }

}
