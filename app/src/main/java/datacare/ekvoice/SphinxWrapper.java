package datacare.ekvoice; /**
 * Created by george on 1/30/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class SphinxWrapper extends AppCompatActivity implements RecognitionListener {

    private SpeechRecognizer recognizer;

    public void startListening(){
    }

    @Override
    public void onBeginningOfSpeech(){
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech(){
        recognizer.stop();
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis){

        if(hypothesis != null){
            String out = hypothesis.getHypstr();
            Intent returnSpeech = new Intent();
            returnSpeech.putExtra("EXTRA_SPHINX", out);
            setResult(1, returnSpeech);
            finish();
        }else{
            setResult(-1);
            finish();
        }
    }
    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis){

    }

    @Override
    public void onTimeout(){

    }

    @Override
    public void onError(Exception e){

    }

    public void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                // .setRawLogDir(assetsDir)

                // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-45f)

                // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)
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
        recognizer.addNgramSearch("engl", languageModel);

        // Phonetic search
        //File phoneticModel = new File(assetsDir, "en-phone.dmp");
        //recognizer.addAllphoneSearch("wakeup", phoneticModel);
    }

    public void sphinxDestroy(){
        recognizer.cancel();
        recognizer.shutdown();
    }

    public void switchBack(View v){
        finish();
    }
}
