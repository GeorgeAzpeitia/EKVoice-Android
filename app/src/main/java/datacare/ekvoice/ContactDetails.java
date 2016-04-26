package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class ContactDetails extends Activity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cell);
        b1 = (Button) findViewById(R.id.button10);
        call();
    }

    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("4088416989"));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "yourActivity is not found", Toast.LENGTH_SHORT).show();
        }
    }
}
