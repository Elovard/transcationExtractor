package extractor.factory;

import extractor.config.ApplicationContext;
import extractor.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ParserFactory {

    private static final Logger logger = LoggerFactory.getLogger(ParserFactory.class);
    private static final ClassPathXmlApplicationContext context = ApplicationContext.getInstance().getContext();

    private final Map<String, FileParser> parsers = new HashMap<>();

    public ParserFactory() {
        Collection<FileParser> beans = context.getBeansOfType(FileParser.class).values();
        for (FileParser parser : beans) {
            parsers.put(parser.getSupportedFileType(), parser);
        }
        logger.info("All parsers have been added");
    }

    public FileParser createParser(final String extension) {
        logger.info("creating parser");
        return this.parsers.get(extension);
    }

}
