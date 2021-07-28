package extractor.parser;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import extractor.exception.TransactionFileParsingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    public ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    void whenCsvFileExists_ThenResultShouldBeTrue() throws Exception {
        String pathCsv = "csv_example.csv";
        CsvParser csvParser = (CsvParser) context.getBean("csvParser");
        List<Transaction> transactions = csvParser.parse(pathCsv);
        assertNotNull(transactions);
    }

    @Test
    void whenCsvFileIsNotExist_ThenExceptionShouldBeThrown() {
        String pathCsv = "noSuchFile";
        CsvParser csvParser = (CsvParser) context.getBean("csvParser");
        assertThrows(Exception.class, () -> csvParser.parse(pathCsv));
    }

    @ParameterizedTest
    @MethodSource
    void whenGivenLineHas6Parameters_ThenParseShouldBeSuccessful(String line) {
        String[] parsedData = line.split(",");
        int expectedSizeOfLine = 6;
        int actualSizeOfLine = parsedData.length;
        assertEquals(expectedSizeOfLine, actualSizeOfLine);
    }

    private static Stream<Arguments> whenGivenLineHas6Parameters_ThenParseShouldBeSuccessful() {
        return Stream.of(
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,usd,success"),
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623,usd, rejected"),
                Arguments.of("1619674154,1c22f114-8251-404e-8c3f-73b70bd0ec80,a00a7fb0-3a72-454d-865d-8f6818f8dd62,34623, eur, failed")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenGivenLineHasLessOrMoreThan6Parameters_ThenParseShouldFail(String line) {
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

    @Test
    void whenFileExists_ThenAllFieldsShouldBeSetCorrectly() throws TransactionFileParsingException {
        String path = "src/test/resources/sample/csv_sample.csv";
        CsvParser parser = new CsvParser();
        List<Transaction> transactions = parser.parse(path);
        Transaction transaction = transactions.get(0);

        long parsedLong = Long.parseLong("1619677426");
        Timestamp timestamp = new Timestamp(parsedLong);
        Date date = new Date(timestamp.getTime());

        assertEquals(date, transaction.getTimestamp());
        assertEquals("11111", transaction.getTransactionId());
        assertEquals("22222", transaction.getUserId());
        assertEquals(150, transaction.getAmount());
        assertEquals("usd", transaction.getCurrency());
        assertEquals(TransactionStatus.FAILED, transaction.getTransactionResult());
    }

}