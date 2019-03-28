package sk.itsovy.sk;

import org.json.simple.parser.ParseException;
import sk.itsovy.sk.exception.BillException;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws BillException, IOException, ParseException, SQLException {
        Application app=Application.getInstance();
        app.example();

    }
}
