package extractor.factories;

import extractor.parser.FileParser;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ParserFactory {

    public static FileParser createParser(String extension) {

        Reflections reflections = new Reflections("extractor");
        Set<Class<? extends FileParser>> classes = reflections.getSubTypesOf(FileParser.class);

        for (Class<? extends FileParser> one : classes) {
            try {
                Method method = one.getDeclaredMethod("getSupportedFileType", null);
                String result = (String) method.invoke(one.newInstance(), null);

                if (extension.equals(result)) {
                    return one.newInstance();
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Unsupported type of file");
        return null;
    }

}
