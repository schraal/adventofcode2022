import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class D4V2 {
    public static void main(String[] args) {


        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));
            int result = doIt(allLines);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int doIt(List<String> allLines) {
        int result = 0;
        for (String line: allLines) {
            String[] sets = line.split(",");
            String[] s1 = sets[0].split("-");
            String[] s2 = sets[1].split("-");
            int s1x = Integer.parseInt(s1[0]);
            int s1y = Integer.parseInt(s1[1]);
            int s2x = Integer.parseInt(s2[0]);
            int s2y = Integer.parseInt(s2[1]);
            if ( (s1x <= s2x && s1y >= s2y) || (s2x <= s1x && s2y >= s1y) || (s1x <= s2x && s1y >= s2x) || (s1x <= s2y && s1y >= s2y)) {
                result ++;
            }
        }
        return result;
    }
}
