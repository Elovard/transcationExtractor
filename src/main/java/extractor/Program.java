package extractor;

import extractor.exception.TransactionException;

public class Program {

    public static void main(String[] args) throws Exception {
        Application app = new Application();

        try {
            app.settingUp(args[0]);
        } catch (TransactionException ex) {
            System.out.println("Error occurred during execution of the program");
        }
    }
}
