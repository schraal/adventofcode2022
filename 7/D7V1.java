import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class D7V1 {
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
        System.out.println(root);
        System.out.println(root.getTotalSize());

        //get the sizes <= 100000
        List<Directory> smallDirectories = root.getDirectoriesByMaxSize(100000);
        int result = 0;
        for (Directory dir : smallDirectories) {
            result += dir.totalSize;
        }
        return result;
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
            String result = directoryName +" (dir)";
            for (File file : files) {
                result += file;
            }
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
