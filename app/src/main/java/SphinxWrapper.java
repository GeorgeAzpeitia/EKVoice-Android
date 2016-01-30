/**
 * Created by george on 1/30/16.
 */
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

public class SphinxWrapper implements RecognitionListener {
    @Override
    public void onBeginningOfSpeech(){
    }

    @Override
    public void onEndOfSpeech(){

    }

    @Override
    public void onResult(Hypothesis hypothesis){

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis){

    }

    @Override
    public void onTimeout(){

    }

    @Override
    public void onError(Exception e){

    }
}
