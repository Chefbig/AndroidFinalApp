package ca.ncai.finalapp.models;

/**
 * Created by niang on 12/6/2017.
 */

public class Order {
    public String deal;
    public String paid;
    public String quantity;
    public String shipped;
    public String user;

    public Order(){}
    public Order(String deal, String paid, String quantity, String shipped, String user){
        this.deal = deal;
        this.paid = paid;
        this.quantity = quantity;
        this.shipped = shipped;
        this.user = user;
    }

}
