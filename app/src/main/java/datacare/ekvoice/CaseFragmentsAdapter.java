package datacare.ekvoice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(final Bundle state){
        super.onCreate(state);
        myCase = (Case)getIntent().getSerializableExtra("caseToExpand");
        makeContactList(myCase);
        setContentView(R.layout.case_swipe_group);


        adapter = new MyAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

    }

    private void makeContactList(Case myCase){
        contacts = new ArrayList<>();
        if(myCase.MD != null){
            contacts.add(myCase.MD);
        }
        if(myCase.manager != null){
            contacts.add(myCase.manager);
        }
        if(myCase.carrier_contact != null){
            contacts.add(myCase.carrier_contact);
        }
        if(myCase.employer != null){
            contacts.add(myCase.employer);
        }
        if(myCase.attorney != null){
            contacts.add(myCase.attorney);
        }
        if(myCase.serviceProvider != null){
            contacts.add(myCase.serviceProvider);
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
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
                        fragments[position] = NotesHistoryFragment.newInstance(myCase.notes);
                        return fragments[position];
                    }
            }
            return null;
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;
        static final String[] testData = {"one", "two", "three", "four", "five"};

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.test2, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("Fragment #" + mNum);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, testData));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }
}
