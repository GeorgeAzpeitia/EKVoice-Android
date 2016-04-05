package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import datacare.ekvoice.util.JSONToCase;
import java.util.ArrayList;

public class CaseList extends Activity {
    //private CustomAdapter ca;
    //private CaseAdapter ia;
    Intent intent = getIntent();
    private ListView listView;
    private ArrayList<Case> caseNames;
    CaseAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        try{
            caseNames = JSONToCase.readJsonStream(null);
        } catch (java.io.IOException e){

        }
        itemsAdapter = new CaseAdapter(this, caseNames);
//        Case test = new Case();
//        test.firstName = "Hillary";
//        test.lastName = "Adams";
//        test.claimNumber = "XY214z";
//        test.phoneNumber = "(512)401-5613";
//        Contact company = new Contact();
//        company.name = "FedEx Ground";
//        test.employer = company;
//        itemsAdapter.add(test);
//
//        Case test2 = new Case();
//        test2.firstName = "Donald";
//        test2.lastName = "Sloan";
//        test2.claimNumber = "0zDl34";
//        test2.phoneNumber = "(938)444-3434";
//        Contact company2 = new Contact();
//        company2.name="A&M Construction";
//        test2.employer = company2;
//        itemsAdapter.add(test2);
//
//        Case test3 = new Case();
//        test3.firstName = "Bernard";
//        test3.lastName = "Stan";
//        test3.claimNumber = "3k4l34";
//        test3.phoneNumber = "(408)144-3894";
//        Contact company3 = new Contact();
//        company3.name="Coal Union";
//        test3.employer = company3;
//        itemsAdapter.add(test3);

        //ca = new CustomAdapter(this);
        //ia = new CaseAdapter(this);
        listView = (ListView) findViewById(R.id.listV);
        listView.setAdapter(itemsAdapter);
        //itemsAdapter.loadObjects();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Case toExpand = itemsAdapter.getItem(position);

                Intent expandEvent = new Intent(CaseList.this, ExpandedCase.class);
                expandEvent.putExtra("caseToExpand", toExpand);
                startActivityForResult(expandEvent,1);
            }
        });
    }

}