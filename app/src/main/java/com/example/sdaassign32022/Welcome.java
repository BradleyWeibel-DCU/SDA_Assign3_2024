package com.example.sdaassign32022;

import android.os.Bundle;
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

    // Construct UI fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
}
