package sk.itsovy.sk.Items.Drink;

public class Draft extends Drink implements DraftInterface{
    private double volume;

    public double getVolume() {
        return volume;
    }

    public Draft(String name, double price, boolean sweet, double volume) {
        super(name, price, sweet);
        this.volume = volume;
    }

    @Override
    public double getTotalPrice() {
        return volume*getPrice();
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
