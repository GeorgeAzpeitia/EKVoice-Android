package datacare.ekvoice;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by kchinnap on 3/1/2016.
 */
public class MenuActivity extends Activity {

    Button button;
    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        //appointment_event();


    }

    public void appointment_event(View view) {

        final Context context = this;
        Intent intent = new Intent(context, AppointmentActivity.class);
        startActivity(intent);

    }

    public void cases_event(View view) {

        final Context context = this;
        button = (Button) findViewById(R.id.button3);
        Intent intent = new Intent(context, CaseList.class);
        startActivity(intent);


    }

}