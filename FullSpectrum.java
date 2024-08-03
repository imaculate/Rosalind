import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FullSpectrum {
    public static void main(String[] args)
    {

        Map<Double, Character> map = new HashMap<Double, Character>()
        {{
            
            put(71.03711, 'A');
            put(103.00919, 'C');
            put(115.02694, 'D');
            put(129.04259, 'E');
            put(147.06841, 'F');
            put(57.02146, 'G');
            put(137.05891, 'H');
            put(113.08406, 'I');
            put(128.09496, 'K');
            put(113.08406, 'L');
            put(131.04049, 'M');
            put(114.04293,'N');
            put(97.05276, 'P');
            put(128.05858, 'Q');
            put(156.10111, 'R');
            put(87.03203, 'S');
            put(101.04768,'T');
            put(99.06841, 'V');
            put(186.07931,'W');
            put(163.06333, 'Y');
        }};

        final Scanner scanner = new Scanner(System.in);
        List<Double> list = new ArrayList<Double>();
        while (scanner.hasNextDouble())
        {
            list.add(scanner.nextDouble());
        }

        int n = (list.size() - 3) / 2;
        double parent = list.get(0);
        

    }

    /*def inferring_peptide(n, p, l, peptide=[""]):

    if len(peptide[0])==n:
        return peptide

    BYions = [] # store all possible aa between the postion i and j of l.
    for i in range(len(l)-1):
        for j in range(i+1, len(l)):
            aa = mass_aa.get(round(l[j]-l[i], 5), 0)
            if aa:
                BYions.append([i, j, aa])
    
    if BYions[0]:        
        new_l = l[BYions[0][1]:] # update l
        new_p = BYions[0][2] # add new aa need to be added into candidate peptide
        new_peptide = [p+np for np in new_p for p in peptide] # update candidate peptide
        peptide =inferring_peptide(n, p, new_l, new_peptide)*/
    
}
