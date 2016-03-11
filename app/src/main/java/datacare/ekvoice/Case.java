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
    public String address;
    public String phoneNumber;
    public Contact MD;
    public Contact manager;
    public Contact carrier;
    public Contact employer;
    public Contact attorney;
    public Contact serviceProvider;
    public Vector<Note> notes;
    public static class Note{
        public String user;
        public String date;
        public String noteText;
    }
}
