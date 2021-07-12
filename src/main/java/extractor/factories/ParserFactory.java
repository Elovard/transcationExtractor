package extractor.factories;

import extractor.parsers.FileParser;

public interface ParserFactory {
    FileParser createParser();
}
