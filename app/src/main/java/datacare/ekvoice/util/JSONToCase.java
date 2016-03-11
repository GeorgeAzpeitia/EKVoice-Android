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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import datacare.ekvoice.Case;

public class JSONToCase {
    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readCaseArray(reader);
        }
        finally {
            reader.close();
        }
    }

    public Vector<Case> readCaseArray(JsonReader reader) throws IOException {
        Vector<Case> cases = new Vector<>();

        reader.beginArray();
        while (reader.hasNext()) {
            cases.add(readMessage(reader));
        }
        reader.endArray();
        return cases;
    }

    public Case readMessage(JsonReader reader) throws IOException {
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
                    aCase.carrier_contact.company = reader.nextString();
                    break;
                case "carrier_contact_name":
                    aCase.carrier_contact.name = reader.nextString();
                    break;
                case "carrier_contact_phone":
                    aCase.carrier_contact.phoneNumber = reader.nextString();
                    break;
                case "carrier_contact_email":
                    aCase.carrier_contact.email = reader.nextString();
                    break;
                case "primary_md_name":
                    aCase.MD.name = reader.nextString();
                    break;
                case "primary_md_phone":
                    aCase.MD.phoneNumber = reader.nextString();
                    break;
                case "primary_md_fax":
                    aCase.MD.fax = reader.nextString();
                    break;
                case "primary_md_addr1":
                    aCase.MD.address1 = reader.nextString();
                    break;
                case "primary_md_addr2":
                    aCase.MD.address2 = reader.nextString();
                    break;
                case "primary_md_city":
                    aCase.MD.city = reader.nextString();
                    break;
                case "primary_md_state":
                    aCase.MD.state = reader.nextString();
                    break;
                case "primary_md_zip":
                    aCase.MD.zip = reader.nextString();
                    break;
                case "attorney_name":
                    aCase.attorney.name = reader.nextString();
                    break;
                case "attorney_phone":
                    aCase.attorney.phoneNumber = reader.nextString();
                    break;
                case "attorney_addr1":
                    aCase.attorney.address1 = reader.nextString();
                    break;
                case "attorney_addr2":
                    aCase.attorney.address2 = reader.nextString();
                    break;
                case "attorney_city":
                    aCase.attorney.city = reader.nextString();
                    break;
                case "attorney_state":
                    aCase.attorney.state = reader.nextString();
                    break;
                case "attorney_zip":
                    aCase.attorney.zip = reader.nextString();
                    break;
                case "attorney_email":
                    aCase.attorney.email = reader.nextString();
                    break;
                case "notes":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Case.Note note = new Case.Note();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            name = reader.nextName();
                            if (name == "user") {
                                note.user = reader.nextString();
                            } else if (name == "date") {
                                note.date = reader.nextString();
                            } else if (name == "notetext") {
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
