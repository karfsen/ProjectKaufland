package sk.itsovy.sk.Items.Food;

import sk.itsovy.sk.Items.Pcs;

public class Sweets extends Food implements Pcs {
    private int amount;
    public Sweets(String name, double price, int amount, int callories) {
        super(name, price, callories);
        this.amount = amount;
    }

    public Sweets(String name, double price, int amount) {
        this(name, price,amount,-1);
    }

    @Override
    public double getTotalPrice() {
        return amount*getPrice();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
