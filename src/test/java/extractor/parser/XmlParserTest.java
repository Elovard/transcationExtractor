package extractor.parser;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import extractor.exception.TransactionFileParsingException;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlParserTest {

    public ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    void whenXmlFileExists_ThenResultShouldBeTrue() throws Exception {
        String pathXml = "xml_example.xml";
        XmlParser xmlParser = (XmlParser) context.getBean("xmlParser");
        List<Transaction> transactionListOfXml = xmlParser.parse(pathXml);
        assertNotNull(transactionListOfXml);
    }

    @Test
    void whenXmlFileIsNotExist_ThenExceptionShouldBeThrown() {
        String pathXml = "noSuchFile";
        XmlParser xmlParser = (XmlParser) context.getBean("xmlParser");
        assertThrows(Exception.class, () -> xmlParser.parse(pathXml));
    }

    @Test
    void whenFileExists_ThenAllFieldsShouldBeSetCorrectly() throws TransactionFileParsingException, ParseException {
        String path = "src/test/resources/sample/sample.xml";
        XmlParser xmlParser = (XmlParser) context.getBean("xmlParser");
        List<Transaction> transactions = xmlParser.parse(path);
        Transaction transaction = transactions.get(0);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        Date date = format.parse("2021-04-29 16:46:13");

        assertEquals("11111", transaction.getTransactionId());
        assertEquals("22222", transaction.getUserId());
        assertEquals(date, transaction.getTimestamp());
        assertEquals(100, transaction.getAmount());
        assertEquals("USD", transaction.getCurrency());
        assertEquals(TransactionStatus.COMPLETE, transaction.getTransactionResult());
    }

    @Test
    void whenTransactionIdIsNull_ThenResultShouldBeEmpty() throws TransactionFileParsingException {
        String path = "src/test/resources/sample/sample2.xml";
        XmlParser xmlParser = (XmlParser) context.getBean("xmlParser");
        List<Transaction> transactions = xmlParser.parse(path);
        Transaction transaction = transactions.get(0);
        assertTrue(transaction.getTransactionId().isEmpty());
    }
}