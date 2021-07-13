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
    public String getSupportedFileType() {
        return "xml";
    }
}
