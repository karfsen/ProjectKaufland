package sk.itsovy.sk.Items.Goods;

import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.Items.Pcs;

public class Goods extends Item implements Pcs {
    private int amount;
    private Category type;

    public int getAmount() {
        return amount;
    }

    public Category getType() {
        return type;
    }

    public Goods(String name, double price, int amount, Category type) {
        super(name, price);
        this.amount = amount;
        this.type = type;
    }

    @Override
    public double getTotalPrice() {
        return amount*getPrice();
    }
}
