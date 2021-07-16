package extractor.parser;

import extractor.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}