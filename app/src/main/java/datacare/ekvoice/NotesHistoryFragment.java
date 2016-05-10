package datacare.ekvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by george on 4/19/16.
 */
public class NotesHistoryFragment extends Fragment{
    private ArrayList<Case.Note> notes;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        View v = inflater.inflate(R.layout.notes_history, container, false);
        NoteListAdapter notesAdapter = new NoteListAdapter(getActivity(), notes);
        ListView cList = (ListView) v.findViewById(R.id.notesList);
        cList.setAdapter(notesAdapter);

//        Button addNote = (Button) v.findViewById(R.id.button9);
//        addNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent note = new Intent(getActivity(), NoteActivity.class);
//                startActivity(note);
//            }
//        });
        return v;
    }
    public static Fragment newInstance(ArrayList<Case.Note> notesParam){
        NotesHistoryFragment frag = new NotesHistoryFragment();
        if (notesParam != null && notesParam.size() != 0) {
            Collections.reverse(notesParam);
            frag.notes = notesParam;
        }
        return frag;
    }


    private static class NoteListAdapter extends ArrayAdapter<Case.Note> {

        public NoteListAdapter(Context context, ArrayList<Case.Note> users) {
            super(context, 0, users);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Case.Note aNote = getItem(position);
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_list_item, parent, false);
            }
            TextView noteContent = (TextView) convertView.findViewById(R.id.noteContent);
            TextView noteUserName = (TextView) convertView.findViewById(R.id.noteUserName);

            noteContent.setText(aNote.noteText);
            noteUserName.setText(aNote.user);
            return convertView;
        }

    }

}

