import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D8V1 {
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
        int[][] trees = new int[allLines.size()][allLines.get(0).length()];
        int lineCount = 0;
        for (String line : allLines) {
            if (!line.isEmpty()) {
                for (int i = 0; i < line.length(); i++) {
                    trees[lineCount][i] = Integer.parseInt(line.substring(i, i + 1));
                }
                lineCount++;
            }
        }
        for (int i = 0; i < trees.length; i++) {
            System.out.println(i + ": " +Arrays.toString(trees[i]));
        }

        int totalVisible = 2*allLines.size()+ 2*(allLines.get(0).length()-2);
        System.out.println(totalVisible);
        for (int row = 1; row < trees.length-1; row++) {
            for (int column = 1; column < trees[row].length-1; column++) {
                boolean left = isVisibleFromLeft(trees, row, column);
                boolean right = isVisibleFromRight(trees, row, column);
                boolean top = isVisibleFromTop(trees, row, column);
                boolean bottom = isVisibleFromBottom(trees, row, column);
                boolean isVisible = left || right || top || bottom;
                if (isVisible) {
                    totalVisible += 1;
                }
                System.out.print(" " +isVisible);
            }
            System.out.println(" "+totalVisible);
        }
        return totalVisible;
    }

    private static boolean isVisibleFromLeft(int[][] trees, int row, int column) {
        boolean visible = true;
        for (int index = column; index >0; index--) {
            visible = visible && (trees[row][index-1] < trees[row][column]);
        }
        return visible;
    }

    private static boolean isVisibleFromRight(int[][] trees, int row, int column) {
        boolean visible = true;
        for (int index = column; index < (trees[row].length-1); index++) {
            visible = visible && (trees[row][column] > trees[row][index+1]);
        }
        return visible;
    }

    private static boolean isVisibleFromTop(int[][] trees, int row, int column) {
        boolean visible = true;
        for (int index = row; index > 0; index--) {
            visible = visible && (trees[index-1][column] < trees[row][column]);
        }
        return visible;
    }

    private static boolean isVisibleFromBottom(int[][] trees, int row, int column) {
        boolean visible = true;
        for (int index = row; index < trees.length-1; index++) {
            visible = visible && (trees[row][column] > trees[index+1][column]);
        }
        return visible;
    }

}
