package datacare.ekvoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SphinxActivity extends AppCompatActivity {

    private Button recordButton;
    private TextView textOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphinx);
    }

    public void switchBack(View v){
        finish();
    }


}
