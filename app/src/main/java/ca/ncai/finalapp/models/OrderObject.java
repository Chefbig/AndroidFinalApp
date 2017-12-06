package ca.ncai.finalapp.models;

/**
 * Created by niang on 12/6/2017.
 */

public class OrderObject extends Order {
    public String username;
    public String dealtitle;
    public String unitprice;
    private String _total;

    public OrderObject(){
    }
    public OrderObject(String deal, String paid, String quantity, String shipped, String user, String username, String dealtitle, String unitprice){

        super(deal,  paid,  quantity,  shipped, user);
        this.username = username;
        this.dealtitle = dealtitle;
        this.unitprice = unitprice;
    }

    public String total(){
        if(unitprice == null)
        {
            unitprice = "1";
        }

        if(quantity == null)
        {
            quantity ="1";
        }
        return Double.toString(Double.parseDouble(unitprice.replaceAll("[^\\d.]+", "")) * Double.parseDouble(quantity));
    }
}
