package datacare.ekvoice;

/**
 * Created by Lou on 1/31/2016.
 */

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import edu.cmu.pocketsphinx.Assets;


public class SpeechWrapper{
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private SphinxWrapper sphinx = new SphinxWrapper();
    private Activity mainHandle = null;



    public SpeechWrapper(final Activity main){

        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        mainHandle = main;
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(main);
                    File assetDir = assets.syncAssets();
                    sphinx.setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    //speechOutput.setText("Failed to init recognizer " + result);
                } else {
                    //speechOutput.setText("Done");
                    //this starts pocketsphinx listening
                    //switchSearch("wakeup");
                }
            }
        }.execute();

    }
    //This currently checks internet connectivity through the wifi and the phone antenna
    //It only checks to see if it has a connection not if there is a working path to the internet

    public static boolean isInternetConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //This will take in the calling method as an activity reference it then calls the speech activity using the parent activity
    public void promptOnlineSpeechInput(Activity loader){

        if(isInternetConnected(loader.getApplicationContext())){

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            try{
                loader.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {

                Toast.makeText(loader.getApplicationContext(), "Couldnt record", Toast.LENGTH_SHORT).show();
            }

        }else {
            sphinx.startListening();
            Toast.makeText(loader.getApplicationContext(), "No Internet, Sphinx is listening.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sphinxDestroy(){
        sphinx.sphinxDestroy();

    }

}
