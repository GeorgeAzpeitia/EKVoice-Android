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
import edu.cmu.pocketsphinx.SpeechRecognizer;
import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;


public class SpeechWrapper{
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Activity mainHandle = null;
    public static SpeechRecognizer sphinxRecognizer = null;
    public boolean sphinxReady = false;

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
                    setupRecognizer(assetDir);
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
                    sphinxReady = true;
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
            if(sphinxReady) {
                Intent intent = new Intent(loader, SphinxWrapper.class);
                loader.startActivityForResult(intent, 10);
                Toast.makeText(loader.getApplicationContext(), "No Internet, Sphinx is listening.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        sphinxRecognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                        // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                        // .setRawLogDir(assetsDir)

                        // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-45f)

                        // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)
                .getRecognizer();

        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */


        // The way these calls work is the first variable is just the name of the model you want to activate when
        // you call recognizer.startListening(String name);
        // There's a couple of different models you can call this one is a natural language grammar search, I think
        // it will probably be what we end up using when we actually implement this in the language.

        // Create language model search

        //File languageModel = new File(assetsDir, "weather.dmp");
        //recognizer.addNgramSearch("wakeup", languageModel);

        // Phonetic search
        //File phoneticModel = new File(assetsDir, "en-phone.dmp");
        //recognizer.addAllphoneSearch("wakeup", phoneticModel);

        // Create keyword-activation search.
        //recognizer.addKeyphraseSearch("wakeup", KEYPHRASE);

        // Create grammar-based search for selection between demos
        //File menuGrammar = new File(assetsDir, "menu.gram");
        //recognizer.addGrammarSearch("wakeup", menuGrammar);

        // Create grammar-based search for digit recognition
        //File digitsGrammar = new File(assetsDir, "digits.gram");
        //recognizer.addGrammarSearch("wakeup", digitsGrammar);

        // Create language model search
        File languageModel = new File(assetsDir, "cmusphinx-5.0-en-us.lm.dmp");
        sphinxRecognizer.addNgramSearch("engl", languageModel);

        // Phonetic search
        //File phoneticModel = new File(assetsDir, "en-phone.dmp");
        //recognizer.addAllphoneSearch("wakeup", phoneticModel);
    }

}
