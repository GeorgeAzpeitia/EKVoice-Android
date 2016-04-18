package datacare.ekvoice.util;

/**
 * Created by george on 3/10/16.
 */
import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import datacare.ekvoice.Case;
import datacare.ekvoice.Contact;

public class JSONToCase {
    public static ArrayList<Case> readJsonStream(InputStream in) throws IOException {
        String testJSON = "[{\"claim_no\":\"W1000000\",\"last_name\":\"Smith\",\"phone\":\"5551001212\",\"addr1\":\"123 Main St\",\"addr2\":\"\",\"city\":\"San Clemente\",\"state\":\"CA\",\"zip\":\"10001\",\"carrier\":\"United Corporate Partners\",\"primary_md_name\":\"Fakerson, Falsey\",\"primary_md_fax\":\"5552001010\",\"primary_md_addr1\":\"321 Somewhere Blvd\",\"primary_md_addr2\":\"Suite #202\",\"primary_md_city\":\"San Francisco\",\"primary_md_state\":\"CA\",\"primary_md_zip\":\"99999\",\"carrier_contact_name\":\"Mendacious, Faux\",\"carrier_contact_phone\":\"5557111117\",\"attorney_name\":\"Real, Totally, Esq.\",\"attorney_phone\":\"5553000001\",\"attorney_email\":\"soreal@lawyers.com\",\"notes\":[{\"user\":\"tsmith\",\"date\":\"2015-11-15 11:15:38\",\"notetext\":\"Initial visit to claimant at MD office. He reports severe pain in the lower left leg.\"},{\"user\":\"tsmith\",\"date\":\"2015-11-21 12:49:01\",\"notetext\":\"Called patient to check up. He reports the pain is getting worse.\"},{\"user\":\"tsmith\",\"date\":\"2015-11-30 16:20:00\",\"notetext\":\"After consulting with MD and attorney, recommending amputation.\"}]},{\"claim_no\":\"W1000001\",\"last_name\":\"Chang\",\"phone\":\"5558221111\",\"email\":\"chang@amalcard.net\",\"addr1\":\"456 Auxiliary Ave\",\"addr2\":\"Apt. 509\",\"city\":\"San Diego\",\"state\":\"CA\",\"zip\":\"20002\",\"carrier\":\"Amalgamated Cardboard, LLC\",\"primary_md_name\":\"Testington, Testy\",\"primary_md_phone\":\"5550110101\",\"primary_md_addr1\":\"654 Nowhere Blvd\",\"primary_md_addr2\":\"Suite #202\",\"primary_md_city\":\"San Benito\",\"primary_md_state\":\"CA\",\"primary_md_zip\":\"77777\",\"carrier_contact_name\":\"Liar, Sucha\",\"carrier_contact_phone\":\"5558374656\",\"attorney_name\":\"Notfake, Definitely, Esq.\",\"attorney_phone\":\"5556664444\",\"attorney_addr1\":\"100 Lawyer Lane\",\"attorney_addr2\":\"Room #300\",\"attorney_city\":\"San Simeon\",\"attorney_state\":\"CA\",\"attorney_zip\":\"33333\",\"notes\":[{\"user\":\"dtest\",\"date\":\"2015-12-11 11:15:38\",\"notetext\":\"Everything seems fine. Minor laceration of the pinky toe.\"},{\"user\":\"dtest\",\"date\":\"2015-12-25 12:49:01\",\"notetext\":\"I can't believe this guy had the gall to die on Christmas.\"}]},{\"claim_no\":\"W1000002\",\"last_name\":\"Garcia\",\"phone\":\"5554001818\",\"addr1\":\"789 Tangential Lane\",\"addr2\":\"\",\"city\":\"San Andreas\",\"state\":\"CA\",\"zip\":\"30003\",\"carrier\":\"United Corporate Partners\",\"primary_md_name\":\"Placeholder, Aloysius\",\"primary_md_fax\":\"5552001010\",\"primary_md_addr1\":\"987 Anywhere Place\",\"primary_md_addr2\":\"\",\"primary_md_city\":\"San Anselmo\",\"primary_md_state\":\"CA\",\"primary_md_zip\":\"66666\",\"carrier_contact_name\":\"Figment, Fanciful\",\"carrier_contact_email\":\"ffigment@ucp.org\",\"attorney_name\":\"Verity, Plentiful\",\"attorney_phone\":\"5557770002\",\"notes\":[{\"user\":\"swilliams\",\"date\":\"2015-11-11 08:15:40\",\"notetext\":\"This is way too complicated for me. I'm calling in backup.\"},{\"user\":\"djones\",\"date\":\"2015-12-19 12:49:01\",\"notetext\":\"Alright, I'm going in! Let's do this!\"},{\"user\":\"swilliams\",\"date\":\"2016-02-15 16:20:00\",\"notetext\":\"I guess everything worked out?\"}]}]";
        StringReader stringStream = new StringReader(testJSON);
        JsonReader reader = new JsonReader(stringStream);
        try {
            return readCaseArray(reader);
        }
        finally {
            reader.close();
        }
    }

