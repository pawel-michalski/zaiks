package org.sortapp;

import org.sortapp.adapter.datasource.ResourceDataSource;
import org.sortapp.adapter.input.ConsoleAlgorithmSelector;
import org.sortapp.adapter.output.LoggingResultOutput;
import org.sortapp.algorithm.DefaultSortingAlgorithmFactory;
import org.sortapp.application.SortingUseCase;

public class Main {
  public static void main(String[] args) {
    SortingUseCase sortingUseCase =
        new SortingUseCase(
            new ResourceDataSource(),
            new ConsoleAlgorithmSelector(),
            new LoggingResultOutput(),
            new DefaultSortingAlgorithmFactory());

    sortingUseCase.execute();
  }
}
