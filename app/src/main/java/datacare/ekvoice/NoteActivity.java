package datacare.ekvoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NoteActivity extends Activity {

    private ToggleButton timerButton;
    private Button stopButton;

    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private Case storedCase;
    private Case.Note storedNote;
    private Boolean editingNote = false;
    long timeInSeconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Intent i = getIntent();
        storedCase =(Case) i.getSerializableExtra("EXTRA_CASE");
        if(i.hasExtra("selectedNote")){
            editingNote = true;
            storedNote = (Case.Note) i.getSerializableExtra("selectedNote");
            populateFromNote();
        }

        timerValue = (TextView) findViewById(R.id.timerValue);
        timerButton = (ToggleButton) findViewById(R.id.timerButton);
        //startButton.setText("Start");
        timerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (stop == false) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    stop = true;
                } else {
                    //startButton.setText("Start");
                    stop = false;
                }
            }
        });
    }

    private void populateFromNote(){
        EditText editBox = (EditText) findViewById(R.id.addNoteEditText);
        editBox.setText(storedNote.noteText);
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInSeconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs));

            customHandler.postDelayed(this, 0);
        }
    };

    public void cancelButton(View v) {
        finish();
    }

}
