package extractor.parser;

import extractor.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    @Test
    void whenCsvFileExists_ThenResultShouldBeTrue() throws Exception {
        String pathCsv = "csv_example.csv";
        CsvParser csvParser = new CsvParser();
        List<Transaction> transactionListOfCsv = csvParser.parse(pathCsv);
        assertNotNull(transactionListOfCsv);
    }

    @Test
    void whenCsvFileIsNotExist_ThenExceptionShouldBeThrown() {
        String pathCsv = "noSuchFile";
        CsvParser csvParser = new CsvParser();
        assertThrows(Exception.class, () -> csvParser.parse(pathCsv));
    }

    @ParameterizedTest
    @MethodSource
    void whenGivenLineHas6Parameters_ThenParseShouldBeSuccessful(String line) {
        CsvParser csvParser = new CsvParser();
        String[] parsedData = line.split(",");
        int expectedSizeOfLine = 6;
        int actualSizeOfLine = parsedData.length;
        assertEquals(expectedSizeOfLine, actualSizeOfLine);
    }

    private static Stream<Arguments> whenGivenLineHas6Parameters_ThenParseShouldBeSuccessful() {
        return Stream.of(
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,usd,success"),
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,usd, rejected"),
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623, euro, failed")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenGivenLineHasLessOrMoreThan6Parameters_ThenParseShouldFail(String line) {
        CsvParser csvParser = new CsvParser();
        String[] parsedData = line.split(",");
        int expectedSizeOfLine = 6;
        int actualSizeOfLine = parsedData.length;
        assertNotEquals(expectedSizeOfLine, actualSizeOfLine);

    }

    private static Stream<Arguments> whenGivenLineHasLessOrMoreThan6Parameters_ThenParseShouldFail() {
        return Stream.of(
                Arguments.of("1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,usd,success"),
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,usd"),
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,success")
        );
    }

}