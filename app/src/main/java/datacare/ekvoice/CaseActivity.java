package datacare.ekvoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kchinnap on 3/3/2016.
 */
public class CaseActivity extends Activity {

    Button button;

    @Override
    public void onCreate(final Bundle state) {
        super.onCreate(state);
        setTheme(R.style.AppTheme_Login);
        setContentView(R.layout.login_layout);
        cases_event();
    }

    public void cases_event() {
        final Context context = this;
        button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, AppointmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
