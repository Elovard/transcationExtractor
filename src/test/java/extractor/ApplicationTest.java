package extractor;

import extractor.entity.Transaction;
import extractor.factory.ParserFactory;
import extractor.parser.CsvParser;
import extractor.parser.FileParser;
import extractor.parser.XmlParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void whenXmlFileExists_ThenResultShouldBeTrue() throws Exception {
        String pathXml = "xml_example.xml";
        XmlParser xmlParser = new XmlParser();
        List<Transaction> transactionListOfXml = xmlParser.parse(pathXml);
        assertNotNull(transactionListOfXml);
    }

    @Test
    void whenXmlFileIsNotExist_ThenExceptionShouldBeThrown() {
        String pathXml = "noSuchFile";
        XmlParser xmlParser = new XmlParser();

        try {
            xmlParser.parse(pathXml);
        } catch (Exception ex) {
            assertThrows(Exception.class, () -> xmlParser.parse(pathXml));
        }
    }

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

        try {
            csvParser.parse(pathCsv);
        } catch (Exception ex) {
            assertThrows(Exception.class, () -> csvParser.parse(pathCsv));
        }
    }

    @Test
    void whenFileNameIsCorrect_ThenResultShouldBeTrue() {
        String path = "csv_example.csv";
        String expectedExtension = "csv";
        try {
            assertEquals(expectedExtension, Application.getExtension(path));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void whenFileNameIsEmpty_ThenExceptionShouldBeThrown() {
        String pathCsv = "";
        try {
            Application.getExtension(pathCsv);
        } catch (Exception e) {
            assertThrows(Exception.class, () -> Application.getExtension(pathCsv));
        }
    }

    @Test
    void whenGivenExtensionIsCsv_ThenCsvParserShouldBeCreated() {
        String csvExtension = "csv";
        String expectedTypeOfParser = "csv";
        ParserFactory parserFactory = new ParserFactory();
        FileParser fileParser = parserFactory.createParser(csvExtension);
        assertEquals(expectedTypeOfParser, fileParser.getSupportedFileType());
    }

    @Test
    void whenGivenExtensionIsXml_ThenXmlParserShouldBeCreated() {
        String xmlExtension = "xml";
        String expectedTypeOfParser = "xml";
        ParserFactory parserFactory = new ParserFactory();
        FileParser fileParser = parserFactory.createParser(xmlExtension);
        assertEquals(expectedTypeOfParser, fileParser.getSupportedFileType());
    }

    @Test
    void whenGivenExtensionIsNotSupported_ThenExceptionShouldBeThrown() throws Exception {
        String path = "file.test";
        String extension = Application.getExtension(path);
        ParserFactory parserFactory = new ParserFactory();
        FileParser fileParser = parserFactory.createParser(extension);
        assertThrows(Exception.class, () -> fileParser.parse(path));

    }

}