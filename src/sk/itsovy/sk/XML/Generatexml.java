package sk.itsovy.sk.XML;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sk.itsovy.sk.Bill.Bill;
import sk.itsovy.sk.Database.Database;
import sk.itsovy.sk.Internet;
import sk.itsovy.sk.Items.Drink.DraftInterface;
import sk.itsovy.sk.Items.Food.Fruit;
import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.Items.Pcs;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Generatexml {
    public void generateXML(Bill bill) throws ParserConfigurationException, TransformerException, IOException, ParseException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("bill");
        doc.appendChild(rootElement);
        double totalprice=bill.getFinalPrice();
        double totalUSD=bill.getFinalUSDPrice(Internet.executePost());
        Element datetime = doc.createElement("DateTime");
        rootElement.appendChild(datetime);
        datetime.appendChild(doc.createTextNode(String.valueOf(bill.getTime())));
        Element items = doc.createElement("items");
        rootElement.appendChild(items);

        for (Item i : bill.getBill() ) {
            Element item = doc.createElement("item");
            items.appendChild(item);

            Element name=doc.createElement("name");
            name.appendChild(doc.createTextNode(i.getName()));
            item.appendChild(name);

            Element price=doc.createElement("price");
            price.appendChild(doc.createTextNode(String.valueOf(i.getPrice())));
            item.appendChild(price);

            if(i instanceof Pcs) {
                Element amount=doc.createElement("amount");
                amount.appendChild(doc.createTextNode(String.valueOf(((Pcs) i).getAmount())+" pcs"));
                item.appendChild(amount);
            }
            else if(i instanceof Fruit) {
                Element weight=doc.createElement("weight");
                weight.appendChild(doc.createTextNode(String.valueOf(((Fruit) i).getWeight())+" g"));
                item.appendChild(weight);
            }
            else if(i instanceof DraftInterface) {
                Element vol=doc.createElement("volume");
                vol.appendChild(doc.createTextNode(String.valueOf(((DraftInterface) i).getVolume())+" L"));
                item.appendChild(vol);
            }
        }
        Element total=doc.createElement("totalPrice");
        total.appendChild(doc.createTextNode(String.valueOf(totalprice)));
        rootElement.appendChild(total);

        Element totalUSDprice=doc.createElement("totalUSDPrice");
        totalUSDprice.appendChild(doc.createTextNode(String.valueOf(totalUSD)));
        rootElement.appendChild(totalUSDprice);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("D:\\SOVY\\JAVA\\ProjectKaufland\\src\\output.xml"));
        transformer.transform(source, result);

        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }
}
