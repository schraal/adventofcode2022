import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D11V3 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        if (args.length != 1) {
            System.out.println("Need to specify the input file");
            System.exit(0);
        } else {
            String inputFile = args[0];
            if (!Files.exists(Paths.get(inputFile))) {
                System.out.println("Input file '"+inputFile+"' does not exist");
                System.exit(0);
            } else {
                try {
                    List<String> allLines = Files.readAllLines(Paths.get(inputFile));
//                    System.out.println("time: "+(System.currentTimeMillis()-start));
                    long result = doIt(allLines, start);
                    System.out.println(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static long doIt(List<String> allLines, long start) {

        //read the monkeys
        List<Monkey> monkeys = new ArrayList<>();
        int monkeyIndex = 0;
        long sumTestValues = 1;
        Pattern testPattern = Pattern.compile("Test: divisible by ([0-9]+)");
        Pattern truePattern = Pattern.compile("If true: throw to monkey ([0-9]+)");
        Pattern falsePattern = Pattern.compile("If false: throw to monkey ([0-9]+)");
        Pattern opsPattern = Pattern.compile("(.+) ([+|*]) (.+)");


        for (String l : allLines) {
            String line = l.trim();
            if (line.startsWith("Monkey")) {
                monkeys.add(new Monkey());
            } else if (line.startsWith("Starting items: ")) {
                String items = line.split(": ")[1];
                for (String i : items.split(", ")) {
                    monkeys.get(monkeyIndex).items.add(Long.parseLong(i));
                }
            } else if (line.startsWith("Operation:")) {
                String operation = line.split(" = ")[1];
                List<String> ops = extractValues(opsPattern, operation);
                monkeys.get(monkeyIndex).operator = ops.get(1);
                monkeys.get(monkeyIndex).operationY = ops.get(2).equals("old") ? 0 : Long.parseLong(ops.get(2));
            } else if (line.startsWith("Test:")) {
                monkeys.get(monkeyIndex).testValue = Long.parseLong(extractValues(testPattern, line).get(0));
                sumTestValues = sumTestValues *monkeys.get(monkeyIndex).testValue;
            } else if (line.startsWith("If true")) {
                monkeys.get(monkeyIndex).trueMonkey = Integer.parseInt(extractValues(truePattern, line).get(0));
            } else if (line.startsWith("If false")) {
                monkeys.get(monkeyIndex).falseMonkey = Integer.parseInt(extractValues(falsePattern, line).get(0));
            } else if (line.isEmpty()) {
                //next monkey
//                System.out.println(monkeys.get(monkeyIndex));
                monkeyIndex++;
            } else {
                System.out.println("unknown line");
            }
        }
//        System.out.println("time: "+(System.currentTimeMillis()-start));

        //play rounds
        for (int round = 0; round < 10000; round++) {
            for (Monkey monkey : monkeys) {
                for (long thing : monkey.items) {
                    long y = monkey.operationY ==0 ? thing : monkey.operationY;
                    long worryLevel = monkey.operator.equals("+") ? thing + y : thing * y;
                    worryLevel = worryLevel % sumTestValues;
                    if (worryLevel % monkey.testValue == 0) {
                        monkeys.get(monkey.trueMonkey).items.add(worryLevel);
                    } else {
                        monkeys.get(monkey.falseMonkey).items.add(worryLevel);
                    }
                    monkey.numberOfActions++;
                }
                monkey.items.clear();
            }
        }
//        System.out.println("time: "+(System.currentTimeMillis()-start));

//        System.out.println();
        long[] values = new long[monkeys.size()];
        for (int i = 0 ; i < monkeys.size(); i++) {
            values[i] = monkeys.get(i).numberOfActions;
        }
        Arrays.sort(values);
//        System.out.println(Arrays.toString(values));
        return values[values.length-2] * values[values.length-1];
    }

    private static List<String> extractValues(String patternString, String line) {
        Pattern pattern = Pattern.compile(patternString);
        return extractValues(pattern, line);
    }

    private static List<String> extractValues(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        List<String> result = new ArrayList<>();
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++)
                result.add(matcher.group(i));
        } else {
            System.out.println("could not match "+pattern);
        }
        return result;
    }

    private static class Monkey {
        List<Long> items = new ArrayList<>();
        long operationY;
        String operator;
        long testValue;
        int trueMonkey;
        int falseMonkey;
        long numberOfActions = 0;

        @Override
        public String toString() {
            return "Monkey{" +
                    "items=" + items +
                    ", operationY='" + operationY + '\'' +
                    ", operator='" + operator + '\'' +
                    ", testValue=" + testValue +
                    ", trueMonkey=" + trueMonkey +
                    ", falseMonkey=" + falseMonkey +
                    '}';
        }
    }
}
