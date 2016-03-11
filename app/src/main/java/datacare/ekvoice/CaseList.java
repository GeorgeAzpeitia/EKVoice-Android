package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        test.claimNumber = "XY214z";
        test.phoneNumber = "(512)401-5613";
        Contact company = new Contact();
        company.name = "Democrat";
        test.employer = company;

        Case test2 = new Case();
        test2.firstName = "Donald";
        test2.lastName = "Trump";
        test2.claimNumber = "3zDl34";
        test2.phoneNumber = "(408)444-3434";
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