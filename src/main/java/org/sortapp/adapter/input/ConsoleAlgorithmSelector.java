package org.sortapp.adapter.input;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sortapp.domain.port.AlgorithmSelector;

public class ConsoleAlgorithmSelector implements AlgorithmSelector {
  private static final Logger logger = LoggerFactory.getLogger(ConsoleAlgorithmSelector.class);

  @Override
  public String selectAlgorithm() {
    logger.info("Choose sorting algorithm:");
    logger.info("1 - BubbleSort");
    logger.info("2 - MergeSort");
    logger.info("Your choice (default is 1): ");

    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine().trim();

    return switch (input) {
      case "2" -> "merge";
      case "1", "" -> "bubble";
      default -> {
        logger.info("Unknown input. Defaulting to BubbleSort.");
        yield "bubble";
      }
    };
  }
}
