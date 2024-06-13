package com.example.billingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.billingproject.model.Billing;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText clientIdEditText;
    private EditText clientNameEditText;
    private EditText prdNameEditText;
    private EditText prdPriceEditText;
    private EditText prdQtyEditText;
    private TextView billingInfoTextView;
    private Button totalInputBillingButton;
    private Button totalRecordBillingButton;
    private Button BillingDetailsButton;
    private Button previousBillingButton;
    private Button nextBillingButton;

    private int currentIndex = 0;
    private int updateIndex;
    public static String TAG;
    public static String KEY_INDEX = "index";
    public static String KEY_UPDATE_INDEX="Update index";
    //To save the data when rotate to the normal orientation from the landscape
    public static String KEY_UPDATE_INFO = "updated info array";

    //CHANGE TO ARRAYLIST
    private ArrayList<Billing> billingArray = new ArrayList<>();

    Billing b1 = new Billing(101, "Johnston Jane", "Chair", 99.99, 2);
    Billing b2 = new Billing(105, "Fikhali Samuel", "Table", 139.99, 1);
    Billing b3 = new Billing(107, "Samson Amina", "KeyUSB", 14.99, 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        billingArray.add(b1);
        billingArray.add(b2);
        billingArray.add(b3);

        //Retrieve currentIndex
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            //To save the data when rotate to the normal orientation from the landscape
            updateIndex = savedInstanceState.getInt(KEY_UPDATE_INDEX);

            ArrayList<String> updateInfo = savedInstanceState.getStringArrayList(KEY_UPDATE_INFO);

            for (int i=0;i<updateInfo.size();i++) {
                billingArray.get(updateIndex).setClient_Id(Integer.parseInt(updateInfo.get(i)));
                billingArray.get(updateIndex).setClient_Name(updateInfo.get(++i));
                billingArray.get(updateIndex).setProduct_Name(updateInfo.get(++i));
                billingArray.get(updateIndex).setPrd_Price(Double.parseDouble(updateInfo.get(++i)));
                billingArray.get(updateIndex).setPrd_Qty(Integer.parseInt(updateInfo.get(++i)));
            }
        }

        billingInfoTextView = (TextView) findViewById(R.id.billing_info_text_view);
        billingInfoTextView.setText(displayInfo(currentIndex));

        //get the view of the courseTotalFeesButton
        totalInputBillingButton = (Button) findViewById(R.id.total_input_billing_button);
        totalInputBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientIdEditText = (EditText) findViewById(R.id.client_id_edit_text);
                clientNameEditText = (EditText) findViewById(R.id.client_name_edit_text);
                prdNameEditText = (EditText) findViewById(R.id.prd_name_edit_text);
                prdPriceEditText = (EditText) findViewById(R.id.prd_price_edit_text);
                prdQtyEditText = (EditText) findViewById(R.id.prd_qty_edit_text);

                int clientID = Integer.parseInt(clientIdEditText.getText().toString());
                String clientName = clientNameEditText.getText().toString();
                String productName = prdNameEditText.getText().toString();
                double price = Double.parseDouble(prdPriceEditText.getText().toString());
                int quantity = Integer.parseInt(prdQtyEditText.getText().toString());

                Billing userBilling = new Billing(clientID, clientName, productName, price, quantity);

                billingArray.add(userBilling);

                billingInfoTextView = (TextView) findViewById(R.id.billing_info_text_view);

                String displayUserInput = "Client: "+clientID+", "+
                        clientName+", Product: "+
                        productName+" is "+
                        String.format("%.2f",userBilling.totalBilling())+" $";

                billingInfoTextView.setText(displayUserInput);

                Toast.makeText(MainActivity.this, displayUserInput, Toast.LENGTH_SHORT).show();
            }
        });

        totalRecordBillingButton = (Button) findViewById(R.id.total_record_billing_button);
        totalRecordBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billingInfoTextView.setText(displayInfo(currentIndex));

                Toast.makeText(MainActivity.this, (displayInfo(currentIndex)), Toast.LENGTH_SHORT).show();
            }
        });

        //get the view of the nextBillingButton
        nextBillingButton = (Button) findViewById(R.id.next_billing_button);
        nextBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % billingArray.size();

                billingInfoTextView = findViewById(R.id.billing_info_text_view);

                billingInfoTextView.setText(displayInfo(currentIndex));
            }
        });

        //get the view of the previousBillingButton
        previousBillingButton = (Button) findViewById(R.id.prev_billing_button);
        previousBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = ((currentIndex - 1) + billingArray.size()) % billingArray.size();

                billingInfoTextView = findViewById(R.id.billing_info_text_view);

                billingInfoTextView.setText(displayInfo(currentIndex));
            }
        });

        BillingDetailsButton = (Button) findViewById(R.id.billing_details_button);
        BillingDetailsButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {

                int clientID = billingArray.get(currentIndex).getClient_Id();
                String clientName = billingArray.get(currentIndex).getClient_Name();
                String productName = billingArray.get(currentIndex).getProduct_Name();
                double productPrice = billingArray.get(currentIndex).getPrd_Price();
                int productQuantity = billingArray.get(currentIndex).getPrd_Qty();

                Intent intent = BillingActivity.newIntent(MainActivity.this, clientID, clientName, productName, productPrice, productQuantity);

                startActivityIntent.launch(intent);

            }
        }));
    }

    //Launch activity - To use for bidirectional comunication
    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    //Decoding Result coming from child activity CourseActivity
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        return;
                    }
                    else {

                        Billing billingUpdateInfo = BillingActivity.decodeMessageBillingUpdateResult(result.getData());
                        billingArray.get(currentIndex).setClient_Id(billingUpdateInfo.getClient_Id());
                        billingArray.get(currentIndex).setClient_Name(billingUpdateInfo.getClient_Name());
                        billingArray.get(currentIndex).setProduct_Name(billingUpdateInfo.getProduct_Name());
                        billingArray.get(currentIndex).setPrd_Price(billingUpdateInfo.getPrd_Price());
                        billingArray.get(currentIndex).setPrd_Qty(billingUpdateInfo.getPrd_Qty());

                        updateIndex = currentIndex;

                        billingInfoTextView.setText(displayInfo(currentIndex));

                        Toast.makeText(MainActivity.this, displayInfo(currentIndex), Toast.LENGTH_SHORT).show();

                    }
                }
            }
    );

    private String displayInfo(int index) {
        return "Client: "+billingArray.get(index).getClient_Id()+", "+
                billingArray.get(index).getClient_Name()+", Product: "+
                billingArray.get(index).getProduct_Name()+" is "+
                String.format("%.2f",billingArray.get(index).totalBilling())+" $";
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    //to restart the app
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    protected void onSaveInstanceState(@NonNull Bundle onSavedInstanceState) {

        super.onSaveInstanceState(onSavedInstanceState);

        onSavedInstanceState.putInt(KEY_INDEX, currentIndex);
        onSavedInstanceState.putInt(KEY_UPDATE_INDEX, updateIndex);

        String clientIDUpdate = billingArray.get(updateIndex).getClient_Id()+"";
        String clientNameUpdate = billingArray.get(updateIndex).getClient_Name();
        String productNameUpdate = billingArray.get(updateIndex).getProduct_Name();
        String priceUpdate = billingArray.get(updateIndex).getPrd_Price()+"";
        String quantityUpdate = billingArray.get(updateIndex).getPrd_Qty()+"";

        ArrayList<String> updateInfo = new ArrayList<>();
        updateInfo.add(clientIDUpdate);
        updateInfo.add(clientNameUpdate);
        updateInfo.add(productNameUpdate);
        updateInfo.add(priceUpdate);
        updateInfo.add(quantityUpdate);

        onSavedInstanceState.putStringArrayList(KEY_UPDATE_INFO, updateInfo);
    }

}

