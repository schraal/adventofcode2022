import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D11V2 {

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
                    long result = doIt(allLines);
                    System.out.println(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static long doIt(List<String> allLines) {

        //read the monkeys
        List<Monkey> monkeys = new ArrayList<>();
        int monkeyIndex = 0;
        BigInteger sumTestValues = BigInteger.ONE;
        for (String l : allLines) {
            String line = l.stripLeading();
            if (line.startsWith("Monkey")) {
                monkeys.add(new Monkey());
            } else if (line.startsWith("Starting items: ")) {
                String items = line.split(": ")[1];
                for (String i : items.split(", ")) {
                    long j = Long.parseLong(i);
                    monkeys.get(monkeyIndex).things.add(BigInteger.valueOf(j));
                }
            } else if (line.startsWith("Operation:")) {
                String operation = line.split(" = ")[1];
                String patternString = "(.+) ([+|*]) (.+)";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(operation);
                if (matcher.find()) {
                    monkeys.get(monkeyIndex).operationX = matcher.group(1);
                    monkeys.get(monkeyIndex).operator = matcher.group(2);

                    monkeys.get(monkeyIndex).operationY = matcher.group(3).equals("old") ? BigInteger.ZERO : new BigInteger(matcher.group(3));
                } else {
                    System.out.println("could not match "+patternString);
                }
            } else if (line.startsWith("Test:")) {
                String patternString = "Test: divisible by ([0-9]+)";
                monkeys.get(monkeyIndex).testValue = new BigInteger(extractValue(patternString, line));
                sumTestValues = sumTestValues.multiply(monkeys.get(monkeyIndex).testValue);
            } else if (line.startsWith("If true")) {
                String patternString = "If true: throw to monkey ([0-9]+)";
                monkeys.get(monkeyIndex).trueMonkey = Integer.parseInt(extractValue(patternString, line));
            } else if (line.startsWith("If false")) {
                String patternString = "If false: throw to monkey ([0-9]+)";
                monkeys.get(monkeyIndex).falseMonkey = Integer.parseInt(extractValue(patternString, line));
            } else if (line.isEmpty()) {
                //next monkey
                System.out.println(monkeys.get(monkeyIndex));
                monkeyIndex++;
            } else {
                System.out.println("unknown line");
            }
        }

        //play rounds
        long start = System.currentTimeMillis();
        for (int round = 0; round < 10000; round++) {
            for (Monkey monkey : monkeys) {
                for (BigInteger thing : monkey.things) {
                    BigInteger worryLevel = computeNew(thing, monkey.operator, monkey.operationY);
                    worryLevel = worryLevel.mod(sumTestValues);
                    if (worryLevel.mod(monkey.testValue).equals(BigInteger.ZERO)) {
                        monkeys.get(monkey.trueMonkey).things.add(worryLevel);
                    } else {
                        monkeys.get(monkey.falseMonkey).things.add(worryLevel);
                    }
                    monkey.numberOfActions++;
                    monkey.things.removeFirst();
                }
            }
//            if (round%100 == 0) {
//                System.out.println("round: "+round);
//                System.out.println("time: "+(System.currentTimeMillis()-start));
//                long[] values = new long[monkeys.size()];
//                for (int i = 0 ; i < monkeys.size(); i++) {
//                    values[i] = monkeys.get(i).numberOfActions;
//                }
//                Arrays.sort(values);
//                System.out.println(Arrays.toString(values));
//            }
        }

        System.out.println();
        long[] values = new long[monkeys.size()];
        for (int i = 0 ; i < monkeys.size(); i++) {
            values[i] = monkeys.get(i).numberOfActions;
        }
        Arrays.sort(values);
        System.out.println(Arrays.toString(values));
        return values[values.length-2] * values[values.length-1];
    }

    private static BigInteger computeNew(BigInteger i, String operator, BigInteger operationY) {
        BigInteger y = operationY.equals(BigInteger.ZERO) ? i : operationY;

        return operator.equals("+")? i.add(y) : i.multiply(y);
    }

    private static String extractValue(String patternString, String line) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);
        String result = null;
        if (matcher.find()) {
            result = matcher.group(1);
        } else {
            System.out.println("could not match "+patternString);
        }
        return result;
    }

    private static class Monkey {
        Deque<BigInteger> things = new ArrayDeque<>();
        String operationX;
        BigInteger operationY;
        String operator;
        BigInteger testValue;
        int trueMonkey;
        int falseMonkey;
        long numberOfActions = 0;

        @Override
        public String toString() {
            return "Monkey{" +
                    "things=" + things +
                    ", operationX='" + operationX + '\'' +
                    ", operationY='" + operationY + '\'' +
                    ", operator='" + operator + '\'' +
                    ", testValue=" + testValue +
                    ", trueMonkey=" + trueMonkey +
                    ", falseMonkey=" + falseMonkey +
                    '}';
        }
    }
}
