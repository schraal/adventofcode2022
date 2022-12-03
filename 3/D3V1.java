import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class D3V1 {
    public static void main(String[] args) {


        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));
            int result = 0;
            for (String line : allLines) {
                boolean found = false;
                for (int i = 0; i < (line.length()/2); i++) {
                    for (int j = line.length()/2; j < line.length(); j++) {
                        if (line.charAt(i) == line.charAt(j)) {
                            int c = line.charAt(i);
                            int priority = c >= 97 ? c-(int)'a'+1 : c-(int)'A'+27;
                            result += priority;
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }

            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
