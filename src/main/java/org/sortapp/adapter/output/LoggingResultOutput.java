package org.sortapp.adapter.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sortapp.domain.SortingResult;
import org.sortapp.domain.port.ResultOutput;

public class LoggingResultOutput implements ResultOutput {
  private static final Logger logger = LoggerFactory.getLogger(LoggingResultOutput.class);

  @Override
  public void displayFetchedData(int[] data) {
    logger.info("Fetched data: {}", arrayToString(data));
  }

  @Override
  public void displaySortedResult(String algorithm, SortingResult result) {
    logger.info("Sorted data ({}): {}", algorithm, arrayToString(result.sortedData()));
    logger.info("Sorting time: {} ms", result.sortingTime());
  }

  private String arrayToString(int[] array) {
    StringBuilder builder = new StringBuilder("[");
    for (int i = 0; i < array.length; i++) {
      builder.append(array[i]);
      if (i < array.length - 1) {
        builder.append(", ");
      }
    }
    builder.append("]");
    return builder.toString();
  }
}
