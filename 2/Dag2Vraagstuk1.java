import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;

class Dag2Vraagstuk1 {
    public static void main(String[] args) {
        Dictionary<String, Integer> scores = new Hashtable<>();
        scores.put("A X", 1+3); //Rock rock
        scores.put("A Y", 2+6); //Rock paper
        scores.put("A Z", 3+0); //Rock scissors

        scores.put("B X", 1+0); //paper rock
        scores.put("B Y", 2+3); //paper paper
        scores.put("B Z", 3+6); //paper scissors

        scores.put("C X", 1+6); //scissors rock
        scores.put("C Y", 2+0); //scissors paper
        scores.put("C Z", 3+3); //scissors scissors

        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));
            int result = 0;
            for (String line : allLines) {
                result += scores.get(line);
            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}