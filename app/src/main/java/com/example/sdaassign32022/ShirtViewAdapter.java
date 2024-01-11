package com.example.sdaassign32022;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ShirtViewAdapter extends RecyclerView.Adapter<ShirtViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;

    //add array for each item\
    private ArrayList<ShirtAdapter> mShirts;

    ShirtViewAdapter(Context mNewContext, ArrayList<ShirtAdapter> mshirt) {
        this.mNewContext = mNewContext;
        this.mShirts = mshirt;
    }

    //declare methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");

        viewHolder.imageText.setText(mShirts.get(position).getShirtName());
        viewHolder.priceText.setText(mShirts.get(position).getShirtPrice());
        viewHolder.imageItem.setImageResource(mShirts.get(position).getImageResourceId());

        // set listener for when user clicks a shirt entry in the list
        viewHolder.itemParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User clicks a shirt entry in the list
                // Show toastr message to user
                Toast.makeText(mNewContext, viewHolder.imageText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShirts.size();
    }

    // viewholder class
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageItem;
        TextView imageText;
        TextView priceText;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.imageItem);
            imageText = itemView.findViewById(R.id.shirtText);
            priceText = itemView.findViewById(R.id.shirtPrice);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);

        }
    }
}
