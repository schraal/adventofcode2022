import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D10V2 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Need to specify the input file");
            System.exit(0);
        } else {
            String inputFile = args[0];
            if (!Files.exists(Path.of(inputFile))) {
                System.out.println("Input file '"+inputFile+"' does not exist");
                System.exit(0);
            } else {
                try {
                    List<String> allLines = Files.readAllLines(Paths.get(inputFile));
                    int result = doIt(allLines);
                    System.out.println(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        int sum = 0;
        for (int value : program) {
            int pixel = cycle % 40;
            cycle++;
//            System.out.println("before cycle "+cycle+" X="+registerX);
            char c = registerX - 1 <= pixel && pixel <= registerX + 1 ? '#' : '.';
//            System.out.println("at cycle "+cycle+" and pixel "+pixel+" and value "+registerX+" the char is "+c);
            System.out.print(c);
            registerX += value;
//            System.out.println("cycle+" X="+registerX);
            if (pixel == 39) {
                System.out.println();
            }
        }

        return sum;
    }
}
