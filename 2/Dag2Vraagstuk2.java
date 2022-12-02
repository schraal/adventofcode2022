import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;

class Dag2Vraagstuk2 {
    public static void main(String[] args) {
        Dictionary<String, Integer> scores = new Hashtable<>();
        scores.put("A X", 0+3); //Rock lose -> scissors
        scores.put("A Y", 3+1); //Rock draw -> rock
        scores.put("A Z", 6+2); //Rock win -> paper

        scores.put("B X", 0+1); //paper lose -> rock
        scores.put("B Y", 3+2); //paper draw -> paper
        scores.put("B Z", 6+3); //paper win -> scissors

        scores.put("C X", 0+2); //scissors lose -> paper
        scores.put("C Y", 3+3); //scissors draw -> scissors
        scores.put("C Z", 6+1); //scissors win -> rock

        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt")); int result = 0; for (String line : allLines) {result += scores.get(line);}; System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}