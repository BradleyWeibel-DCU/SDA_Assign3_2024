package com.example.sdaassign32022;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/*
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan 2019
 */
public class ProductList extends Fragment {

    private static final String TAG = "RecyclerViewActivity";
    private ArrayList<FlavorAdapter> mFlavor = new ArrayList<>();

    public ProductList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Create an ArrayList of AndroidFlavor objects
        mFlavor.add(new FlavorAdapter("Donut", "1.6", R.drawable.donut));
        mFlavor.add(new FlavorAdapter("Eclair", "2.0-2.1", R.drawable.eclair));
        mFlavor.add(new FlavorAdapter("Froyo", "2.2-2.2.3", R.drawable.froyo));
        mFlavor.add(new FlavorAdapter("GingerBread", "2.3-2.3.7", R.drawable.gingerbread));
        mFlavor.add(new FlavorAdapter("Honeycomb", "3.0-3.2.6", R.drawable.honeycomb));
        mFlavor.add(new FlavorAdapter("Ice Cream Sandwich", "4.0-4.0.4", R.drawable.icecream));
        mFlavor.add(new FlavorAdapter("Jelly Bean", "4.1-4.3.1", R.drawable.jellybean));
        mFlavor.add(new FlavorAdapter("KitKat", "4.4-4.4.4", R.drawable.kitkat));
        mFlavor.add(new FlavorAdapter("Lollipop", "5.0-5.1.1", R.drawable.lollipop));
        mFlavor.add(new FlavorAdapter("Marshmallow", "6.0-6.0.1", R.drawable.marshmallow));

        //start it with the view
        Log.d(TAG, "Starting recycler view");
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_view);
        FlavorViewAdapter recyclerViewAdapter = new FlavorViewAdapter(getContext(), mFlavor);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
