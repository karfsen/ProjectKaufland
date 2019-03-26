package sk.itsovy.sk;

import sk.itsovy.sk.Bill.Bill;
import sk.itsovy.sk.Items.Drink.Bottle;
import sk.itsovy.sk.Items.Drink.Draft;
import sk.itsovy.sk.Items.Drink.Drink;
import sk.itsovy.sk.Items.Food.Fruit;
import sk.itsovy.sk.Items.Food.Pastry;
import sk.itsovy.sk.Items.Goods.Category;
import sk.itsovy.sk.Items.Goods.Goods;
import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.exception.BillException;



public class Application {

    //--------------Design pattern singleton

    private static Application app=new Application();

    private Application() {
    }

    public static Application getInstance(){
        return app;
    }

    //--------------END

    public void example() throws BillException {
        Bill bill=new Bill();
        Bottle milk=new Bottle("Milk 1,5%",0.59,2);
        bill.addItem(milk);
        Item pizza=new Pastry("Pizza salama",1.10,2,466);
        bill.addItem(pizza);
        Fruit fruit=new Fruit("Apple",0.59,370);
        bill.addItem(fruit);
        Goods pencil=new Goods("Pencil",0.20,1, Category.SCHOOL);
        bill.addItem(pencil);
        Draft vinea=new Draft("Vinea red",1.20,true,0.5);
        bill.addItem(vinea);
        Draft beer=new Draft("Beer",1,false,0.5);
        bill.addItem(beer);
        bill.removeItem(beer);
        bill.getFinalPrice();
        bill.printBill();
        bill.end();
    }
}
