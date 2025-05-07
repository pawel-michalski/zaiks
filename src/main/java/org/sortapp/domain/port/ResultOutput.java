package org.sortapp.domain.port;

import org.sortapp.domain.SortingResult;

public interface ResultOutput {
    void displayFetchedData(int[] data);
    void displaySortedResult(String algorithm, SortingResult result);
}
