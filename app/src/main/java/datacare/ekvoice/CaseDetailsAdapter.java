package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by george on 4/11/16.
 * After selecting a case in CaseList.java the user will be sent to this activity which serves to hold
 * the swipe pager for the contact list and the note history.
 */
public class CaseDetailsAdapter extends FragmentActivity {
    static final int NUM_PAGES = 2;
    static final int EMPTY_NOTE = 1;
    ViewPager pager;
    MyAdapter adapter;
    private static Case myCase;
    private static ArrayList<Contact> contacts;
    private Button addNote;
    private PagerTabStrip tabs;

    @Override
    protected void onCreate(final Bundle state){
        super.onCreate(state);
        myCase = (Case)getIntent().getSerializableExtra("caseToExpand");
        setContentView(R.layout.case_swipe_group);
        final TextView tabTitle = (TextView) findViewById(R.id.tab_title);
        tabTitle.setText(myCase.lastName);
        tabs = (PagerTabStrip) findViewById(R.id.caseTabStrip);
        tabs.setDrawFullUnderline(false);
        addNote = (Button) findViewById(R.id.addNoteBtn);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CaseDetailsAdapter.this, NoteActivity.class);
                i.putExtra("CASE_EXTRA", myCase);
                startActivityForResult(i, EMPTY_NOTE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        makeContactList(myCase);
        adapter = new MyAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            myCase = (Case) data.getSerializableExtra("editedCase");
        }
    }

    //Tnis method will check to see if each contact has been populated by the JSON parser if the
    //contact is populated it will add it to the list of contacts to display.
    private void makeContactList(Case myCase){
        contacts = new ArrayList<>();
        //Populate main client contact
        Contact mainClient = new Contact();
        mainClient.name = myCase.firstName == null ? myCase.lastName : myCase.lastName + ", " + myCase.firstName;
        mainClient.position = "Main Client";
        mainClient.email = myCase.email;
        mainClient.phoneNumber = myCase.phoneNumber;
        mainClient.address1 = myCase.address1;
        mainClient.address2 = myCase.address2;
        mainClient.city = myCase.city;
        mainClient.state = myCase.state;
        mainClient.zip = myCase.zip;
        contacts.add(mainClient);

        //populate and add the rest of associated contacts if they exist
        if(myCase.MD != null){
            myCase.MD.position = "Primary Physician";
            contacts.add(myCase.MD);
        }
        if(myCase.manager != null){
            myCase.manager.position = "Case Manager";
            contacts.add(myCase.manager);
        }
        if(myCase.carrier_contact != null){
            myCase.carrier_contact.position = "Carrier Contact";
            contacts.add(myCase.carrier_contact);
        }
        if(myCase.employer != null){
            myCase.employer.position = "Employer";
            contacts.add(myCase.employer);
        }
        if(myCase.attorney != null){
            myCase.attorney.position = "Primary Attorney";
            contacts.add(myCase.attorney);
        }
        if(myCase.serviceProvider != null){
            myCase.serviceProvider.position = "Service Provider";
            contacts.add(myCase.serviceProvider);
        }
    }
    //This adapter will handle the contactlist and noteshistory fragments for the swipe pager. This
    //is mostly implemented inside the getItem method. Note the the information passed into the
    // fragments through the constructors.
    public class MyAdapter extends FragmentStatePagerAdapter {
        private String tabTitles[] = new String[] { "Contacts", "Notes"};
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        private Fragment[] fragments = new Fragment[NUM_PAGES];
        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(fragments[position] == null){
                        fragments[position] = ContactListFragment.newInstance(contacts, myCase);
                        return fragments[position];
                    }

                case 1:
                    if(fragments[position] == null){
                        fragments[position] = NotesHistoryFragment.newInstance(myCase.notes, myCase);
                        return fragments[position];
                    }

            }
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }
    }
}
