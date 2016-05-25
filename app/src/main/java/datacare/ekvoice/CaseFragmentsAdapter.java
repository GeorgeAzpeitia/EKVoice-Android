package datacare.ekvoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import datacare.ekvoice.R;

/**
 * Created by george on 4/11/16.
 */
public class CaseFragmentsAdapter extends FragmentActivity {
    static final int NUM_PAGES = 2;
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
        makeContactList(myCase);
        setContentView(R.layout.case_swipe_group);
        final TextView tabTitle = (TextView) findViewById(R.id.tab_title);
        tabTitle.setText(myCase.lastName);
        adapter = new MyAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (PagerTabStrip) findViewById(R.id.caseTabStrip);
        tabs.setDrawFullUnderline(false);
        addNote = (Button) findViewById(R.id.addNoteBtn);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CaseFragmentsAdapter.this, NoteActivity.class);
                i.putExtra("CASE_EXTRA", myCase);
                startActivity(i);
            }
        });

    }

    private void makeContactList(Case myCase){
        contacts = new ArrayList<>();
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

    public class MyAdapter extends FragmentPagerAdapter {
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
                        fragments[position] = ContactListFragment.newInstance(contacts);
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
