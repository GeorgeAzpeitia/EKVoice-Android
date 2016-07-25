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

/**
 * This is the activity called when creating some sort of note it has three main ways of being called.
 * As a blank empty note, editing an existing note, or a new note with a contact already selected.
 */
public class NoteActivity extends Activity {

    private ToggleButton timerButton;
    private Button stopButton;

    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private Case storedCase;
    private Case.Note note;
    private Contact contact;
    private Boolean emptyNoteWithContact = false;
    private Boolean editingNote = false;
    private EditText editBox;
    private TextView addContactLabel;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactEmail;
    private TextView callLabel;
    private ImageButton callButton;
    private Button saveButton;
    long timeInSeconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        timerValue = (TextView) findViewById(R.id.timerValue);
        timerButton = (ToggleButton) findViewById(R.id.timerButton);
        addContactLabel = (TextView) findViewById(R.id.addNoteContactLabel);
        contactName = (TextView) findViewById(R.id.addNoteContactName);
        contactPhone = (TextView) findViewById(R.id.addNoteContactPhone);
        contactEmail = (TextView) findViewById(R.id.addNoteContactEmail);
        callButton = (ImageButton) findViewById(R.id.addNoteCallButton);
        saveButton = (Button) findViewById(R.id.addNoteSave);
        editBox = (EditText) findViewById(R.id.addNoteEditText);
        callLabel = (TextView) findViewById(R.id.noteCallLabel);

        contactName.setVisibility(View.INVISIBLE);
        contactPhone.setVisibility(View.INVISIBLE);
        contactEmail.setVisibility(View.INVISIBLE);
        callButton.setVisibility(View.INVISIBLE);
        callLabel.setVisibility(View.INVISIBLE);
        callButton.setEnabled(false);
        Intent i = getIntent();
        storedCase = (Case) i.getSerializableExtra("CASE_EXTRA");

        if(i.hasExtra("selectedNote")){
            editingNote = true;
            note = (Case.Note) i.getSerializableExtra("selectedNote");
            populateFromNote();
        }else if (i.hasExtra("selectedContact")){
            emptyNoteWithContact = true;
            contact = (Contact) i.getSerializableExtra("selectedContact");
            populateFromContact();
        }
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
        //Save the note
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                if(editBox.getText().toString().isEmpty()){
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }else if(editingNote){
                    storedCase.notes.remove(note);
                    note.noteText = editBox.getText().toString();
                    if(contact != note.contact){
                        note.contact = contact;
                    }
                    storedCase.notes.add(note);
                    returnIntent.putExtra("editedCase", storedCase);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else{
                    note = new Case.Note();
                    note.user = storedCase.username;
                    note.noteText = editBox.getText().toString();
                    if(contact != null){
                        note.contact = contact;
                    }
                    storedCase.notes.add(note);
                    returnIntent.putExtra("editedCase", storedCase);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });


    }

    private void populateFromNote(){
        editBox.setText(note.noteText);
        timerValue.setText(note.hours + ":" + note.minutes + ":"
                + String.format("%02d", note.seconds));
        if(note.contact != null){
            contact = note.contact;
            populateFromContact();
        }


    }
    private void populateFromContact(){
        addContactLabel.setText("Change Contact");
        if(contact.name != null){
            contactName.setText(contact.name);
            contactName.setVisibility(View.VISIBLE);
        }
        if(contact.email != null){
            contactEmail.setText(contact.email);
            contactEmail.setVisibility(View.VISIBLE);
        }
        if(contact.phoneNumber != null){
            contactPhone.setText(contact.phoneNumber);
            contactPhone.setVisibility(View.VISIBLE);
            //Needs to be implemented
//            callButton.setEnabled(true);
//            callButton.setVisibility(View.VISIBLE);
        }

    }
    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            //TODO Notes need to use GregorianCalendar to keep track of time
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
