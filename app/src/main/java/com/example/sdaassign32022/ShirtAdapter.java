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

/**
 * {@link ShirtAdapter} represents a single t-shirt type.
 * Each object has 3 properties: type, price, and image resource ID.
 * This is a basic arrayAdapter
 */

public class ShirtAdapter {

    // T-Shirt type (e.g. classic, tank top, premium, etc)
    private String mShirtName;

    // T-Shirt price (e.g. 13, 15, 17, 18)
    private String mShirtPrice;

    // Drawable resource ID
    private int mImageResourceId;

    /*
     * Create a new ShirtAdapter object.
     * @param vShirtType is the name of the t-shirt type (e.g. classic)
     * @param vShirtPrice is the corresponding t-shirt price (e.g. 15)
     * @param image is drawable reference ID that corresponds to the t-shirt
     */

    public ShirtAdapter(String vShirtType, String vShirtPrice, int imageResourceId)
    {
        mShirtName = vShirtType;
        mShirtPrice = vShirtPrice;
        mImageResourceId = imageResourceId;
    }

    // Get the name of the t-shirt
    public String getShirtName() {
        return mShirtName;
    }

    // Get the t-shirt price
    public String getShirtPrice() {
        return mShirtPrice;
    }

    // Get the image resource ID
    public int getImageResourceId() {
        return mImageResourceId;
    }
}

