package sk.itsovy.sk;

import org.json.simple.parser.ParseException;
import sk.itsovy.sk.Database.Mongo;
import sk.itsovy.sk.exception.BillException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws BillException, IOException, ParseException, SQLException, TransformerException, ParserConfigurationException {
        Application app=Application.getInstance();
        app.example();
    }
}
