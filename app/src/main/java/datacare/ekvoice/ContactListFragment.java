package datacare.ekvoice;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

/**
 * Created by george on 4/11/16.
 */
public class ContactListFragment extends Fragment {
    private ArrayList<Contact> contacts;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        View v = inflater.inflate(R.layout.contacts_list, container, false);


        //get our list view
        ListView cList = (ListView) v.findViewById(R.id.contactsList);

        //create custom adapater that holds elements and their state.
        final ContactListAdapter contactsAdapter = new ContactListAdapter(getActivity(), contacts);

        //set elements to adapter
        cList.setAdapter(contactsAdapter);

        // get our folding cell
        cList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ((FoldingCell) view).toggle(false);
                //contactsAdapter.registerToggle(position);
            }
        });


        /*Button addNote = (Button) v.findViewById(R.id.button14);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent note = new Intent(getActivity(), NoteActivity.class);
                startActivity(note);
            }
        }); */
        return v;
    }

    public static Fragment newInstance(ArrayList<Contact> contactsParam){
        ContactListFragment frag = new ContactListFragment();
        if (contactsParam != null && contactsParam.size() != 0) {
            frag.contacts = contactsParam;
        }
        return frag;
    }

    private static class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter(Context context, ArrayList<Contact> users) {
            super(context, 0, users);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Contact aContact = getItem(position);
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell, parent, false);
            }

            TextView contactName = (TextView) convertView.findViewById(R.id.contactName);
            TextView contactPosition = (TextView) convertView.findViewById(R.id.contactPosition);

            contactName.setText(aContact.name);
            contactPosition.setText(aContact.position);
            return convertView;
        }

    }
}
