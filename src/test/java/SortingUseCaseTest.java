import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

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

    SortingUseCase useCase =
        new SortingUseCase(dataSource, algorithmSelector, resultOutput, algorithmFactory);
    useCase.execute();

    verify(dataSource).fetchData();
    verify(algorithmSelector).selectAlgorithm();
    verify(algorithmFactory).createAlgorithm("bubble");
    verify(resultOutput).displayFetchedData(testData);

    ArgumentCaptor<SortingResult> resultCaptor = ArgumentCaptor.forClass(SortingResult.class);
    verify(resultOutput).displaySortedResult(eq("bubble"), resultCaptor.capture());

    SortingResult capturedResult = resultCaptor.getValue();
    assertArrayEquals(expectedSorted, capturedResult.sortedData());
    assertTrue(capturedResult.sortingTime() >= 0);
  }

  @Test
  void execute_withMergeSort_shouldProcessDataCorrectly() throws ResourceException {

    int[] testData = {10, 2, 8, 6, 4};
    int[] expectedSorted = {2, 4, 6, 8, 10};

    DataSource dataSource = mock(DataSource.class);
    when(dataSource.fetchData()).thenReturn(testData);

    AlgorithmSelector algorithmSelector = mock(AlgorithmSelector.class);
    when(algorithmSelector.selectAlgorithm()).thenReturn("merge");

    ResultOutput resultOutput = mock(ResultOutput.class);

    SortingAlgorithmFactory algorithmFactory = mock(SortingAlgorithmFactory.class);
    when(algorithmFactory.createAlgorithm("merge")).thenReturn(new MergeSortAlgorithm());

    SortingUseCase useCase =
        new SortingUseCase(dataSource, algorithmSelector, resultOutput, algorithmFactory);
    useCase.execute();

    verify(dataSource).fetchData();
    verify(algorithmSelector).selectAlgorithm();
    verify(algorithmFactory).createAlgorithm("merge");
    verify(resultOutput).displayFetchedData(testData);

    ArgumentCaptor<SortingResult> resultCaptor = ArgumentCaptor.forClass(SortingResult.class);
    verify(resultOutput).displaySortedResult(eq("merge"), resultCaptor.capture());

    SortingResult capturedResult = resultCaptor.getValue();
    assertArrayEquals(expectedSorted, capturedResult.sortedData());
    assertTrue(capturedResult.sortingTime() >= 0);
  }

  @Test
  void execute_withException_shouldHandleGracefully() throws ResourceException {
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.fetchData()).thenThrow(new ResourceException("Test exception"));

    AlgorithmSelector algorithmSelector = mock(AlgorithmSelector.class);
    when(algorithmSelector.selectAlgorithm()).thenReturn("bubble");

    ResultOutput resultOutput = mock(ResultOutput.class);

    SortingAlgorithmFactory algorithmFactory = mock(SortingAlgorithmFactory.class);

    SortingUseCase useCase =
        new SortingUseCase(dataSource, algorithmSelector, resultOutput, algorithmFactory);
    useCase.execute();

    verify(dataSource).fetchData();
    verify(algorithmSelector).selectAlgorithm();
    verifyNoInteractions(resultOutput);
  }
}
