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

import datacare.ekvoice.Case;

public class JSONToCase {
    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        }
        finally {
            reader.close();
        }
    }

    public List readMessagesArray(JsonReader reader) throws IOException {
        List messages = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public Message readMessage(JsonReader reader) throws IOException {
        long id = -1;
        String text = null;
        Case aCase = new Case();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name){
                case "claim_no":
                    break;
                case "last_name":
                    break;
                case "phone":
                    break;
                case "addr1":
                    break;
                case "addr2":
                    break;
                case "city":
                    break;
                case "state":
                    break;
                case "zip":
                    break;
                case "carrier":
                    break;
                case "primary_md_name":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_addr1":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;
                case "primary_md_fax":
                    break;


            }
        }
        reader.endObject();
        return new Message(id, text, user, geo);
    }

    public List readDoublesArray(JsonReader reader) throws IOException {
        List doubles = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
        String username = null;
        int followersCount = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }
}
