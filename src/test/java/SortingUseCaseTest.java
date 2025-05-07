import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.sortapp.algorithm.BubbleSortAlgorithm;
import org.sortapp.algorithm.MergeSortAlgorithm;
import org.sortapp.application.SortingAlgorithmFactory;
import org.sortapp.application.SortingUseCase;
import org.sortapp.domain.SortingResult;
import org.sortapp.domain.exception.ResourceException;

import org.sortapp.domain.port.AlgorithmSelector;
import org.sortapp.domain.port.DataSource;
import org.sortapp.domain.port.ResultOutput;
import org.sortapp.domain.port.SortingAlgorithm;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortingUseCaseTest {

    @Test
    void execute_withBubbleSort_shouldProcessDataCorrectly() throws ResourceException {
        // Setup
        int[] testData = {5, 3, 1, 4, 2};
        int[] expectedSorted = {1, 2, 3, 4, 5};

        DataSource dataSource = mock(DataSource.class);
        when(dataSource.fetchData()).thenReturn(testData);

        AlgorithmSelector algorithmSelector = mock(AlgorithmSelector.class);
        when(algorithmSelector.selectAlgorithm()).thenReturn("bubble");

        ResultOutput resultOutput = mock(ResultOutput.class);

        SortingAlgorithmFactory algorithmFactory = mock(SortingAlgorithmFactory.class);
        when(algorithmFactory.createAlgorithm("bubble")).thenReturn(new BubbleSortAlgorithm());

        // Execute
        SortingUseCase useCase = new SortingUseCase(dataSource, algorithmSelector, resultOutput, algorithmFactory);
        useCase.execute();

        // Verify
        verify(dataSource).fetchData();
        verify(algorithmSelector).selectAlgorithm();
        verify(algorithmFactory).createAlgorithm("bubble");
        verify(resultOutput).displayFetchedData(testData);

        // Capture and verify the SortingResult
        ArgumentCaptor<SortingResult> resultCaptor = ArgumentCaptor.forClass(SortingResult.class);
        verify(resultOutput).displaySortedResult(eq("bubble"), resultCaptor.capture());

        SortingResult capturedResult = resultCaptor.getValue();
        assertArrayEquals(expectedSorted, capturedResult.sortedData());
        assertTrue(capturedResult.sortingTime() >= 0);
    }

    @Test
    void execute_withMergeSort_shouldProcessDataCorrectly() throws ResourceException {
        // Setup
        int[] testData = {10, 2, 8, 6, 4};
        int[] expectedSorted = {2, 4, 6, 8, 10};

        DataSource dataSource = mock(DataSource.class);
        when(dataSource.fetchData()).thenReturn(testData);

        AlgorithmSelector algorithmSelector = mock(AlgorithmSelector.class);
        when(algorithmSelector.selectAlgorithm()).thenReturn("merge");

        ResultOutput resultOutput = mock(ResultOutput.class);

        SortingAlgorithmFactory algorithmFactory = mock(SortingAlgorithmFactory.class);
        when(algorithmFactory.createAlgorithm("merge")).thenReturn(new MergeSortAlgorithm());

        // Execute
        SortingUseCase useCase = new SortingUseCase(dataSource, algorithmSelector, resultOutput, algorithmFactory);
        useCase.execute();

        // Verify
        verify(dataSource).fetchData();
        verify(algorithmSelector).selectAlgorithm();
        verify(algorithmFactory).createAlgorithm("merge");
        verify(resultOutput).displayFetchedData(testData);

        // Capture and verify the SortingResult
        ArgumentCaptor<SortingResult> resultCaptor = ArgumentCaptor.forClass(SortingResult.class);
        verify(resultOutput).displaySortedResult(eq("merge"), resultCaptor.capture());

        SortingResult capturedResult = resultCaptor.getValue();
        assertArrayEquals(expectedSorted, capturedResult.sortedData());
        assertTrue(capturedResult.sortingTime() >= 0);
    }

    @Test
    void execute_withException_shouldHandleGracefully() throws ResourceException {
        // Setup
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.fetchData()).thenThrow(new ResourceException("Test exception"));

        AlgorithmSelector algorithmSelector = mock(AlgorithmSelector.class);
        when(algorithmSelector.selectAlgorithm()).thenReturn("bubble");

        ResultOutput resultOutput = mock(ResultOutput.class);

        SortingAlgorithmFactory algorithmFactory = mock(SortingAlgorithmFactory.class);

        // Execute
        SortingUseCase useCase = new SortingUseCase(dataSource, algorithmSelector, resultOutput, algorithmFactory);
        useCase.execute();

        // Verify
        verify(dataSource).fetchData();
        verify(algorithmSelector).selectAlgorithm();
        verifyNoInteractions(resultOutput);
    }
}

