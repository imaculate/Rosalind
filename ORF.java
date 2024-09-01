import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class ORF {
    static Map<Character, Character> revcMap = Map.of('A', 'T', 'T', 'A', 'C', 'G', 'G','C');
    static Map<String, String> rnaToProtein  = new HashMap<String, String>() {{
        put("UUU", "F");
        put("UUC", "F");
        put("UUA", "L");
        put("UUG", "L");
        put("UCU", "S");
        put("UCC", "S");
        put("UCA", "S");
        put("UCG", "S");
        put("UAU", "Y");
        put("UAC", "Y");
        //put("UAA", "Stop");
        //put("UAG", "Stop");
        put("UGU", "C");
        put("UGC", "C");
        //put("UGA", "Stop");
        put("UGG", "W");
        put("UUU", "F");
        put("CUU", "L");
        put("AUU", "I");
        put("GUU", "V");
        put("CUC", "L");
        put("AUC", "I");  
        put("GUC", "V");
        put("CUA", "L");
        put("AUA", "I");
        put("GUA", "V");
        put("CUG", "L");
        put("AUG", "M");
        put("GUG", "V");
        put("CCU", "P");
        put("ACU", "T");
        put("GCU", "A");
        put("CCC", "P");
        put("ACC", "T");
        put("GCC", "A");
        put("CCA", "P");
        put("ACA", "T");
        put("GCA", "A");
        put("CCG", "P");
        put("ACG", "T");
        put("GCG", "A");
        put("CAU", "H");
        put("AAU", "N");
        put("GAU", "D");
        put("CAC", "H");
        put("AAC", "N");
        put("GAC", "D");
        put("CAA", "Q");
        put("AAA", "K");
        put("GAA", "E");
        put("CAG", "Q");
        put("AAG", "K");
        put("GAG", "E");
        put("CGU", "R");
        put("AGU", "S");
        put("GGU", "G");
        put("CGC", "R");
        put("AGC", "S");
        put("GGC", "G");
        put("CGA", "R");
        put("AGA", "R");
        put("GGA", "G");
        put("CGG", "R");
        put("AGG", "R");
        put("GGG", "G");
        put("CAA", "Q");
        put("ACU", "T");
        put("ACG", "T");
        put("ACA", "T");
        put("ACC", "T");
    }};

    static Set<String> specialCodons = new HashSet<>(Arrays.asList("ATG", "TAA", "TAG", "TGA"));
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // discard title
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        scanner.close();

        String dna = sb.toString();
        System.out.println("Processing: " + dna);
        Set<String> results = orf(dna);
        results.addAll(orf(reverseComplement(dna)));

        for (String prot : results)
        {
            System.out.println(prot);
        }
    }


    public static Set<String> orf(String dna)
    {
        Map<Integer, String> matchesMap = findCodons(dna);
        Map<Integer, List<Integer>> positionsMap = new HashMap<>();
        for (Integer p: matchesMap.keySet())
        {
            if (matchesMap.get(p).equals("ATG"))
            {
                positionsMap.computeIfAbsent(p%3, t -> new ArrayList<>()).add(p+1);
            }
            else
            {
                positionsMap.computeIfAbsent(p%3, t -> new ArrayList<>()).add(-p);
            }
        }
        positionsMap.forEach((key, value) -> System.out.println(key + " " + value.toString()));
        Set<String> result = new HashSet<>();
        for (int key = 0; key < 3; key++)
        {
            List<Integer> starts = new ArrayList<>();
            for (Integer p: positionsMap.get(key))
            {
                if (p > 0)
                {
                    starts.add(p-1);
                }
                else
                {
                    for (Integer start: starts)
                    {
                        String prot = translate(transcribe(dna.substring(start, -p)));
                        result.add(prot);
                    }
                    starts.clear();
                }
            }
        }
        return result;
    }

    public static String reverseComplement(String s)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
        {
            sb.append(revcMap.get(s.charAt(i)));   
        }
        return sb.reverse().toString();
    }

    public static Map<Integer, String> findCodons(String dna)
    {
        Map<Integer, String> map = new TreeMap<>();
        for (int i = 0; i <= dna.length() - 3; i++)
        {
            String codon = dna.substring(i, i+3);
            if (specialCodons.contains(codon))
            {
                map.put(i, codon);
            }
        }
        return map;
    }

    public static String transcribe(String dna)
    {
        return dna.replace('T', 'U');
    }

    public static String translate(String rna)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rna.length() - 1; i+=3)
        {
            String codon = rna.substring(i, i+3);
            sb.append(rnaToProtein.getOrDefault(codon, ""));
        }
        return sb.toString();
    }
}
