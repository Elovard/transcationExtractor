package extractor.command;

import extractor.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintAllTransactionsCommand extends Command implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PrintAllTransactionsCommand.class);

    @Override
    public void execute(final List<Transaction> transactions) {
        System.out.println("Here's the list of transactions: ");
        transactions.forEach(System.out::println);
    }

    @Override
    public String getCommandDescription() {
        return "Print all transactions";
    }
}
