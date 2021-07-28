package extractor;

import extractor.entity.Transaction;
import extractor.exception.ApplicationException;
import extractor.exception.ExtensionResolvingException;
import extractor.parser.FileParser;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {


    public ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    public Application app = context.getBean(Application.class);


    @Test
    void whenCommandLineArgumentsContainMoreThanOneCommand_ThenOnlyOneArgumentShouldBeSaved()
            throws ExtensionResolvingException {

        String[] commandLineArguments = {"file=test.csv", "file=file.xml", "file=sample.dat"};
        Map<String, String> parsedArguments = app.parseCommandLineArguments(commandLineArguments);
        assertEquals(1, parsedArguments.size());
    }

    @Test
    void whenArgumentsAreInvalid_ThenTheyShouldNotBeSaved() throws ExtensionResolvingException {
        String[] commandLineArguments = {"file=file=file", "test=test=test=test=test", "sample"};
        Map<String, String> parsedArguments = app.parseCommandLineArguments(commandLineArguments);
        assertEquals(0, parsedArguments.size());
    }

    @Test
    void whenFileTypeIsSupported_ThenParserShouldBeCreated() throws ExtensionResolvingException {
        String extension = "csv";
        FileParser fileParser = app.resolveParser(extension);
        assertNotNull(fileParser);
    }

    @Test
    void whenFileTypeUnsupported_ThenExceptionShouldBeThrown() {
        String extension = "test";
        assertThrows(ExtensionResolvingException.class, () -> app.resolveParser(extension));
    }

    @Test
    void whenExtensionIsNull_ThenExceptionShouldBeThrown() {
        String extension = null;
        assertThrows(ExtensionResolvingException.class, () -> app.resolveParser(extension));
    }

    @Test
    void whenCommandIsNotFound_ThenExceptionShouldBeThrown() throws ApplicationException {
        int noSuchCommandId = 10;
        List<Transaction> transactions = new ArrayList<>();
        assertThrows(ApplicationException.class, () -> app.executeCommand(noSuchCommandId, transactions));
    }

}