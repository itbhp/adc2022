package it.twinsbrain.adc2022.day07;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day07Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day07/input.txt"));
        FileSystem parsed = parse(input);
    }

    static Pattern filePattern = Pattern.compile("(\\d+) (\\w+)");

    public static FileSystem parse(List<String> input) {
        Directory root = Directory.root();
        Directory currentDir = root;
        var iterator = input.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            var consoleLine = iterator.next();
            if (consoleLine.startsWith("$ cd")) {
                currentDir = execCd(currentDir, consoleLine);
            }

            if (consoleLine.startsWith("$ ls")) {
                String nextLine = iterator.next();
                while (nextLine.matches("dir\\s.*") || nextLine.matches("\\d+\\s.*")) {
                    if (nextLine.startsWith("dir")) {
                        addDir(currentDir, nextLine);
                    } else {
                        addFile(currentDir, nextLine);
                    }
                    if (iterator.hasNext()) {
                        nextLine = iterator.next();
                    } else {
                        nextLine = "";
                    }
                }
            }
        }
        return root;
    }

    private static Directory execCd(Directory currentDir, String consoleLine) {
        var dirName = consoleLine.substring(4).trim();
        if ("..".equals(dirName)) {
            return currentDir.moveBack();
        } else {
            return currentDir.moveTo(dirName);
        }
    }

    private static void addDir(Directory currentDir, String line) {
        var dirName = line.substring(4).trim();
        currentDir.addSubDir(dirName);
    }

    private static void addFile(Directory currentDir, String line) {
        var matcher = filePattern.matcher(line);
        if (matcher.matches()) {
            var size = Integer.parseInt(matcher.group(1));
            var fileName = matcher.group(2);
            currentDir.addFile(fileName, size);
        }
    }

}

sealed interface FileSystem permits Directory, File {
    boolean isDirectory();

    String name();
}

final class Directory implements FileSystem {
    private final String name;
    private final List<FileSystem> children;
    private final Directory parent;

    public Directory(String name, List<FileSystem> children, Directory parent) {
        this.name = name;
        this.children = children;
        this.parent = parent;
    }

    static Directory root() {
        return new Directory("/", new ArrayList<>(), null);
    }

    public Directory moveTo(String dirName) {
        return (Directory) children.stream()
                .filter(FileSystem::isDirectory)
                .filter(d -> d.name().equals(dirName))
                .findFirst().orElseThrow(() -> new RuntimeException("dir not found"));
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public String name() {
        return name;
    }

    public void addSubDir(String dirName) {
        children.add(new Directory(dirName, new ArrayList<>(), this));
    }

    public void addSubDir(Directory d) {
        children.add(d);
    }

    public void addFile(String fileName, int size) {
        children.add(new File(fileName, size));
    }

    public Directory moveBack() {
        return parent;
    }
}

final class File implements FileSystem {

    private final String fileName;
    private final int size;

    public File(String fileName, int size) {
        this.fileName = fileName;
        this.size = size;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String name() {
        return fileName;
    }

    public int size() {
        return size;
    }
}