package org.sortapp.application;

import org.sortapp.domain.port.SortingAlgorithm;

public interface SortingAlgorithmFactory {
  SortingAlgorithm createAlgorithm(String algorithmType);
}
