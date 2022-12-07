import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class D5V2 {
    public static void main(String[] args) {


        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));
            doIt(allLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doIt(List<String> allLines) {

        // step 1: find the empty line
        int empty_line = 0;
        for (String line: allLines) {
            if (line.isEmpty()) {
                break;
            }
            empty_line++;
        }

        // step 2: reverse from the empty line to read in the crate piles
        // step 2a: determine number of piles
        String piles = allLines.get(empty_line-1);
        String[] pile_numbers = piles.split(" ");
        int number_of_piles = Integer.parseInt(pile_numbers[pile_numbers.length-1]);

        // step 2b: read in the piles
        List<Stack<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < number_of_piles; i++) {
            stacks.add(new Stack<>());
        }
        for (int i = (empty_line - 2); i >= 0; i--) {
            String line = allLines.get(i);
            for (int j = 0; j < number_of_piles; j++) {
                try {
                    char a = line.charAt(1 + 4 * j);
                    if (' ' != a) {
                        Stack<Character> pile = stacks.get(j);
                        pile.push(a);
                    }
                } catch(Exception e) {
//                    e.printStackTrace();
//                    System.out.println("out of stacks at line " + i);
                }
            }
        }

        // step 3: start at the empty line and execute the steps
        for (int i = empty_line+1; i < allLines.size(); i++) {
            String line = allLines.get(i);
            String[] ops = line.split(" ");
            int number = Integer.parseInt(ops[1]);
            int from = Integer.parseInt(ops[3]);
            int to = Integer.parseInt(ops[5]);

            // use temp stack to preserve the order.
            Stack<Character> temp = new Stack<>();
            for (int j=0; j < number; j++) {
                Character s = stacks.get(from - 1).pop();
                temp.push(s);
            }
            for (int j=0; j < number; j++) {
                Character s = temp.pop();
                stacks.get(to - 1).push(s);
            }

        }
        // step 4: read the top of the crates
        for (int i = 0; i<number_of_piles; i++) {
            System.out.print(stacks.get(i).peek());
        }
        System.out.println();
    }
}
