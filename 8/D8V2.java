import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class D8V2 {
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

        int highestScenicScore = 0;
        System.out.println(highestScenicScore);
        for (int row = 1; row < trees.length-1; row++) {
            for (int column = 1; column < trees[row].length-1; column++) {
                int left = isVisibleFromLeft(trees, row, column);
                int right = isVisibleFromRight(trees, row, column);
                int top = isVisibleFromTop(trees, row, column);
                int bottom = isVisibleFromBottom(trees, row, column);
                int scenicScore = left * right * top * bottom;
                if (scenicScore > highestScenicScore) {
                    highestScenicScore = scenicScore;
                }
                System.out.print(" (" +top+","+left+","+bottom+","+right+")");
            }
            System.out.println(" "+highestScenicScore);
        }
        return highestScenicScore;
    }

    private static int isVisibleFromLeft(int[][] trees, int row, int column) {
        int visible = 0;
        for (int index = column; index > 0; index--) {
            visible++;
            if (trees[row][index-1] >= trees[row][column]) {
                break;
            }
        }
        return visible;
    }

    private static int isVisibleFromRight(int[][] trees, int row, int column) {
        int visible = 0;
        for (int index = column; index < (trees[row].length-1); index++) {
            visible++;
            if (trees[row][column] <= trees[row][index+1]){
                break;
            }
        }
        return visible;
    }

    private static int isVisibleFromTop(int[][] trees, int row, int column) {
        int visible = 0;
        for (int index = row; index > 0; index--) {
            visible++;
            if (trees[index-1][column] >= trees[row][column]){
                break;
            }
        }
        return visible;
    }

    private static int isVisibleFromBottom(int[][] trees, int row, int column) {
        int visible = 0;
        for (int index = row; index < trees.length-1; index++) {
            visible++;
            if (trees[row][column] <= trees[index+1][column]) {
                break;
            }
        }
        return visible;
    }

}
