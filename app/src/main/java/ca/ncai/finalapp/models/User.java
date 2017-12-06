package ca.ncai.finalapp.models;

/**
 * Created by niang on 12/6/2017.
 */

public class User {
    public String email;
    public String firstname;
    public String lastname;
    public String username;

    public User(){}
    public User(String email, String firstname, String lastname, String username)
    {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
    }
}
