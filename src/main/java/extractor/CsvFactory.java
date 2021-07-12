package extractor;

public class CsvFactory implements ParserFactory{

    @Override
    public FileParser createParser() {
        return new CsvParser();
    }
}
