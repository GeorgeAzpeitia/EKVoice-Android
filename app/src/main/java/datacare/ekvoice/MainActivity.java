package datacare.ekvoice;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

import java.io.File;
import java.io.IOException;
import java.lang.Override;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

public class MainActivity extends AppCompatActivity {

    private Button startSpeech, switchToSphinx;
    private EditText speechOutput;
    private SpeechRecognizer recognizer;
    private final Activity mainHandle = this;
    private SpeechWrapper onlineSpeech = new SpeechWrapper(mainHandle);
    private String holder = "Loading Offline Mode...";
    private TextView loadingMessage;

    @Override
    public void onCreate(final Bundle state) {
        //standard startup tasks
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        //initialize view references
        speechOutput = (EditText) findViewById(R.id.textOutput);
        startSpeech = (Button) findViewById(R.id.listenButton);
        switchToSphinx = (Button) findViewById(R.id.sphinxButton);
        loadingMessage = (TextView) findViewById(R.id.sphinxLoadingMessage);


        holder = "Loading Offline Mode...";
        loadingMessage.setText(holder);
        switchToSphinx.setEnabled(false);

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
//                while (holder.equals("Loading Offline Mode...")) {
//                    holder = onlineSpeech.getLoadingMessage();
//                }
                return null;
            }

            protected void onPostExecute(Exception result) {
                if (result != null){
                    loadingMessage.setText("Failed");
                } else {
                    holder = "Ready";
                    loadingMessage.setText(holder);
                    switchToSphinx.setEnabled(true);
                }
            }
        }.execute();
    }

    //This needs to be part of main in order for the speech function to work. Which makes sense,
    //the SpeechWrapper will get the speech and spit out text, it's up to the implementation to
    //decide what to do with it.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        startSpeech.setEnabled(true);
        super.onActivityResult(requestCode, resultCode, data);
        TextView note = (TextView) findViewById(R.id.textOutput);
        if( data != null){
            if(requestCode == 100){
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                note.setText(results.get(0));
            }else{
                String sphinxResults = data.getStringExtra("EXTRA_SPHINX");
                note.setText(sphinxResults);
            }
        }
    }

    public void onlineSpeechRequest(View view){
        onlineSpeech.promptOnlineSpeechInput(mainHandle);
    }

    public void onSphinxRequest(View view){
        if(onlineSpeech.sphinxReady) {
            Intent intent = new Intent(this, SphinxWrapper.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
