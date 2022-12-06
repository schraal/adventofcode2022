import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class D6V1 {
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
        int index = 14;
        String s = allLines.get(0);
        List<Character> t = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            t.add(s.charAt(i));
        }
        while (!areAllCharDifferent()) {
            for (int i = 0; i < 13; i++) {
                t.add(i, t.get(i+1));
            }
            t.add(13, s.charAt(index));
            System.out.println(t);
            index++;
        }
        return index;
    }

    private static boolean areAllCharDifferent(List<Character> l) {
        Set<Character> s = new HashSet<>(l);
        return s.size() == l.size();
    }
}
