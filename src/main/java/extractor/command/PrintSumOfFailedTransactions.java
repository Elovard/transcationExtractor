package extractor.command;

import extractor.config.ApplicationContext;
import extractor.entity.Transaction;
import extractor.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PrintSumOfFailedTransactions extends Command {

    private static final Logger logger = LoggerFactory.getLogger(PrintTopFiveTransactionsCommand.class);

//    private static final ClassPathXmlApplicationContext context = ApplicationContext.getInstance().getContext();
//    private final TransactionService transactionService;

    @Autowired
    public PrintSumOfFailedTransactions(TransactionService transactionService) {
//        this.transactionService = (TransactionService) context.getBean("transactionService");
    }

    @Override
    public void execute(List<Transaction> transactionList) {
        final int sumOfFailedTransactions = transactionService.countSumOfFailedTransactions(transactionList);
        System.out.println("Sum of failed transactions: " + sumOfFailedTransactions);
    }

    @Override
    public String getCommandDescription() {
        return "Print sum of failed transactions";
    }
}
