package datacare.ekvoice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ExpandedCase extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_case);

        Case myCase = (Case)getIntent().getSerializableExtra("caseToExpand");
        TextView tView = (TextView) findViewById(R.id.nameText);
        tView.setText(myCase.lastName+", "+myCase.firstName);
        tView = (TextView) findViewById(R.id.employerText);
        tView.setText(myCase.employer.name);
        tView = (TextView) findViewById(R.id.phoneText);
        tView.setText(myCase.phoneNumber);
        tView = (TextView) findViewById(R.id.claimText);
        tView.setText(myCase.claimNumber);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
