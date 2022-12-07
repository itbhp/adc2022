package it.twinsbrain.adc2022.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07Solution {

    public static FileSystem parse(List<String> input) {
        var filePattern = Pattern.compile("(\\d+) (.*)");
        Directory root = Directory.root();
        Directory currentDir = root;
        var iterator = input.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            var consoleLine = iterator.next();
            if (consoleLine.startsWith("$ cd")) {
                currentDir = execCd(currentDir, consoleLine);
            } else if (consoleLine.startsWith("$ ls")) {
                // DO NOTHING
            } else if (consoleLine.startsWith("dir")) {
                addDir(currentDir, consoleLine);
            } else if (consoleLine.matches("\\d+\\s.*")) {
                addFile(currentDir, consoleLine, filePattern);
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

    private static void addFile(Directory currentDir, String line, Pattern filePattern) {
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

    public void addFile(String fileName, int size) {
        children.add(new File(fileName, size));
    }

    public Directory moveBack() {
        return parent;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[" + name + "]");
        if (!children.isEmpty()) {
            Map<Boolean, List<FileSystem>> areDirectory = children.stream()
                    .collect(Collectors.partitioningBy(FileSystem::isDirectory));
            for (FileSystem file : areDirectory.get(false)) {
                res.append("-> ").append(file);
            }
            for (FileSystem directory : areDirectory.get(true)) {
                res.append("-> ").append(directory);
            }
        }
        return res.toString();
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

    @Override
    public String toString() {
        return fileName + "(" + size + ")";
    }
}
