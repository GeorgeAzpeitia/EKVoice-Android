package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by kchinnap on 3/6/2016.
 */
public class SettingsActivity extends Activity {

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

    }
}
