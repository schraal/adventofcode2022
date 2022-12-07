import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class D7V2 {
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

        //init directory tree
        Directory root = new Directory("/");
        Directory currentDirectory = root;
        //read in the file tree
        for (String line : allLines) {
            if (line.startsWith("$")) {
                // commando's
                String command = line.substring(2); // take off "$ "
                if ("ls".equals(command)) {
                    //read directory
                    //skip here. files are read below
                } else if ("cd ..".equals(command)) {
                    //navigate to parent directory
                    currentDirectory = currentDirectory.getParent();
                } else if (command.startsWith("cd")) {
                    //navigate to child directory
                    String directoryName = command.split(" ")[1];
                    if (!currentDirectory.getDirectoryName().equals(directoryName)) {
                        currentDirectory = currentDirectory.getChildNode(directoryName);
                        if (currentDirectory == null) {
                            System.out.println("Could not navigate into directory: "+ directoryName);
                            System.exit(-1);
                        }
                    }
                } else {
                    System.out.println("unknown command: "+command);
                }
            } else {
                // read the directory contents, create tree.
                if (line.startsWith("dir")) {
                    // create directory
                    String directoryName = line.split(" ")[1];
                    Directory newDirectory = new Directory(directoryName);
                    newDirectory.setParent(currentDirectory);
                    currentDirectory.addDirectory(newDirectory);
                    System.out.println("created dir: "+ newDirectory);
                } else {
                    String[] filespecs = line.split(" ");
                    String fileName = filespecs[1];
                    int fileSize = Integer.parseInt(filespecs[0]);
                    File file = new File(fileName, fileSize);
                    currentDirectory.addFile(file);
                    System.out.println("created file: "+ file);
                }
            }
        }

        //compute directory sizes
        System.out.println(root.getTotalSize());
        System.out.println(prettyPrintTree(root));

        //get the sizes <= 100000
//        List<Directory> smallDirectories = root.getDirectoriesByMaxSize(100000);
//        int result = 0;
//        for (Directory dir : smallDirectories) {
//            result += dir.totalSize;
//        }
//        return result;
        //find the smallest dir that frees up enough space.
        int totalSpace = 70000000;
        int neededSpace = 30000000;
        int currentTakenSpace = root.getTotalSize();
        int unusedSpace = totalSpace - currentTakenSpace;
        int spaceToFreeUp = neededSpace - unusedSpace;
        System.out.println("Space to free up: "+spaceToFreeUp);

        //navigate through the tree to find the smallest dir that frees up enough space when deleted.
        List<Directory> dirs = root.getDirectoriesByMinSize(spaceToFreeUp);
//        System.out.println(dirs);
        int result = Integer.MAX_VALUE;
        for (Directory dir : dirs) {
            System.out.println(dir);
            if (dir.totalSize < result) {
                result = dir.totalSize;
            }
        }

        return result;
    }

    private static String prettyPrintTree(Directory dir) {
        int indent = 0;
        return prettyPrintTree(dir, indent);
    }

    private static String prettyPrintTree(Directory dir, int indent) {
        StringBuilder result = new StringBuilder();
        result.append(getIndent(indent)).append("/").append(dir).append("\n");
        for (Directory d : dir.children) {
            result.append(prettyPrintTree(d, indent + 4));
        }
        for (File f : dir.files) {
            result.append(getIndent(indent + 4)).append("- ").append(f).append("\n");
        }
        return result.toString();
    }

    private static String getIndent(int indent) {
        StringBuilder result = new StringBuilder();
        result.append(" ".repeat(Math.max(0, indent)));
        return result.toString();
    }


    private static class Directory {
        int totalSize;
        String directoryName;
        Directory parent = null;
        List<File> files = new ArrayList<>();
        List<Directory> children = new ArrayList<>();

        Directory(String directoryName) {
            this.directoryName = directoryName;
        }

        String getDirectoryName() {
            return this.directoryName;
        }

        Directory getChildNode(String directoryName) {
            Directory result = null;
            for (Directory c : this.children) {
                if (c.getDirectoryName().equals(directoryName)) {
                    result = c;
                    break;
                }
            }
            return result;
        }

        void setParent(Directory parentDirectory) {
            this.parent = parentDirectory;
        }

        public void addFile(File file) {
            this.files.add(file);
        }

        public Directory getParent() {
            return parent;
        }

        public int getTotalSize() {
            int totalSize = 0;
            if (children.size() > 0) {
                for (Directory child : children) {
                    totalSize += child.getTotalSize();
                }
            }
            for (File file : files) {
                totalSize += file.fileSize;
            }
            this.totalSize = totalSize;
            return totalSize;
        }

        public String toString() {
            String result = directoryName +" (dir, totalSize: "+totalSize+")";
            return result;
        }

        public void addDirectory(Directory newDirectory) {
            this.children.add(newDirectory);
        }

        public List<Directory> getDirectoriesByMaxSize(int i) {
            List<Directory> result = new ArrayList<>();
            for (Directory dir : children) {
                result.addAll(dir.getDirectoriesByMaxSize(i));
                if (dir.totalSize <= i) {
                    result.add(dir);
                }
            }
            return result;
        }

        public List<Directory> getDirectoriesByMinSize(int spaceToFreeUp) {
            List<Directory> result = new ArrayList<>();
            if (totalSize >= spaceToFreeUp) {
                result.add(this);
            }
            for (Directory dir : children) {
                result.addAll(dir.getDirectoriesByMinSize(spaceToFreeUp));
            }
            return result;
        }
    }

    private static class File {
        String fileName;
        int fileSize;

        File(String fileName, int fileSize) {
            this.fileName = fileName;
            this.fileSize = fileSize;
        }

        public String toString() {
            return fileName + " (file, size="+fileSize+")";
        }
    }
}
