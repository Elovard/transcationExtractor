package extractor.factory;

import extractor.Application;
import extractor.parser.FileParser;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserFactoryTest {

    @Test
    void whenGivenExtensionIsCsv_ThenCsvParserShouldBeCreated() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        String csvExtension = "csv";
        String expectedTypeOfParser = "csv";
        ParserFactory parserFactory = (ParserFactory) context.getBean("parserFactory");
        FileParser fileParser = parserFactory.createParser(csvExtension);
        assertEquals(expectedTypeOfParser, fileParser.getSupportedFileType());
    }

    @Test
    void whenGivenExtensionIsXml_ThenXmlParserShouldBeCreated() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        String xmlExtension = "xml";
        String expectedTypeOfParser = "xml";
        ParserFactory parserFactory = (ParserFactory) context.getBean("parserFactory");
        FileParser fileParser = parserFactory.createParser(xmlExtension);
        assertEquals(expectedTypeOfParser, fileParser.getSupportedFileType());
    }

    @Test
    void whenGivenExtensionIsNotSupported_ThenExceptionShouldBeThrown() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Application app = context.getBean(Application.class);
        String path = "file.test";
        String extension = app.getExtension(path);
        ParserFactory parserFactory = (ParserFactory) context.getBean("parserFactory");
        FileParser fileParser = parserFactory.createParser(extension);
        assertThrows(Exception.class, () -> fileParser.parse(path));

    }

}