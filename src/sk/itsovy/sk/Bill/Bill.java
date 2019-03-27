package sk.itsovy.sk.Bill;

import org.json.simple.parser.ParseException;
import sk.itsovy.sk.Internet;
import sk.itsovy.sk.Items.Drink.DraftInterface;
import sk.itsovy.sk.Items.Food.Fruit;
import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.Global;
import sk.itsovy.sk.Items.Pcs;
import sk.itsovy.sk.exception.BillException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Bill {
    boolean open;
    private int pocet;
    private List<Item> list;
    private LocalTime time;
    private LocalDate date;


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
                list.add(item);
                pocet++;
            }
            else{
                String mess="BILL IS FULL , MAX ITEMS : "+Global.MAXITEMS;
                throw new BillException(mess);
            }
        }
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

    public int getCount(){
        return pocet;
    }

    public void end() {
        if (open){
            date=LocalDate.now();
            time=LocalTime.now();
            System.out.println("Date: "+date);

            System.out.println("Time: "+time);
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


}
