package datacare.ekvoice;

/**
 * Created by Lou on 1/31/2016.
 */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Locale;
import java.util.ArrayList;
import java.lang.Override;

//I'm guessing this is where we'll have online recognition -Lou
public class SpeechWrapper extends AppCompatActivity{
    private TextView note;
    private Button recordButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    public void promptOnlineSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(), "Couldn't record", Toast.LENGTH_SHORT);
        }
    }

    public TextView getNote(){
        return note;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == REQ_CODE_SPEECH_INPUT && data != null){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            note.setText(results.get(0));
        }

    }

}
