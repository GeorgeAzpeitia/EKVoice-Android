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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;


public class MainActivity extends AppCompatActivity implements
        RecognitionListener {

    private Button startSpeech;
    private TextView speechOutput;
    private SpeechRecognizer recognizer;
    private Activity mainHandle = this;
    private SpeechWrapper onlineSpeech = new SpeechWrapper();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            speechOutput.setBackgroundColor(30);

        }
    };

    @Override
    public void onCreate(final Bundle state) {
        //standard startup tasks
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        //initialize view reference
        speechOutput = ((TextView) findViewById(R.id.textOutput));
        speechOutput.setText("Preparing the recognizer");
        startSpeech = (Button) findViewById(R.id.listenButton);

        //set the button to listen if pressed
        startSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        long interval = System.currentTimeMillis() + 1;
                        boolean loop = true;
                        for(;loop;) {
                            while (System.currentTimeMillis() < interval) {
                                synchronized (this) {
                                    try {
                                        wait(interval - System.currentTimeMillis());
                                        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                        if(cm.getActiveNetworkInfo() == null) {
                                            handler.sendEmptyMessage(0);
                                            loop = false;
                                        }
                                    } catch (Exception e) {}
                                }
                            }

                        }
                    }
                };

                Thread checkNetworkLoss = new Thread(r);
                checkNetworkLoss.start();
                startSpeech.setEnabled(false);
                onlineSpeech.promptOnlineSpeechInput(mainHandle);
            }
        });

        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(MainActivity.this);
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
                    speechOutput.setText("Failed to init recognizer " + result);
                } else {
                    speechOutput.setText("Done");
                    //this starts pocketsphinx listening
                    //switchSearch("wakeup");
                }
            }
        }.execute();

    }

    //This needs to be part of main in order for the speech function to work. Which makes sense, the SpeechWrapper
    //will get the speech and spit out text, it's up to the implementation to decide what to do with it.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        startSpeech.setEnabled(true);
        super.onActivityResult(requestCode, resultCode, data);
        TextView note = (TextView) findViewById(R.id.textOutput);

        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        note.setText(results.get(0));
        if (data != null){
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();

        //speechOutput.setText(text);
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {

        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            speechOutput.setText(text);
            //makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {

    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals("wakeup"))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 10000);

    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                        // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                //.setRawLogDir(assetsDir)

                        // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-45f)

                        // Use context-independent phonetic search, context-dependent is too slow for mobile
                //.setBoolean("-allphone_ci", true)

                .getRecognizer();
        recognizer.addListener(this);

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
        recognizer.addNgramSearch("wakeup", languageModel);

        // Phonetic search
        //File phoneticModel = new File(assetsDir, "en-phone.dmp");
        //recognizer.addAllphoneSearch("wakeup", phoneticModel);
    }

    @Override
    public void onError(Exception error) {
        speechOutput.setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch("wakeup");
    }
}
