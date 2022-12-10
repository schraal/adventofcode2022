import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.util.*;

public class D10V1 {

    public static void main(String[] args) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get("input.txt"));
//            List<String> allLines = Files.readAllLines(Paths.get("example.txt"));
            int result = doIt(allLines);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int doIt(List<String> allLines) {
        List<Integer> program = new ArrayList<>();
        for (String line : allLines) {
            String[] cmd = line.split(" ");
            switch (cmd[0]) {
                case "noop" -> program.add(0);
                case "addx" -> {
                    int value = Integer.parseInt(cmd[1]);
                    program.add(0);
                    program.add(value);
                }
                default -> System.out.println("unknown cmd");
            }
        }

        //run program
        int registerX = 1;
        int cycle = 0;
        Set<Integer> checkCycles = new HashSet<>(Arrays.asList(20,60,100,140,180,220));
        int sum = 0;
        for (int value : program) {
            cycle++;
            System.out.println("before cycle "+cycle+" X="+registerX);
            if (checkCycles.contains(cycle)) {
                System.out.println();
                sum += cycle * registerX;
            }
            registerX += value;
            System.out.println("after cycle "+cycle+" X="+registerX);
        }

        return sum;
    }
}
