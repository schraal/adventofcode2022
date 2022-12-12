import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D12V1V2 {
    private static int maxX;
    private static int maxY;

    private static int minA = Integer.MAX_VALUE;


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        if (args.length != 1) {
            System.out.println("Need to specify the input file");
            System.exit(0);
        } else {
            String inputFile = args[0];
            if (!Files.exists(Paths.get(inputFile))) {
                System.out.println("Input file '" + inputFile + "' does not exist");
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

        //read the file
        maxX = allLines.get(0).length();
        maxY = allLines.size();
        System.out.println(maxX + ", " + maxY);

        Point startPoint = null;
        Point endPoint = null;
        char[][] map = new char[maxX][maxY];
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                char c = allLines.get(y).substring(x, x + 1).charAt(0);
                if (c == 'S') {
                    endPoint = new Point(x, y);
                    c = 'a';
                }
                if (c == 'E') {
                    startPoint = new Point(x, y);
                    c = 'z';
                }
                map[x][y] = c;
            }
        }
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                System.out.print(map[x][y]);
            }
            System.out.println();
        }

        if (startPoint == null) {
            System.out.println("startpoint is not present");
            System.exit(0);
        }
        if (endPoint == null) {
            System.out.println("endpoint is not present");
            System.exit(0);
        }
        System.out.println(startPoint);
        System.out.println(endPoint);

        //play rounds
        int[][] distances = new int[maxX][maxY];
        for (int x=0; x< maxX; x++) {
            Arrays.fill(distances[x], Integer.MAX_VALUE);
        }

        Deque<Point> queue = new ArrayDeque<>();
        queue.addLast(new Point(startPoint.x, startPoint.y));
        distances[startPoint.x][startPoint.y] = 0;
        while(!queue.isEmpty()) {
            Point p = queue.pollFirst();
            int distance = distances[p.x][p.y] + 1;
            tryStep(map, queue, distances, distance, p.x, p.y, p.x-1, p.y);
            tryStep(map, queue, distances, distance, p.x, p.y, p.x+1, p.y);
            tryStep(map, queue, distances, distance, p.x, p.y, p.x, p.y-1);
            tryStep(map, queue, distances, distance, p.x, p.y, p.x, p.y+1);
        }

//        for (int y = 0; y < maxY; y++) {
//            for (int x = 0; x < maxX; x++) {
//                System.out.print(distances[x][y]+", ");
//            }
//            System.out.println();
//        }
        System.out.println("solution 1: "+distances[endPoint.x][endPoint.y]);
        System.out.print("solution 2: ");
        return minA;
    }

    private static void tryStep(char[][] map, Deque<Point> queue, int[][] distances, int distance, int x, int y, int newX, int newY) {
        if (isValidStep(map, distances, distance, x, y, newX, newY) ) {
            queue.addLast(new Point(newX, newY));
            distances[newX][newY] = distance;
            if (map[newX][newY] == 'a') {
                if (distance < minA) {
                    minA = distance;
                }
            }
        }
    }

    private static boolean isValidStep(char[][] map, int[][]distances, int distance, int x, int y, int newX, int newY) {
        return newX >= 0 && newX < maxX &&
                newY >= 0 && newY < maxY &&
                map[x][y]-map[newX][newY] <= 1 &&
                distance < distances[newX][newY];
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }

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
            System.out.println("could not match " + pattern);
        }
        return result;
    }
}
