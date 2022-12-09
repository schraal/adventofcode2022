import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class D9V1 {
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
        int[] positions = new int[]{0, 0, 0, 0}; //hx, hy, tx, ty
        Set<String> tPositions= new HashSet<>();
        for (String line : allLines) {
            String[] s = line.split(" ");
            String direction = s[0];
            int steps = Integer.parseInt(s[1]);
            switch(direction) {
                case "U":
                    //up
                    for (int i = 0; i < steps; i++) {
                        positions[1] = positions[1]+1;
                        positions = recomputeTPosition(positions[0], positions[1], positions[2], positions[3]);
                        tPositions.add(positions[2]+","+positions[3]);
                    }
                    break;
                case "D":
                    //down
                    for (int i = 0; i < steps; i++) {
                        positions[1] = positions[1]-1;
                        positions = recomputeTPosition(positions[0], positions[1], positions[2], positions[3]);
                        tPositions.add(positions[2]+","+positions[3]);
                    }
                    break;
                case "L":
                    //
                    for (int i = 0; i < steps; i++) {
                        positions[0] = positions[0]-1;
                        positions = recomputeTPosition(positions[0], positions[1], positions[2], positions[3]);
                        tPositions.add(positions[2]+","+positions[3]);
                    }
                    break;
                case "R":
                    //
                    for (int i = 0; i < steps; i++) {
                        positions[0] = positions[0]+1;
                        positions = recomputeTPosition(positions[0], positions[1], positions[2], positions[3]);
                        tPositions.add(positions[2]+","+positions[3]);
                    }
                    break;
                default:
                    System.out.println("unknown direction: "+direction);
                    break;
            }
//            System.out.println(tPositions);
        }
        return tPositions.size();
    }

    private static int[] recomputeTPosition(int hx, int hy, int tx, int ty) {
        //assumption: tail is at most one steps behind head
        if (tx == hx-2) {
            tx = tx + 1;
            ty = hy;
        } else if (tx == hx+2) {
            tx = tx - 1;
            ty = hy;
        } else if (ty == hy-2) {
            ty = ty + 1;
            tx = hx;
        } else if (ty == hy+2) {
            ty = ty - 1;
            tx = hx;
        //} else { //close enough
        }
        return new int[]{hx, hy, tx, ty};
    }

}
