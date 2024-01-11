package com.example.sdaassign32022;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderTshirt extends Fragment {

    // Required empty public constructor
    public OrderTshirt() { }

    // Class wide variables
    private Spinner mSpinner;
    private TextView mImageTextView;
    private EditText mCustomerName, meditDelivery;
    private ImageView mCameraImage;

    // Static keys
    private static int REQUEST_CODE = 100;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "OrderTshirt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment get the root view.
        final View root = inflater.inflate(R.layout.fragment_order_tshirt, container, false);

        mImageTextView = root.findViewById(R.id.imageText);
        mCustomerName = root.findViewById(R.id.editCustomer);
        meditDelivery = root.findViewById(R.id.editDeliver);
        meditDelivery.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditDelivery.setRawInputType(InputType.TYPE_CLASS_TEXT);

        mCameraImage = root.findViewById(R.id.imageView);
        Button mSendButton = root.findViewById(R.id.sendButton);

        //set a listener on the the camera image
        mCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);
            }
        });

        //set a listener to start the email intent.
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });

        //initialise spinner using the integer array
        mSpinner = root.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_time_entries, R.layout.spinner_days);
        mSpinner.setAdapter(adapter);
        mSpinner.setEnabled(true);

        return root;
    }

    // Open camera intent after clicking image in UI
    private void dispatchTakePictureIntent(View v)
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted, proceed with image capture (and later saving)
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(v.getContext().getPackageManager()) != null)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
        else // Permission has not yet granted
            askPermission();
    }

    // After a photo has been taken and the tick clicked in UI
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
                // Get image as Bitmap image
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                // Insert image into UI image object
                mCameraImage.setImageBitmap(cameraImage);

                // Create folder for images
                // Location: Phone > Android > data > com.example.sdaassign32022 > files > DCIM > sdaa32024photos
                File myDir = new File(getContext().getExternalFilesDir("DCIM"), "sdaa32024photos");
                // If directory doesn't exist, create it
                if (!myDir.exists())
                    myDir.mkdirs();

                // Name of photo
                String photoName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
                // Determine if image already exists
                File file = new File(myDir, photoName);
                if (file.exists())
                    file.delete(); // Delete if so

            try {
                // Insert image into folder
                FileOutputStream out = new FileOutputStream(file);
                cameraImage.compress(Bitmap.CompressFormat.PNG, 100, out);
                // Cleanup
                out.flush();
                out.close();

                // Change text in TextView
                mImageTextView.setText(R.string.image_text_post_capture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    // Returns the Email Body Message, update this to handle either collection or delivery
    private String createOrderSummary(View v)
    {
        String orderMessage = "";
        String deliveryInstruction = meditDelivery.getText().toString();
        String customerName = getString(R.string.customer_name) + " " + mCustomerName.getText().toString();

        orderMessage += customerName + "\n" + "\n" + getString(R.string.order_message_1);
        orderMessage += "\n" + "Deliver my order to the following address: ";
        orderMessage += "\n" + deliveryInstruction;
        orderMessage += "\n" + getString(R.string.order_message_collect) + mSpinner.getSelectedItem().toString() + "days";
        orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString();

        return orderMessage;
    }

    //Update me to send an email
    private void sendEmail(View v)
    {
        // Check that Name is not empty, and ask if they want to continue
        String customerName = mCustomerName.getText().toString();
        if (mCustomerName == null || customerName.equals(""))
            Toast.makeText(getContext(), getString(R.string.name_missing_error), Toast.LENGTH_SHORT).show();
        else
            Log.d(TAG, "sendEmail: should be sending an email with "+ createOrderSummary(v));
    }
}
