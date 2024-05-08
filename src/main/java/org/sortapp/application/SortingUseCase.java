package org.sortapp.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sortapp.domain.SortingResult;
import org.sortapp.domain.port.AlgorithmSelector;
import org.sortapp.domain.port.DataSource;
import org.sortapp.domain.port.ResultOutput;
import org.sortapp.domain.port.SortingAlgorithm;
import org.sortapp.domain.service.SortingService;

public class SortingUseCase {
  private static final Logger logger = LoggerFactory.getLogger(SortingUseCase.class);

  private final DataSource dataSource;
  private final AlgorithmSelector algorithmSelector;
  private final ResultOutput resultOutput;
  private final SortingAlgorithmFactory algorithmFactory;
  private final SortingService sortingService;

  public SortingUseCase(
      DataSource dataSource,
      AlgorithmSelector algorithmSelector,
      ResultOutput resultOutput,
      SortingAlgorithmFactory algorithmFactory) {
    this.dataSource = dataSource;
    this.algorithmSelector = algorithmSelector;
    this.resultOutput = resultOutput;
    this.algorithmFactory = algorithmFactory;
    this.sortingService = new SortingService();
  }

  public void execute() {
    try {
      String algorithmType = algorithmSelector.selectAlgorithm();
      SortingAlgorithm algorithm = algorithmFactory.createAlgorithm(algorithmType);

      int[] data = dataSource.fetchData();
      resultOutput.displayFetchedData(data);

      SortingResult result = sortingService.sort(data, algorithm);
      resultOutput.displaySortedResult(algorithmType, result);

    } catch (Exception e) {
      logger.error("An error occurred: {}", e.getMessage(), e);
    }
  }
}
