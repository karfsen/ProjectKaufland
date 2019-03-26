package sk.itsovy.sk.Items.Drink;

import sk.itsovy.sk.Items.Item;

public abstract class Drink extends Item {
    private boolean sweet;

    public Drink(String name, double price, boolean sweet) {
        super(name, price);
        this.sweet = sweet;
    }

    public boolean isSweet() {
        return sweet;
    }
}
