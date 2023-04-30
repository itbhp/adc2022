package it.twinsbrain.adc2022;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilesModule {

  public static URI resource(String fileName) throws URISyntaxException {
    return Objects.requireNonNull(FilesModule.class.getResource(fileName)).toURI();
  }

  public static List<String> read(URI fileName) {
    try (var lines = Files.lines(Paths.get(fileName))) {
      return lines.collect(Collectors.toList());
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }
}
