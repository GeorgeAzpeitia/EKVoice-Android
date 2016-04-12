package datacare.ekvoice;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by george on 4/11/16.
 */
public class ContactListFragment extends Fragment {
    int mNum;
    static final String[] testData = {"one", "two", "three", "four", "five"};

    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        View v = inflater.inflate(R.layout.contacts_list, container, false);
        return v;
    }
}
