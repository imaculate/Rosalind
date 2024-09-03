import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Splicing {
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
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String line = "";
        List<String> in = new ArrayList<>();
        while (scanner.hasNextLine()) {
            while (scanner.hasNextLine()) 
            {
                line = scanner.nextLine();
                if (line.startsWith(">")) break;
                sb.append(line);
            }

            if (!sb.isEmpty()) { in.add(sb.toString()); }
            sb.setLength(0);
        }

        scanner.close();

        // System.out.println("Collected " + in.size() + " strings");
        String dna = in.get(0);
        for (int i = 1; i < in.size(); i++)
        {
            dna = dna.replaceAll(in.get(i), "");
            // System.out.println("Removed " + s + " to convert to " + dna);
        }

        System.out.println(translate(transcribe(dna)));
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
