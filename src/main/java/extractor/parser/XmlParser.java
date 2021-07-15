package extractor.parser;

import extractor.builder.TransactionBuilder;
import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Date;
import java.util.List;

public class XmlParser implements FileParser {

    private static final List<Transaction> listOfTransactions = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    @Override
    public List<Transaction> parse(String filePath) throws Exception {
        logger.info("entering parse method in XmlParser");
        File inputFile = new File(filePath);

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputFile);
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

                    Transaction transaction = new TransactionBuilder()
                            .setDate(date)
                            .setTransactionId(parsedTransId)
                            .setUserId(parsedUserId)
                            .setAmount(Double.parseDouble(fromStringToDouble.replaceAll("\\s", "")))
                            .setCurrency(parsedCurrency)
                            .setTransactionStatus(TransactionStatus.valueOf(parsedStatus.toUpperCase()))
                            .getResult();

                    logger.info("adding new transaction to the list");
                    listOfTransactions.add(transaction);

                }
            }

        } catch (ParserConfigurationException e) {
            System.out.println("XML document can't be created");
            logger.error("failed to read XML document");
        } catch (IOException | SAXException e) {
            logger.error("can't find this file");
            throw new Exception("Can't find your file!");
        } catch (ParseException e) {
            logger.error("parse error");
            e.printStackTrace();
        }
        logger.info("returning the list of transactions");
        return listOfTransactions;
    }

    @Override
    public String getSupportedFileType() {
        return "xml";
    }
}
