package extractor.parser;

import extractor.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlParserTest {

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
        assertThrows(Exception.class, () -> xmlParser.parse(pathXml));
    }

}