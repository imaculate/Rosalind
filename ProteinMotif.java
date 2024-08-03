import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.URL;
import java.util.*;

public class ProteinMotif {
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        String line = "";
        while (scanner.hasNext()) {
            line = scanner.nextLine();

            try
            {
                String protId = line.split("_")[0];
                URL protUrl = new URL("https://rest.uniprot.org/uniprotkb/" + protId + ".fasta");
                BufferedReader in = new BufferedReader(new InputStreamReader(protUrl.openStream()));
                String inputLine;
                StringBuilder sb = new StringBuilder();
                inputLine = in.readLine();
                while ((inputLine = in.readLine()) != null)
                {
                    sb.append(inputLine);
                }
                in.close();
                findPositions(sb.toString(), line);
            } catch (Exception e) {
                //System.out.println("Something went wrong.");
            } finally {
                //System.out.println("The 'try catch' is finished.");
            }
            
        }
    }

    public static void findPositions(String s, String label)
    {
        // pattern = N{P}[ST]{P}.
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= s.length() - 4; i++)
        {
            if (s.charAt(i) != 'N') continue;

            if (s.charAt(i+1) == 'P') continue;

            if (s.charAt(i+2) != 'S' && s.charAt(i+2) != 'T') continue;

            if (s.charAt(i+3) == 'P') continue;

            list.add(i+1);
        }

        if (list.size() > 0)
        {
            System.out.println(label);
            for (Integer j: list)
            {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

        
}
