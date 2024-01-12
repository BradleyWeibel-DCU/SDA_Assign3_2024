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

        // Get prices from strings
        String thirteen = getString(R.string.thirteen_dollars);
        String fifteen = getString(R.string.fifteen_dollars);
        String seventeen = getString(R.string.seventeen_dollars);
        String eighteen = getString(R.string.eighteen_dollars);

        // Create an ArrayList of shirt type objects
        shirtType.add(new ShirtAdapter(getString(R.string.tank_top), thirteen, R.drawable.tank_top));
        shirtType.add(new ShirtAdapter(getString(R.string.classic), fifteen, R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.v_neck), fifteen, R.drawable.v_neck));
        shirtType.add(new ShirtAdapter(getString(R.string.slim_fit), fifteen, R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.tall), seventeen, R.drawable.long_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.collared), seventeen, R.drawable.collared_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.long_sleeve), seventeen, R.drawable.long_sleeve));
        shirtType.add(new ShirtAdapter(getString(R.string.premium), eighteen, R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.soft), eighteen, R.drawable.t_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.heavy_duty), eighteen, R.drawable.heavy_shirt));
        shirtType.add(new ShirtAdapter(getString(R.string.workout), eighteen, R.drawable.workout_shirt));

        //start it with the view
        Log.d(TAG, "Starting recycler view");
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_view);
        ShirtViewAdapter recyclerViewAdapter = new ShirtViewAdapter(getContext(), shirtType);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
