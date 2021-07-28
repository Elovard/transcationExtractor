package extractor.factory;

import extractor.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParserFactory {

    private static final Logger logger = LoggerFactory.getLogger(ParserFactory.class);

    @Autowired
    private final List<FileParser> parserBeans;

    private final Map<String, FileParser> parsers = new HashMap<>();

    public ParserFactory(List<FileParser> parserBeans) {
        this.parserBeans = parserBeans;
        init();
        logger.info("Created ParserFactory");
    }

    private void init() {
        for (FileParser fileParser : parserBeans) {
            parsers.put(fileParser.getSupportedFileType(), fileParser);
        }
        logger.info("Added parsers to the Map");
    }

    public FileParser createParser(final String extension) {
        logger.info("creating parser");
        return this.parsers.get(extension);
    }

}
