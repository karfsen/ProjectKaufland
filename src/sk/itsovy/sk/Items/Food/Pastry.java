package sk.itsovy.sk.Items.Food;

import sk.itsovy.sk.Items.Pcs;

public class Pastry extends Food implements Pcs {
    private int amount;

    @Override
    public double getTotalPrice() {
        return amount*getPrice();
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public Pastry(String name, double price, int amount, int callories) {
        super(name, price, callories);
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Pastry(String name, double price, int amount) {
        this(name,price,amount,-1);
    }
}
