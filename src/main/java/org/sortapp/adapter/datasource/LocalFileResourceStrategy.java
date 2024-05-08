package org.sortapp.adapter.datasource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.sortapp.domain.exception.ResourceException;

class LocalFileResourceStrategy implements ResourceStrategy {
  private static final String DEFAULT_PATH = "Z:/dane/lista.json";
  private final String path;
  private final JsonParser jsonParser;

  LocalFileResourceStrategy(JsonParser jsonParser) {
    this(DEFAULT_PATH, jsonParser);
  }

  LocalFileResourceStrategy(String path, JsonParser jsonParser) {
    this.path = path;
    this.jsonParser = jsonParser;
  }

  @Override
  public int[] fetchData() throws ResourceException {
    try {
      String content = Files.readString(Paths.get(path));
      return jsonParser.parse(content);
    } catch (IOException e) {
      throw new ResourceException("Failed to read the file: " + path, e);
    }
  }

  @Override
  public boolean canFetch() {
    return Files.exists(Paths.get(path));
  }
}
