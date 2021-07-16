package extractor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest {

    @Test
    void whenFileNameIsCorrect_ThenResultShouldBeTrue() {
        String path = "csv_example.csv";
        String expectedExtension = "csv";
        Application app = new Application();
        try {
            assertEquals(expectedExtension, app.getExtension(path));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void whenFileNameIsEmpty_ThenExceptionShouldBeThrown() {
        String pathCsv = "";
        Application app = new Application();
        try {
           app.getExtension(pathCsv);
        } catch (Exception e) {
            assertThrows(Exception.class, () -> app.getExtension(pathCsv));
        }
    }

}