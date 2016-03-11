package datacare.ekvoice;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Robin on 3/5/2016.
 */
public class Case implements Serializable{
    public Case() {
        notes = new Vector<Note>();
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
    public Contact MD = new Contact();
    public Contact manager = new Contact();
    public Contact carrier_contact = new Contact();
    public Contact employer = new Contact();
    public Contact attorney = new Contact();
    public Contact serviceProvider = new Contact();
    public Vector<Note> notes;
    public static class Note{
        public String user;
        public String date;
        public String noteText;
    }
}
