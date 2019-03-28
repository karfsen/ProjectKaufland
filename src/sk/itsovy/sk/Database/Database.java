package sk.itsovy.sk.Database;

import sk.itsovy.sk.Bill.Bill;
import sk.itsovy.sk.Global;
import sk.itsovy.sk.Items.Drink.DraftInterface;
import sk.itsovy.sk.Items.Food.Fruit;
import sk.itsovy.sk.Items.Item;
import sk.itsovy.sk.Items.Pcs;

import java.sql.*;

public class Database {

    private static Database db = new Database();

    private Database(){

    }

    public static Database getInstance(){
        return db;
    }
    public void insertNewBill(Bill bill) throws SQLException {
        Connection conn = Global.getConnection();
        PreparedStatement sqlPreparedStatement = null;
        PreparedStatement secondSqlStatement = null;
        try {
            conn.setAutoCommit(false);

            sqlPreparedStatement = conn.prepareStatement("INSERT INTO Bill (totalPrice) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            sqlPreparedStatement.setDouble(1, bill.getFinalPrice());

            sqlPreparedStatement.execute();

            int key = 0;
            ResultSet rs = sqlPreparedStatement.getGeneratedKeys();
            if(rs.next()){
                key = rs.getInt(1);
            }
            if(key == 0) {
                throw new SQLException("wrong id");
            }

            for(Item i: bill.getBill()) {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO billItems (billID, name,price, count, unit) VALUES(?,?,?,?,?)");
                statement.setLong(1, key);
                statement.setString(2, i.getName());
                statement.setDouble(3, i.getPrice());
                if(i instanceof Pcs) {
                    statement.setDouble(4,((Pcs) i).getAmount());
                    statement.setString(5, "pcs");
                }else if(i instanceof Fruit) {
                    statement.setDouble(4,((Fruit) i).getWeight());
                    statement.setString(5, "g");
                }else if(i instanceof DraftInterface) {
                    statement.setDouble(4,((DraftInterface) i).getVolume());
                    statement.setString(5, "l");
                }
                statement.executeUpdate();
            }
            conn.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
        finally {
            if (sqlPreparedStatement != null) {
                sqlPreparedStatement.close();
            }
            if (secondSqlStatement != null) {
                secondSqlStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

}