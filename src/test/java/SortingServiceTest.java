import org.junit.jupiter.api.Test;
import org.sortapp.domain.SortingResult;
import org.sortapp.domain.port.SortingAlgorithm;
import org.sortapp.domain.service.SortingService;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SortingServiceTest {

    @Test
    void sort_shouldReturnCorrectResult() {
        // Setup
        int[] input = {5, 3, 1, 4, 2};
        int[] sorted = {1, 2, 3, 4, 5};

        SortingAlgorithm mockAlgorithm = mock(SortingAlgorithm.class);
        when(mockAlgorithm.sort(input)).thenReturn(sorted);

        SortingService service = new SortingService();

        // Execute
        SortingResult result = service.sort(input, mockAlgorithm);

        // Verify
        verify(mockAlgorithm).sort(input);
        assertArrayEquals(sorted, result.sortedData());
        assertTrue(result.sortingTime() >= 0);
    }

    @Test
    void sort_shouldMeasureSortingTime() {
        // Setup
        int[] input = {5, 3, 1, 4, 2};
        int[] sorted = {1, 2, 3, 4, 5};

        SortingAlgorithm algorithm = data -> {
            try {
                // Simulate some processing time
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return sorted;
        };

        SortingService service = new SortingService();

        // Execute
        SortingResult result = service.sort(input, algorithm);

        // Verify
        assertArrayEquals(sorted, result.sortedData());
        assertTrue(result.sortingTime() >= 10, "Sorting time should reflect the processing delay");
    }
}
