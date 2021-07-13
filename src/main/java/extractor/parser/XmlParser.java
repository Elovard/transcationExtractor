package extractor.parser;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class XmlParser implements FileParser {

    private static List<Transaction> listOfTransactions = new ArrayList<>();

    @Override
    public List<Transaction> parse(String filePath) {
        File inputFile = new File(filePath);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("transaction");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String parsedTransId = eElement.getElementsByTagName("id").item(0).getTextContent();
                    String parsedUserId = eElement.getElementsByTagName("user").item(0).getChildNodes()
                            .item(1).getTextContent();
                    String parsedTimestamp = eElement.getElementsByTagName("timestamp").item(0).getTextContent();
                    String parsedAmount = eElement.getElementsByTagName("amount").item(0).getTextContent();
                    String parsedCurrency = eElement.getElementsByTagName("currency").item(0).getTextContent();
                    String parsedStatus = eElement.getElementsByTagName("status").item(0).getTextContent();

                    String fromStringToDouble = String.valueOf(parsedAmount);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                    Date date = format.parse(parsedTimestamp);

                    listOfTransactions.add(new Transaction(
                            date,
                            parsedTransId,
                            parsedUserId,
                            Double.parseDouble(fromStringToDouble.replaceAll("\\s", "")),
                            parsedCurrency,
                            TransactionStatus.valueOf(parsedStatus.toUpperCase())));

                }
            }

        } catch (ParserConfigurationException e) {
            System.out.println("XML document can't be created");
        } catch (IOException | SAXException e) {
            System.out.println("IO exception has occurred during parsing the file");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listOfTransactions;
    }

    @Override
    public List<Transaction> printAllTransactions() {
        System.out.println("Here's the list of transactions: ");
        listOfTransactions.forEach(System.out::println);
        return listOfTransactions;
    }

    @Override
    public List<Transaction> printTopFiveTransactions() {
        List<Double> amountsOfTransactions = new ArrayList<>();
        for (Transaction transaction : listOfTransactions) {
            String convert = String.valueOf(transaction.getAmount());
            amountsOfTransactions.add(Double.valueOf(convert.replaceAll("\\s", "")));
        }
        if (amountsOfTransactions.size() < 5) {
            System.out.println("The list of transactions contains less than 5 transactions");
            return null;
        }
        amountsOfTransactions.sort(Comparator.reverseOrder());
        System.out.println(
                "Top-5 transactions: " + amountsOfTransactions.get(0) + ", " +
                        amountsOfTransactions.get(1) + ", " +
                        amountsOfTransactions.get(2) + ", " +
                        amountsOfTransactions.get(3) + ", " +
                        amountsOfTransactions.get(4));
        return listOfTransactions;
    }

    @Override
    public List<Transaction> printTotals() {
        System.out.println("Total number of transactions: " + listOfTransactions.size());

        int successful = 0;
        int failed = 0;
        int rejected = 0;

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("COMPLETE"))) {
                successful++;
            }
        }
        System.out.println("\tWhere successful: " + successful);

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("FAILURE"))) {
                failed++;
            }
        }
        System.out.println("\t\t\tfailed: " + failed);

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("REJECTED"))) {
                rejected++;
            }
        }
        System.out.println("\t\t\trejected: " + rejected);
        return listOfTransactions;
    }

    @Override
    public String getSupportedFileType() {
        return "xml";
    }
}
