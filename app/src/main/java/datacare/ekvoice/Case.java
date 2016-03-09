package datacare.ekvoice;

import java.io.Serializable;

/**
 * Created by Robin on 3/5/2016.
 */
public class Case implements Serializable{
    public String lastName;
    public String firstName;
    public String claimNumber;
    public String address;
    public String phoneNumber;
    public Contact manager;
    public Contact carrier;
    public Contact employer;
    public Contact attorney;
    public Contact serviceProvider;
}