    public static ArrayList<Case> readCaseArray(JsonReader reader) throws IOException {
        ArrayList<Case> cases = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            cases.add(readMessage(reader));
        }
        reader.endArray();
        return cases;
    }

    public static Case readMessage(JsonReader reader) throws IOException {
        Case aCase = new Case();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name){
                case "claim_no":
                    aCase.claimNumber = reader.nextString();
                    break;
                case "last_name":
                    aCase.lastName = reader.nextString();
                    break;
                case "phone":
                    aCase.phoneNumber = reader.nextString();
                    break;
                case "email":
                    aCase.email = reader.nextString();
                    break;
                case "addr1":
                    aCase.address1 = reader.nextString();
                    break;
                case "addr2":
                    aCase.address2 = reader.nextString();
                    break;
                case "city":
                    aCase.city = reader.nextString();
                    break;
                case "state":
                    aCase.state = reader.nextString();
                    break;
                case "zip":
                    aCase.zip = reader.nextString();
                    break;
                case "carrier":
                    if(aCase.carrier_contact == null) aCase.carrier_contact = new Contact();
                    aCase.carrier_contact.company = reader.nextString();
                    break;
                case "carrier_contact_name":
                    if(aCase.carrier_contact == null) aCase.carrier_contact = new Contact();
                    aCase.carrier_contact.name = reader.nextString();
                    break;
                case "carrier_contact_phone":
                    if(aCase.carrier_contact == null) aCase.carrier_contact = new Contact();
                    aCase.carrier_contact.phoneNumber = reader.nextString();
                    break;
                case "carrier_contact_email":
                    if(aCase.carrier_contact == null) aCase.carrier_contact = new Contact();
                    aCase.carrier_contact.email = reader.nextString();
                    break;
                case "primary_md_name":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.name = reader.nextString();
                    break;
                case "primary_md_phone":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.phoneNumber = reader.nextString();
                    break;
                case "primary_md_fax":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.fax = reader.nextString();
                    break;
                case "primary_md_addr1":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.address1 = reader.nextString();
                    break;
                case "primary_md_addr2":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.address2 = reader.nextString();
                    break;
                case "primary_md_city":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.city = reader.nextString();
                    break;
                case "primary_md_state":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.state = reader.nextString();
                    break;
                case "primary_md_zip":
                    if(aCase.MD == null) aCase.MD = new Contact();
                    aCase.MD.zip = reader.nextString();
                    break;
                case "attorney_name":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.name = reader.nextString();
                    break;
                case "attorney_phone":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.phoneNumber = reader.nextString();
                    break;
                case "attorney_addr1":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.address1 = reader.nextString();
                    break;
                case "attorney_addr2":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.address2 = reader.nextString();
                    break;
                case "attorney_city":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.city = reader.nextString();
                    break;
                case "attorney_state":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.state = reader.nextString();
                    break;
                case "attorney_zip":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.zip = reader.nextString();
                    break;
                case "attorney_email":
                    if(aCase.attorney == null) aCase.attorney = new Contact();
                    aCase.attorney.email = reader.nextString();
                    break;
                case "notes":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Case.Note note = new Case.Note();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            name = reader.nextName();
                            if (name.equals("user")) {
                                note.user = reader.nextString();
                            } else if (name.equals("date")) {
                                note.date = reader.nextString();
                            } else if (name.equals("notetext")) {
                                note.noteText = reader.nextString();
                            }
                        }
                        reader.endObject();
                        aCase.notes.add(note);
                    }
                    reader.endArray();
                    break;
            }
        }
        reader.endObject();
        return aCase;
    }

}
