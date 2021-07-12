package extractor.factories;

import extractor.parsers.CsvParser;
import extractor.parsers.FileParser;

public class CsvFactory implements ParserFactory {

    @Override
    public FileParser createParser() {
        return new CsvParser();
    }
}
