package extractor;

public class XmlFactory implements ParserFactory{

    @Override
    public FileParser createParser() {
        return new XmlParser();
    }
}
