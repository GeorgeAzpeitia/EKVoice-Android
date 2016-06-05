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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by kchinnap on 3/1/2016.
 * A simple login activity,Because we don't have access to any way of actual user authentication we
 * simply have the login button do a login without checking credentials.
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

                Intent intent = new Intent(context, CaseList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
