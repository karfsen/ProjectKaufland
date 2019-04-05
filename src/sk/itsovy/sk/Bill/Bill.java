package sk.itsovy.sk.Bill;

import org.json.simple.parser.ParseException;
import sk.itsovy.sk.Internet;
import sk.itsovy.sk.Items.Drink.Bottle;
import sk.itsovy.sk.Items.Drink.Draft;
import sk.itsovy.sk.Items.Drink.DraftInterface;
import sk.itsovy.sk.Items.Food.Fruit;
import sk.itsovy.sk.Items.Food.Pastry;
import sk.itsovy.sk.Items.Food.Sweets;
import sk.itsovy.sk.Items.Goods.Goods;
import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.Global;
import sk.itsovy.sk.Items.Pcs;
import sk.itsovy.sk.exception.BillException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Bill {
    boolean open;
    private int pocet;
    private List<Item> list;
    private LocalDateTime date = LocalDateTime.now();



    public Bill() throws IOException, ParseException {
        this.list = new ArrayList<>();
        open=true;
    }

    public void addItem(Item item) throws BillException {
        if(item!=null) {
            if(open==false){
                String mess="Bill is closed, not allowed to add any items";
                throw new BillException(mess);
            }

            if(pocet<=Global.MAXITEMS) {
                Item inBill = checkItem(item);
                if(inBill == null)
                    list.add(item);
                else
                    updateItem(inBill, item);
                pocet++;
            }
            else{
                String mess="BILL IS FULL , MAX ITEMS : "+Global.MAXITEMS;
                throw new BillException(mess);
            }
        }
    }
    public ArrayList<Item> getBill(){
        return (ArrayList<Item>) list;
    }
    public void removeItem(Item item){
        if(list.contains(item)) {
            list.remove(item);
            pocet--;
        }
    }

    public void printBill() throws IOException, ParseException {
        if(pocet==0){
            System.out.println("Nothing to print , Bill is empty");
        }
        else{
            for(Item item:list) {
                if (item instanceof DraftInterface) {
                    System.out.println(item.getName() +"    "+((DraftInterface) item).getVolume());
                    System.out.println(item.getPrice()+"   "+item.getTotalPrice());
                }
                else if(item instanceof Fruit){
                    System.out.println(item.getName() + "   "+ ((Fruit) item).getWeight());
                    System.out.println(item.getPrice()+"   "+item.getTotalPrice());
                }
                else if(item instanceof Pcs){
                    System.out.println(item.getName() + "   "+((Pcs) item).getAmount());
                    System.out.println(item.getPrice()+"   "+item.getTotalPrice());
                }
            }
        }
        System.out.println(getFinalPrice());
        System.out.println(getFinalUSDPrice(Internet.executePost()));
    }

    public Item checkItem(Item item){
        for (Item item1: list) {
            if(item.getName().toLowerCase().equals(item1.getName().toLowerCase()) && item.getClass().getName().equals(item1.getClass().getName()))
                return item1;
        }

        return null;
    }

    public void updateItem(Item newItem, Item oldItem){
        if (newItem instanceof DraftInterface){
            double newVolume = ((DraftInterface) newItem).getVolume() + ((DraftInterface) oldItem).getVolume();
            ((DraftInterface) newItem).setVolume(newVolume);
        }
        else if (newItem instanceof Fruit){
            double newWeight = ((Fruit) newItem).getWeight() + ((Fruit) oldItem).getWeight();
            ((Fruit) newItem).setWeight(newWeight);
        }
        else if(newItem instanceof Pcs){
            int newAmount = ((Pcs) newItem).getAmount() + ((Pcs) oldItem).getAmount();
            ((Pcs) newItem).setAmount(newAmount);
        }
    }

    public int getCount(){
        return pocet;
    }

    public void end() {
        if (open){
            date=LocalDateTime.now();
            System.out.println("Date: "+date);
        }
        open=false;
    }

    public double getFinalPrice(){
        //throw new UnsupportedOperationException("Method does not exists yet");
        double total = 0;
        for(Item item:list){
            if (item instanceof DraftInterface) {
                total = total + item.getTotalPrice();
            }
            else if(item instanceof Fruit){
                total=total+item.getTotalPrice();
            }
            else if(item instanceof Pcs){
                total=total+item.getTotalPrice();
            }
        }
        return Math.round(total*100.0)/100.0;
    }

    public double getFinalUSDPrice(double usd){
        //throw new UnsupportedOperationException("Method does not exists yet");
        double total=getFinalPrice();
        return Math.round(total*usd*100.0)/100.0;
    }

    public LocalDateTime getDateTime() {
        return date;
    }


}
