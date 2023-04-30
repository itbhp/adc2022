package it.twinsbrain.adc2022.day07;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07Solution {

  public static void main(String[] args) throws URISyntaxException {
    var input = read(resource("/day07/input.txt"));
    System.out.printf("Part 1: %s", part1(input));
    System.out.println();
    System.out.printf("Part 2: %s", part2(input));
  }

  public static int part1(List<String> input) {
    var root = parse(input);
    final AtomicInteger sum = new AtomicInteger(0);
    Consumer<Directory> accumulator =
        dirToVisit -> {
          if (dirToVisit.size() < 100_000) {
            sum.addAndGet(dirToVisit.size());
          }
        };
    visit(root, accumulator);
    return sum.intValue();
  }

  public static int part2(List<String> input) {
    var root = parse(input);
    var currentAvailableSpace = 70_000_000 - root.size();
    var neededSpace = 30_000_000 - currentAvailableSpace;
    var candidateDirToDelete = new ConcurrentLinkedQueue<Directory>();
    Consumer<Directory> accumulator =
        dirToVisit -> {
          if (dirToVisit.size() >= neededSpace) {
            candidateDirToDelete.add(dirToVisit);
          }
        };
    visit(root, accumulator);
    return candidateDirToDelete.stream().mapToInt(Directory::size).min().orElse(0);
  }

  public static void visit(Directory root, Consumer<Directory> accumulator) {
    var unvisitedDir = new LinkedList<Directory>();
    unvisitedDir.push(root);
    while (!unvisitedDir.isEmpty()) {
      var dirToVisit = unvisitedDir.pop();
      accumulator.accept(dirToVisit);
      dirToVisit.allSubDirs().forEach(unvisitedDir::push);
    }
  }

  public static Directory parse(List<String> input) {
    var filePattern = Pattern.compile("(\\d+) (.*)");
    var root = Directory.root();
    var currentDir = root;
    var iterator = input.iterator();
    iterator.next(); // skip root
    while (iterator.hasNext()) {
      var consoleLine = iterator.next();
      if (consoleLine.startsWith("$ cd")) {
        currentDir = execCd(currentDir, consoleLine);
      } else if (consoleLine.startsWith("$ ls")) {
        // DO NOTHING: on next iteration it will parse listed dirs or files
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

  int size();
}

final class Directory implements FileSystem {
  private final String name;
  private final List<FileSystem> children;
  private final Directory parent;

  private int size = -1; // not yet calculated

  public Directory(String name, List<FileSystem> children, Directory parent) {
    this.name = name;
    this.children = children;
    this.parent = parent;
  }

  static Directory root() {
    return new Directory("/", new ArrayList<>(), null);
  }

  public Directory moveTo(String dirName) {
    return (Directory)
        children.stream()
            .filter(FileSystem::isDirectory)
            .filter(d -> d.name().equals(dirName))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("dir not found"));
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public int size() {
    if (size != -1) {
      return size;
    }
    size = children.stream().mapToInt(FileSystem::size).sum();
    return size;
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

  public List<Directory> allSubDirs() {
    return children.stream()
        .filter(FileSystem::isDirectory)
        .map(it -> (Directory) it)
        .collect(toList());
  }

  @Override
  public String toString() {
    var res = new StringBuilder("[" + name + "]");
    if (!children.isEmpty()) {
      var partitionWithDirs =
          children.stream().collect(Collectors.partitioningBy(FileSystem::isDirectory));
      res.append("->");
      res.append("{");
      res.append(
          partitionWithDirs.get(false).stream().map(FileSystem::toString).collect(joining(",")));
      var subDirs = partitionWithDirs.get(true);
      if (subDirs.size() > 0) {
        res.append(",");
        res.append(subDirs.stream().map(FileSystem::toString).collect(joining(",")));
      }
      res.append("}");
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

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    return fileName + "(" + size + ")";
  }
}
