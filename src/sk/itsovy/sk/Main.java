package sk.itsovy.sk;

import sk.itsovy.sk.exception.BillException;

public class Main {
    public static void main(String[] args) throws BillException {
        Application app=Application.getInstance();
        app.example();
    }
}
