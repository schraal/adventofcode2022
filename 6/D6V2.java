import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class D6V2 {
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
        Character[] t = new Character[14];
        for (int i = 0; i < 14; i++) {
            t[i]=s.charAt(i);
        }
        System.out.println(Arrays.toString(t));
        while (!areAllCharDifferent(t)) {
            for (int i = 0; i < 13; i++) {
                t[i] = t[i+1];
            }
            t[13] = s.charAt(index);
            System.out.println(Arrays.toString(t));
            index++;
        }
        return index;
    }

    private static boolean areAllCharDifferent(Character[] t) {
        List<Character> l = Arrays.asList(t);
        Set<Character> s = new HashSet<>(l);
        System.out.println(s.size() + ", "+ t.length);
        return s.size() == l.size();
    }
}
