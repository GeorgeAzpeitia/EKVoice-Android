package datacare.ekvoice;

import java.io.Serializable;

/**
 * Created by Robin on 3/5/2016.
 */
public class Contact implements Serializable{
    public String name;
    public String phoneNumber;
    public String fax;
    public String company;
    public String address1;
    public String address2;
    public String city;
    public String state;
    public String zip;
    public String email;
    public boolean sendEmailOk;
    public boolean sendFaxOk;
    public boolean sendMailOk;
    public String history;
    public String notes;
}
