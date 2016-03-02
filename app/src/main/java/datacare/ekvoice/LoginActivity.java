package datacare.ekvoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;

/**
 * Created by kchinnap on 3/1/2016.
 */
public class LoginActivity extends Activity{

    Button button;

    @Override
    public void onCreate(final Bundle state) {
        super.onCreate(state);
        setTheme(R.style.AppTheme_Login);
        setContentView(R.layout.login_layout);
        login_event();
    }

    public void login_event() {

        final Context context = this;

        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
