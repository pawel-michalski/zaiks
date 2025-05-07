package org.sortapp.algorithm;

import org.sortapp.application.SortingAlgorithmFactory;
import org.sortapp.domain.port.SortingAlgorithm;

public class DefaultSortingAlgorithmFactory implements SortingAlgorithmFactory {
    public static final String BUBBLE = "bubble";
    public static final String MERGE = "merge";

    @Override
    public SortingAlgorithm createAlgorithm(String algorithmType) {
        return switch (algorithmType.toLowerCase()) {
            case MERGE -> new MergeSortAlgorithm();
            default -> new BubbleSortAlgorithm();
        };
    }
}
