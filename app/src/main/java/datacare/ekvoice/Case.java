package datacare.ekvoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Robin on 3/5/2016.
 */
public class Case implements Serializable{
    public Case() {
        notes = new ArrayList<Note>();
    }
    public String lastName;
    public String firstName;
    public String claimNumber;
    public String address1;
    public String address2;
    public String city;
    public String state;
    public String zip;
    public String phoneNumber;
    public String email;
    public Contact MD;
    public Contact manager;
    public Contact carrier_contact;
    public Contact employer;
    public Contact attorney;
    public Contact serviceProvider;
    public ArrayList<Note> notes;
    public static class Note implements Serializable{
        public String user;
        public String date;
        public String noteText;
    }
}
