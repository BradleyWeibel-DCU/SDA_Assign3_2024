package com.example.sdaassign32022;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.List;

/**
 * @author Bradley Weibel
 * @version 1.0.0
 */
public class OrderTshirt extends Fragment {

    // Required empty public constructor
    public OrderTshirt() { }

    // Class wide variables
    private Spinner mCollectionDaysSpinner, mOrderTypeSpinner, mOrderAmountSpinner;
    private TextView mImageTextView, mCollectionTextView;
    private RadioGroup mRadioGroup;
    private RadioButton mDeliverBtn, chosenRadioButton;
    private EditText mCustomerName, mEditDelivery;
    private ImageView mCameraImage;
    private Button mSendButton;
    private Uri photo;

    // Static keys
    private static int REQUEST_CODE = 100;
    private static final int REQUEST_TAKE_PHOTO = 2;

    /**
     * Executes at start up of fragment.
     * Maps class variables to UI elements.
     * Sets up initial state of delivery and collection UI elements.
     * Sets listeners on UI elements for clicks (image, radio buttons, send button).
     * Setup spinners/dropdowns.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view Completed view is returned.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment get the root view.
        final View root = inflater.inflate(R.layout.fragment_order_tshirt, container, false);

        // Map UI elements to variables
        mImageTextView = root.findViewById(R.id.imageText);
        mRadioGroup = root.findViewById(R.id.radioGroupOrder);
        mDeliverBtn = root.findViewById(R.id.radioBtnDeliver);
        mCustomerName = root.findViewById(R.id.editCustomer);
        mEditDelivery = root.findViewById(R.id.editDeliveryAddress);
        mCollectionTextView = root.findViewById(R.id.collectTextView);
        mCollectionDaysSpinner = root.findViewById(R.id.spinnerCollectionDays);
        mOrderTypeSpinner = root.findViewById(R.id.spinnerOrderType);
        mOrderAmountSpinner = root.findViewById(R.id.spinnerOrderAmount);
        mCameraImage = root.findViewById(R.id.imageView);
        mSendButton = root.findViewById(R.id.sendButton);

        // UI Delivery address field handling
        mEditDelivery.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditDelivery.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Setup initial UI state of delivery vs collection elements
        hideAndClearDeliveryEditField();
        hideCollectionDetails();

        // Radio button handling when a user selects an option
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * Handling of radio button selection in terms of what UI elements are shown.
             * @param radioGroup group of radio buttons
             * @param i selected radio button
             */
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Get selected radio button
                chosenRadioButton = root.findViewById(i);
                // Determine which radio button was selected
                if (chosenRadioButton.getText().toString() == mDeliverBtn.getText())
                {
                    // Delivery option chosen
                    showDeliveryEditField();
                    hideCollectionDetails();
                }
                else
                {
                    // Collection option selected
                    hideAndClearDeliveryEditField();
                    showCollectionDetails();
                }
            }
        });

        // Set a listener on the the camera image
        mCameraImage.setOnClickListener(new View.OnClickListener() {
            /**
             * User clicks the image in UI
             * @param v view
             */
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);
            }
        });

        // Set a listener for when user clicks the SUBMIT ORDER button
        mSendButton.setOnClickListener(new View.OnClickListener() {
            /**
             * User clicks the SUBMIT ORDER btn in UI
             * @param v view
             */
            @Override
            public void onClick(View v) {
                attemptSendEmail(v);
            }
        });

        // Spinners handling
        // Collection days spinner using the integer array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_time_entries, R.layout.spinner_days);
        mCollectionDaysSpinner.setAdapter(adapter);
        mCollectionDaysSpinner.setEnabled(true);

        // Order Type spinner using array
        ArrayAdapter<CharSequence> stringAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_shirt_type_entries, R.layout.spinner_type);
        mOrderTypeSpinner.setAdapter(stringAdapter);
        mOrderTypeSpinner.setEnabled(true);

        // Order Amount spinner using array
        adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_order_amount_entries, R.layout.spinner_amount);
        mOrderAmountSpinner.setAdapter(adapter);
        mOrderAmountSpinner.setEnabled(true);

        return root;
    }

    /**
     * Sets the delivery field's visibility to true
     */
    private void showDeliveryEditField()
    {
        mEditDelivery.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the delivery field's visibility to false
     * Clears the delivery field's text
     */
    private void hideAndClearDeliveryEditField()
    {
        mEditDelivery.setVisibility(View.INVISIBLE);
        mEditDelivery.setText("");
    }

    /**
     * Sets the collection label and spinner's visibility to true
     */
    private void showCollectionDetails() {
        mCollectionTextView.setVisibility(View.VISIBLE);
        mCollectionDaysSpinner.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the collection label and spinner's visibility to false
     */
    private void hideCollectionDetails() {
        mCollectionTextView.setVisibility(View.INVISIBLE);
        mCollectionDaysSpinner.setVisibility(View.INVISIBLE);
    }

    /**
     * Opens the activity to capture a photo and has given permission
     * If user has not yet given permission, then redirected to appropriate method
     * @param v view
     */
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
            askStoragePermission();
    }

    // After a photo has been taken and the tick clicked in UI
    /**
     * Attempts to show image in UI, save image in file system and populate photo class variable.
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
            // TODO: Improve quality of image inserted in UI and stored on device
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
                // Populate image variable
                photo = Uri.fromFile(file);
                // Cleanup
                out.flush();
                out.close();

                // Change text in image TextView
                mImageTextView.setText(R.string.image_text_post_capture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Ask permission to use device storage
    /**
     * Asks user permission to use external storage in device.
     */
    private void askStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    // User wants to submit the order / send the email
    /**
     * Verifies user fulfills requirements for creating email from passed data.
     * If user has data missing, toast message is shown, prompting user to insert said missing data.
     * @param v
     */
    private void attemptSendEmail(View v)
    {
        // Determine if all criteria are met for submission of order
        if (mCustomerName.getText().toString().equals(""))                                                                          // Is name field empty
            Toast.makeText(getContext(), getString(R.string.name_missing_error), Toast.LENGTH_SHORT).show();
        else if (mImageTextView.getText().toString().equals(getString(R.string.image_text_pre_capture)))                                              // Has a photo been chosen
            Toast.makeText(getContext(), getString(R.string.photo_missing_error), Toast.LENGTH_SHORT).show();
        else if (chosenRadioButton == null)                                                                                         // Is a order receive method choice missing
            Toast.makeText(getContext(), getString(R.string.radio_choice_missing_error), Toast.LENGTH_SHORT).show();
        else if (chosenRadioButton.getText().toString() == mDeliverBtn.getText() && mEditDelivery.getText().toString().equals(""))  // Delivery method selected, but no delivery address inserted
            Toast.makeText(getContext(), getString(R.string.delivery_option_error), Toast.LENGTH_SHORT).show();
        else                                                                                                                        // All good, send email
            createEmail();
    }

    // Create and populate the email, then open the email app
    /**
     * Creates email from user inserted data.
     * Starts email activity if successful.
     * If not email app is present, user shown a error toast message.
     */
    private void createEmail()
    {
        // Declare intent to send an email (only email applications are selected with ACTION_SENDTO)
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        // Set email data to be passed to the intent
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.email_address) });
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_order_subject));
        intent.putExtra(Intent.EXTRA_TEXT, createEmailBody());
        intent.putExtra(Intent.EXTRA_STREAM, photo);

        // Using mailto: only shows email applications to perform our action, not other apps like Google drive, etc
        intent.setData(Uri.parse("mailto:"));

        // Get list of email apps
        final PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);

        if (list.size() == 0) // No email app has been found, show error message
            Toast.makeText(getContext(), getString(R.string.errorMessageEmailAppNotFound), Toast.LENGTH_SHORT).show();
        else
            startActivity(intent);
    }

    // Returns the email body message
    /**
     * Creates body of email message from data inserted by user.
     * @return string of email body
     */
    private String createEmailBody()
    {
        String orderMessage = "";
        orderMessage += getString(R.string.order_greeting) + "\n" + "\n" + getString(R.string.order_message_1) + "\n";
        orderMessage += "\n" + getString(R.string.order_type) + " " + mOrderTypeSpinner.getSelectedItem().toString() + ".";
        orderMessage += "\n" + getString(R.string.order_amount) + " " + mOrderAmountSpinner.getSelectedItem().toString() + ".";
        orderMessage += "\n";

        // Determine if order is to be delivered or collected
        if (chosenRadioButton.getText().toString() == mDeliverBtn.getText())
        {
            // Order is to be delivered
            orderMessage += "\n" + getString(R.string.order_message_deliver);
            // Add user's delivery address
            orderMessage += "\n" + mEditDelivery.getText().toString() + ".";
        }
        else
        {
            // Order is to be collected
            orderMessage += "\n" + getString(R.string.order_message_collect) + mCollectionDaysSpinner.getSelectedItem().toString() + " " + getString(R.string.order_message_days);
        }

        // Final text of email body
        orderMessage += "\n" + "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString() + ".";

        return orderMessage;
    }
}
