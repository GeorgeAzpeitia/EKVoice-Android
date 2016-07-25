package datacare.ekvoice;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by george on 4/11/16.
 * This fragment holds and populates the list of contacts to be displayed inside the CaseDetailsAdapter
 */
public class ContactListFragment extends Fragment {
    private ArrayList<Contact> clientContacts;
    private Case clientCase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        View v = inflater.inflate(R.layout.contacts_list, container, false);

        //get our list view
        ListView cList = (ListView) v.findViewById(R.id.contactsList);

        //create custom adapater that holds elements and their state.
        final ContactListAdapter contactsAdapter = new ContactListAdapter(getActivity(), clientContacts);

        //set elements to adapter
        cList.setAdapter(contactsAdapter);

        // get our folding cell
        cList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ((FoldingCell) view).toggle(false);
            }
        });

        return v;
    }

    public static Fragment newInstance(ArrayList<Contact> contactsParam, Case caseParam){
        ContactListFragment frag = new ContactListFragment();
        if (contactsParam != null && contactsParam.size() != 0) {
            frag.clientContacts = contactsParam;
        }
        frag.clientCase = caseParam;
        return frag;
    }
    //This is the adapter for populating the contacts list.
    private  class ContactListAdapter extends ArrayAdapter<Contact> {
        static final int CONTACT_NOTE = 2;
        public ContactListAdapter(Context context, ArrayList<Contact> users) {
            super(context, 0, users);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Contact aContact = getItem(position);
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell, parent, false);
            }
            Button addNote = (Button) convertView.findViewById(R.id.contactAddNote);
            addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),NoteActivity.class);
                    i.putExtra("SELECTED CONTACT", aContact);
                    i.putExtra("CASE_EXTRA", clientCase);
                    getActivity().startActivityForResult(i, CONTACT_NOTE);
                }
            });
            TextView contactName = (TextView) convertView.findViewById(R.id.contactName);
            TextView contactPosition = (TextView) convertView.findViewById(R.id.contactPosition);
            TextView contactNameExpanded = (TextView) convertView.findViewById(R.id.contactNameExpanded);
            TextView contactPositionExpanded = (TextView) convertView.findViewById(R.id.contactPositionExpanded);
            TextView contactAddress1 = (TextView) convertView.findViewById(R.id.contactAddressLine1);
            TextView contactAddress2 = (TextView) convertView.findViewById(R.id.contactAddressLine2);
            TextView contactAddress3 = (TextView) convertView.findViewById(R.id.contactAddressLine3);
            TextView contactEmail = (TextView) convertView.findViewById(R.id.contactEmail);
            TextView contactPhone = (TextView) convertView.findViewById(R.id.contactPhone);

            contactName.setText(aContact.name);
            contactNameExpanded.setText(aContact.name);

            if(aContact.position != null){
                contactPosition.setText(aContact.position);
                contactPositionExpanded.setText(aContact.position);
            }else{
                contactPositionExpanded.setVisibility(View.GONE);
            }

            if(aContact.address1 != null && aContact.address1.length() > 0){
                contactAddress1.setText(aContact.address1);
                if(aContact.address2 != null && aContact.address2.length() > 0){
                    contactAddress2.setText(aContact.address2);
                    contactAddress2.setVisibility(View.VISIBLE);
                }
                contactAddress3.setText( aContact.city + ", " + aContact.state + ", " + aContact.zip);
            }else {
                contactAddress1.setText("No Address");
                contactAddress3.setText("Provided");
            }

            if(aContact.email != null){
                contactEmail.setText(aContact.email);
            }else {
                contactEmail.setText("No Email Provided");
            }
            if(aContact.phoneNumber != null){
                contactPhone.setText(PhoneNumberUtils.formatNumber(aContact.phoneNumber));
            }else {
                contactPhone.setText("No Phone Number Provided");
            }

            return convertView;
        }

    }
}
