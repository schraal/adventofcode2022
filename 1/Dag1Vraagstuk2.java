import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("SpellCheckingInspection")
class Dag1Vraagstuk2 {
    public static void main(String[] args) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));

            int totaal = 0;
            Set<Integer> totalen = new TreeSet<>();
            for (String line : allLines) {
                if (line.isEmpty()) {
                    totalen.add(totaal);
                    totaal = 0;
                    continue;
                }
                totaal += Integer.parseInt(line);
            }
            //laatste elf ook meetellen
            if (totaal != 0) {
                totalen.add(totaal);
            }
            List<Integer> l = totalen.stream().toList();
            List<Integer> l2 = l.subList(Math.max(l.size() - 3, 0), l.size());
            Integer sum = l2.stream().mapToInt(Integer::intValue).sum();

            System.out.println(sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}