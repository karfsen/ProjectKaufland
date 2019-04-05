package sk.itsovy.sk.Database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import sk.itsovy.sk.Bill.Bill;
import sk.itsovy.sk.Global;
import com.mongodb.client.MongoDatabase;
import sk.itsovy.sk.Items.Drink.DraftInterface;
import sk.itsovy.sk.Items.Food.Fruit;
import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.Items.Pcs;

import javax.swing.text.Document;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Mongo {
        private static Mongo mdb=new Mongo();

    public Mongo(){

    }

    public static Mongo getMdb() {
        return mdb;
    }

    public MongoDatabase MongoCon(){
        try
        {
            MongoClient mongoClient = new MongoClient(new MongoClientURI(Global.mongoURL));

            MongoCredential credential;
            credential = MongoCredential.createCredential(Global.userMongo, Global.mongoDB,Global.mongoPW.toCharArray());
            MongoDatabase database = mongoClient.getDatabase(Global.mongoDB);

            for (String name : database.listCollectionNames()) {

                System.out.println(name);
            }
            return database;

        }
            catch(Exception e)
        {
            System.out.println(e);
            return null;
        }

    }

    public MongoCollection<org.bson.Document> accessBill(){
        MongoDatabase database = this.MongoCon();

        try {
            // Retieving a collection Bill
            MongoCollection<org.bson.Document> collection = database.getCollection("bills");
            System.out.println("Collection bill selected successfully");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public MongoCollection<org.bson.Document> accessItems(){
        MongoDatabase database = MongoCon();

        try {
            // Retieving a collection Items
            MongoCollection<org.bson.Document> collection = database.getCollection("items");
            System.out.println("Collection items selected successfully");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void insertBillToMDB(Bill bill){
        MongoCollection<org.bson.Document> collectionBill = accessBill();
        MongoCollection<org.bson.Document> collectionItem = accessItems();




        org.bson.Document documentBill = new org.bson.Document("date", String.valueOf(bill.getDateTime()))
                .append("totalPrice", bill.getFinalPrice());
        collectionBill.insertOne(documentBill);
        System.out.println("Document Bill insertion succesfull");
        ObjectId id = documentBill.getObjectId("_id");

        for (Item item:bill.getBill()) {
            org.bson.Document documentItem = new org.bson.Document("name",item.getName())
                    .append("idBill",id)
                    .append("price",item.getPrice());
            if (item instanceof DraftInterface){
                documentItem.append("count",((DraftInterface) item).getVolume());
                documentItem.append("unit", "l");

            }else if(item instanceof Fruit){
                documentItem.append("count",((Fruit) item).getWeight());
                documentItem.append("unit", "kg");

            }else if(item instanceof Pcs){
                documentItem.append("count",((Pcs) item).getAmount());
                documentItem.append("unit", "pcs");
            }
            collectionItem.insertOne(documentItem);
            System.out.println("Document item inserted");
        }
    }
}
