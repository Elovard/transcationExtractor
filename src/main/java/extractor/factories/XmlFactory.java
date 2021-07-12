package extractor.factories;

import extractor.parsers.FileParser;
import extractor.parsers.XmlParser;

public class XmlFactory implements ParserFactory {

    @Override
    public FileParser createParser() {
        return new XmlParser();
    }
}
