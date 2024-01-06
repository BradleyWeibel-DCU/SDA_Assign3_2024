package com.example.sdaassign32022;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

/*
 * A simple {@link Fragment} subclass.
 *
 */

public class Welcome extends Fragment {

    // Required empty public constructor
    public Welcome() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_welcome, container, false);

        Log.d("Initial Task 1 error", "We arrived here and are returning the Welcome page-----------------------------------------------");

        return root;
    }
}
