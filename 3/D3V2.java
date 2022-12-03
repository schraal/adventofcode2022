import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class D3V2 {
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
        for (int group = 0; group < (allLines.size() / 3); group++) {
            String e1 = allLines.get(3 * group);
            String e2 = allLines.get(3 * group + 1);
            String e3 = allLines.get(3 * group + 2);
            for (int i = 0; i < e1.length(); i++) {
                String s = e1.substring(i, i + 1);
                if (e2.contains(s) && e3.contains(s)) {
                    char c = s.charAt(0);
                    int priority = c >= 97 ? c - (int) 'a' + 1 : c - (int) 'A' + 27;
                    result += priority;
//                        System.out.println(group +", "+ c +", "+ priority +", "+ result);
                    break;
                }
            }
        }
        return result;
    }
}
