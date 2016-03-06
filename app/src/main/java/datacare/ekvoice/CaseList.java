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
    private ArrayList<Case> caseNames = new ArrayList<>();
    ArrayAdapter<Case> itemsAdapter =
            new ArrayAdapter<Case>(this, android.R.layout.simple_list_item_1, caseNames);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        Case test = new Case();
        test.firstName = "Bernard";
        test.lastName = "Sanders";
        Contact company = new Contact();
        company.name = "Independent";
        test.employer = company;
        caseNames.add(test);

        //ca = new CustomAdapter(this);
        //ia = new CaseAdapter(this);

        listView = (ListView) findViewById(R.id.listV);
        //listView.setAdapter(ia);
        //ia.loadObjects();
    }

}