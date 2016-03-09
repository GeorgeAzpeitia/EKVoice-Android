package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        caseNames = new ArrayList<Case>();
        itemsAdapter = new CaseAdapter(this, caseNames);
        Case test = new Case();
        test.firstName = "Hillary";
        test.lastName = "Clampton";
        Contact company = new Contact();
        company.name = "Democrat";
        test.employer = company;
        Case test2 = new Case();
        test2.firstName = "Donald";
        test2.lastName = "Trump";
        Contact company2 = new Contact();
        company2.name="Republican";
        test2.employer = company2;
        itemsAdapter.add(test);
        itemsAdapter.add(test2);

        //ca = new CustomAdapter(this);
        //ia = new CaseAdapter(this);

        listView = (ListView) findViewById(R.id.listV);
        listView.setAdapter(itemsAdapter);
        //itemsAdapter.loadObjects();
    }

}