package org.sortapp.domain.service;

import java.util.Arrays;
import org.sortapp.domain.SortingResult;
import org.sortapp.domain.port.SortingAlgorithm;

public class SortingService {

  public SortingResult sort(int[] data, SortingAlgorithm algorithm) {

    int[] dataCopy = Arrays.copyOf(data, data.length);

    long startTime = System.currentTimeMillis();
    int[] sortedData = algorithm.sort(dataCopy);
    long sortingTime = System.currentTimeMillis() - startTime;

    return new SortingResult(sortedData, sortingTime);
  }
}
