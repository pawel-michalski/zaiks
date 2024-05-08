package org.sortapp.adapter.datasource;

class JsonMockProvider {
  static String getJsonElements() {
    return "{\"elements\":[10,13,-5,50,0]}";
  }

  static boolean containsElementsArray(String input) {
    if (input == null) return false;

    String regex = "\\{\"elements\":\\[\\s*-?\\d+(\\s*,\\s*-?\\d+)*\\s*]}";
    return input.matches(".*" + regex + ".*");
  }
}
