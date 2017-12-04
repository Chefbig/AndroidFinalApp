package ca.ncai.finalapp.models;

/**
 * Created by niang on 12/3/2017.
 */

public class Deal{
    public String price;
    public String description;
    public String title;
    public String url;

    public Deal(){}
    public Deal(String title, String url, String price, String description) {
        this.title = title;
        this.url = url;
        this.price = price;
        this.description = description;
    }
}
