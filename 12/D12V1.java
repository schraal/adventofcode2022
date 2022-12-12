import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D12V1 {
    private static int maxX;
    private static int maxY;

    private static int minSteps;

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
        minSteps = maxX * maxY;
        System.out.println(maxX + ", " + maxY);
        Point startPoint = null;
        Point endPoint = null;
        char[][] map = new char[maxX][maxY];
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                String s = allLines.get(y).substring(x, x + 1);
                if (s.equals("S")) {
                    startPoint = new Point(x, y);
                    s = "a";
                }
                if (s.equals("E")) {
                    endPoint = new Point(x, y);
                    s = "z";
                }
                map[x][y] = s.charAt(0);
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
        List<Point> path = new ArrayList<>();
        int x = startPoint.x;
        int y = startPoint.y;
        path.add(new Point(x, y));
        int minU = computeMin(map, path, endPoint, x, y, x, y + 1);
        int minR = computeMin(map, path, endPoint, x, y, x + 1, y);
        int minD = computeMin(map, path, endPoint, x, y, x, y - 1);
        int minL = computeMin(map, path, endPoint, x, y, x - 1, y);
        return Math.min(minU, Math.min(minD, Math.min(minL, minR)));
    }

    private static int computeMin(char[][] map, List<Point> path, Point endPoint, int oldX, int oldY, int x, int y) {
        if (x >= 0 && x < maxX && y >= 0 && y < maxY) { //within the playing field
            if (map[x][y] - map[oldX][oldY] <= 1) { //can only do 1 step up/down
                if (path.size() - 1 < minSteps) { //not worth looking at longer paths than the min already found
                    Point currentPoint = new Point(x, y);
                    path.add(currentPoint);
                    if (currentPoint.equals(endPoint)) {
                        //found the end
//                        System.out.println(path);
                        System.out.println(path.size());
                        if (path.size()-1 < minSteps) {
                            minSteps = path.size()-1;
                        }
                        return path.size() - 1;
                    } else {
                        int minU = !path.contains(new Point(x, y + 1)) ? computeMin(map, new ArrayList<>(path), endPoint, x, y, x, y + 1) : Integer.MAX_VALUE;
                        int minR = !path.contains(new Point(x + 1, y)) ? computeMin(map, new ArrayList<>(path), endPoint, x, y, x + 1, y) : Integer.MAX_VALUE;
                        int minD = !path.contains(new Point(x, y - 1)) ? computeMin(map, new ArrayList<>(path), endPoint, x, y, x, y - 1) : Integer.MAX_VALUE;
                        int minL = !path.contains(new Point(x - 1, y)) ? computeMin(map, new ArrayList<>(path), endPoint, x, y, x - 1, y) : Integer.MAX_VALUE;
                        return Math.min(minU, Math.min(minD, Math.min(minL, minR)));
                    }
                }
            }
        }
        //not found a new minimum
        return Integer.MAX_VALUE;
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
