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
 *
 */

public class ProductList extends Fragment {

    private static final String TAG = "RecyclerViewActivity";
    private ArrayList<ShirtAdapter> shirtType = new ArrayList<>();

    // Required empty public constructor
    public ProductList() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Create an ArrayList of shirt type objects
        shirtType.add(new ShirtAdapter("Tank Top", "$13", R.drawable.tank_top));
        shirtType.add(new ShirtAdapter("Classic T-Shirt", "$15", R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter("V-Neck T-Shirt", "$15", R.drawable.v_neck));
        shirtType.add(new ShirtAdapter("Slim Fit T-Shirt", "$15", R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter("Tall T-Shirt", "$17", R.drawable.long_shirt));
        shirtType.add(new ShirtAdapter("Collared Shirt", "$17", R.drawable.collared_shirt));
        shirtType.add(new ShirtAdapter("Long Sleeve T-Shirt", "$17", R.drawable.long_sleeve));
        shirtType.add(new ShirtAdapter("Premium Q T-Shirt", "$18", R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter("X-Soft T-Shirt", "$18", R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter("Heavy Duty T-Shirt", "$18", R.drawable.heavy_shirt));
        shirtType.add(new ShirtAdapter("Workout T-Shirt", "$18", R.drawable.workout_shirt));

        //start it with the view
        Log.d(TAG, "Starting recycler view");
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_view);
        ShirtViewAdapter recyclerViewAdapter = new ShirtViewAdapter(getContext(), shirtType);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
