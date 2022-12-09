import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class D9V2 {
    private static final int LENGTH = 10;

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
        Position[] positions = new Position[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            positions[i] = new Position();
        }

        int count = 0;
        Set<String> tPositions= new HashSet<>();
        for (String line : allLines) {
            String[] s = line.split(" ");
            String direction = s[0];
            int steps = Integer.parseInt(s[1]);
            switch(direction) {
                case "U":
                    //up
                    for (int i = 0; i < steps; i++) {
                        Position t = positions[0];
                        t.setY(t.getY()+1);
                        recomputePositions(positions);
                        tPositions.add(positions[LENGTH-1].toString());
                    }
                    break;
                case "D":
                    //down
                    for (int i = 0; i < steps; i++) {
                        Position t = positions[0];
                        t.setY(t.getY()-1);
                        recomputePositions(positions);
                        tPositions.add(positions[LENGTH-1].toString());
                    }
                    break;
                case "L":
                    //
                    for (int i = 0; i < steps; i++) {
                        Position t = positions[0];
                        t.setX(t.getX()-1);
                        recomputePositions(positions);
                        tPositions.add(positions[LENGTH-1].toString());
                    }
                    break;
                case "R":
                    //
                    for (int i = 0; i < steps; i++) {
                        Position t = positions[0];
                        t.setX(t.getX()+1);
                        recomputePositions(positions);
                        tPositions.add(positions[LENGTH-1].toString());
                    }
                    break;
                default:
                    System.out.println("unknown direction: "+direction);
                    break;
            }
//            System.out.println(tPositions);
            count++;
            System.out.print(count+": "+line+": ");
            for (int i = 0; i < LENGTH; i++) {
                System.out.print(i+":"+positions[i]+", ");
            }
            System.out.println();
        }
        System.out.println(tPositions);
        return tPositions.size();
    }

    private static void recomputePositions(Position[] positions) {
        //assumption: tail is at most one steps behind head
        for (int i = 0; i < LENGTH-1; i++) {
            Position h = positions[i];
            Position t = positions[i+1];
            if (t.getX() == h.getX()-2 && t.getY() == h.getY()-2) {
                t.setX(t.getX() + 1);
                t.setY(t.getY() + 1);
            } else if (t.getX() == h.getX()-2 && t.getY() == h.getY()+2) {
                t.setX(t.getX() + 1);
                t.setY(t.getY() - 1);
            } else if (t.getX() == h.getX()+2 && t.getY() == h.getY()-2) {
                t.setX(t.getX() - 1);
                t.setY(t.getY() + 1);
            } else if (t.getX() == h.getX()+2 && t.getY() == h.getY()+2) {
                t.setX(t.getX() - 1);
                t.setY(t.getY() - 1);
            } else if (t.getX() == h.getX()-2) {
                t.setX(t.getX() + 1);
                t.setY(h.getY());
            } else if (t.getX() == h.getX()+2) {
                t.setX(t.getX() - 1);
                t.setY(h.getY());
            } else if (t.getY() == h.getY()-2) {
                t.setY(t.getY() + 1);
                t.setX(h.getX());
            } else if (t.getY() == h.getY()+2) {
                t.setY(t.getY() - 1);
                t.setX(h.getX());
                //} else { //close enough
            }
        }
    }
}

class Position {
    int x=0;
    int y=0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
