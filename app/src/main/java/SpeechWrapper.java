/**
 * Created by george on 1/30/16.
 */

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.java.util.ArrayList;

import java.lang.Override;

//I'm guessing this is where we'll have online recognition -Lou
public class SpeechWrapper {
    private String note = "";
    private Button recordButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    note.setText(results.get(0));
                }
                break;
            }
        }

    }

}
