package sk.itsovy.sk.Items.Drink;

import sk.itsovy.sk.Items.Pcs;

public class Bottle extends Drink implements Pcs {
    private int amount;

    public Bottle(String name, double price, boolean sweet, int amount) {
        super(name, price, sweet);
        this.amount = amount;
    }

    public Bottle(String name, double price, int amount) {
        this(name, price, false,amount);
    }

    public Bottle(String name, double price,boolean sweet) {
        this(name, price, sweet,1);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public double getTotalPrice() {
        return amount*getPrice();
    }
}
