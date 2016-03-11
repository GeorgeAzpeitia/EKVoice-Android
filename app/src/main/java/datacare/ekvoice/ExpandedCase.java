package datacare.ekvoice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ExpandedCase extends Activity {

    Case myCase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_case);

        myCase = (Case)getIntent().getSerializableExtra("caseToExpand");
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

    public void onSaveButton(View v) {
        EditText et = (EditText) findViewById(R.id.noteText);
        Case.Note aNote = new Case.Note();
        aNote.noteText = et.getText().toString();
        myCase.notes.add(aNote);
        TextView tView = (TextView) findViewById(R.id.nameText);
        tView.setText(myCase.notes.firstElement().noteText);
    }

    public void onViewAllNotes(View v) {
        
    }
}
