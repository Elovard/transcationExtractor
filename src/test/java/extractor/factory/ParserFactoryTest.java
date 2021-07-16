package extractor.factory;

import extractor.Application;
import extractor.parser.FileParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserFactoryTest {

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
        Application app = new Application();
        String extension = app.getExtension(path);
        ParserFactory parserFactory = new ParserFactory();
        FileParser fileParser = parserFactory.createParser(extension);
        assertThrows(Exception.class, () -> fileParser.parse(path));

    }

}