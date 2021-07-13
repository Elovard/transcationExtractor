package extractor.factory;

import extractor.parser.FileParser;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParserFactory {

    public static final String ROOT_PACKAGE = "extractor";
    private static final Logger logger = LoggerFactory.getLogger(ParserFactory.class);

    private final Map<String, FileParser> parsers = new HashMap<>();

    public ParserFactory() {
        Reflections reflections = new Reflections(ROOT_PACKAGE);
        Set<Class<? extends FileParser>> implementationsOfFileParser = reflections.getSubTypesOf(FileParser.class);

        for (Class<? extends FileParser> impl : implementationsOfFileParser) {
            try {
                Method method = impl.getDeclaredMethod("getSupportedFileType", null);
                String result = (String) method.invoke(impl.newInstance(), null);

                parsers.put(result, impl.newInstance());
                logger.info("adding parser" + result + " to the list");

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public FileParser createParser(final String extension) {
        logger.info("creating parser");
        return this.parsers.get(extension);
    }

}
