import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Dag1Vraagstuk1 {
    public static void main(String[] args) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));

            int totaal = 0;
            int max = 0;
            for (String line : allLines) {
                System.out.println(line);
                if (line.isEmpty()) {
                    System.out.println("lege regel");
                    totaal = 0;
                    continue;
                }
                totaal += Integer.parseInt(line);
                if (totaal > max) {
                    max = totaal;
                }
            }
            System.out.println(max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}