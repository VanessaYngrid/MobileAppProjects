package com.example.billingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.billingproject.model.Billing;

public class BillingActivity extends AppCompatActivity {

    private EditText clientIdUpdateEditText;
    private EditText clientNameUpdateEditText;
    private EditText prdNameUpdateEditText;
    private EditText prdPriceUpdateEditText;
    private EditText prdQtyUpdateEditText;
    private Button updateBillingButton;
    private int clientIdRetrieve;
    private String clientNameRetrieve;
    private String prdNameRetrieve;
    private double prdPriceRetrieve;
    private int prdQtyRetrieve;
    private static String EXTRA_CLIENT_ID="com.example.billingproject.client_Id";
    private static String EXTRA_CLIENT_NAME="com.example.billingproject.client_Name";
    private static String EXTRA_PRODUCT_NAME="com.example.billingproject.product_Name";
    private static String EXTRA_PRODUCT_PRICE="com.example.billingproject.prd_Price";
    private static String EXTRA_PRODUCT_QUANTITY="com.example.billingproject.prd_Qty";

    //Use for coding extra parameters from the MainActivity to child CourseActivity
    public static Intent newIntent(Context packageContext, int client_Id, String client_Name, String product_Name, double prd_Price, int prd_Qty)
    {
        Intent intent = new Intent(packageContext, BillingActivity.class);
        intent.putExtra(EXTRA_CLIENT_ID,client_Id);
        intent.putExtra(EXTRA_CLIENT_NAME,client_Name);
        intent.putExtra(EXTRA_PRODUCT_NAME,product_Name);
        intent.putExtra(EXTRA_PRODUCT_PRICE,prd_Price);
        intent.putExtra(EXTRA_PRODUCT_QUANTITY,prd_Qty);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_billing);

        //Decoding Extra parameters (Retrieve coded parameter from the MainActivity)
        clientIdRetrieve = getIntent().getIntExtra(EXTRA_CLIENT_ID, 0);
        clientNameRetrieve = getIntent().getStringExtra(EXTRA_CLIENT_NAME);
        prdNameRetrieve = getIntent().getStringExtra(EXTRA_PRODUCT_NAME);
        prdPriceRetrieve = getIntent().getDoubleExtra(EXTRA_PRODUCT_PRICE, 0.0);
        prdQtyRetrieve = getIntent().getIntExtra(EXTRA_PRODUCT_QUANTITY, 0);

        //get the view of courseIdEditText
        clientIdUpdateEditText= (EditText) findViewById(R.id.client_id_update_edit_text);
        clientIdUpdateEditText.setText(clientIdRetrieve + ""); //Add " " to convert to string

        clientNameUpdateEditText= (EditText) findViewById(R.id.client_name_update_edit_text);
        clientNameUpdateEditText.setText(clientNameRetrieve);

        prdNameUpdateEditText= (EditText) findViewById(R.id.prd_name_update_edit_text);
        prdNameUpdateEditText.setText(prdNameRetrieve);

        prdPriceUpdateEditText= (EditText) findViewById((R.id.prd_price_update_edit_text));
        prdPriceUpdateEditText.setText(prdPriceRetrieve + "");

        prdQtyUpdateEditText= (EditText) findViewById(R.id.prd_qty_update_edit_text);
        prdQtyUpdateEditText.setText(prdQtyRetrieve + ""); //Add " " to convert to string

        //get the view of courseUpdateButton
        updateBillingButton= (Button) findViewById(R.id.billing_update_button);
        updateBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send or coding parameters the update course info to Parent Activity
                setBillingUpdateResult(Integer.parseInt(clientIdUpdateEditText.getText().toString()),
                        clientNameUpdateEditText.getText().toString(),
                        prdNameUpdateEditText.getText().toString(),
                        Double.parseDouble(prdPriceUpdateEditText.getText().toString()),
                        Integer.parseInt(prdQtyUpdateEditText.getText().toString()));
            }
        });
    }

    //coding extra parameters from child CourseActivity to parent MainActivity

    private void setBillingUpdateResult(int client_Id, String client_Name, String product_Name, double prd_Price, int prd_Qty)
    {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_CLIENT_ID,client_Id);
        dataIntent.putExtra(EXTRA_CLIENT_NAME,client_Name);
        dataIntent.putExtra(EXTRA_PRODUCT_NAME,product_Name);
        dataIntent.putExtra(EXTRA_PRODUCT_PRICE,prd_Price);
        dataIntent.putExtra(EXTRA_PRODUCT_QUANTITY,prd_Qty);
        setResult(RESULT_OK, dataIntent);
    }

    //Decoding extra parameters in Parent MainActivity
    public static Billing decodeMessageBillingUpdateResult(Intent resultIntent)
    {
        Billing billingUpdatedInfo = new Billing();

        billingUpdatedInfo.setClient_Id(resultIntent.getIntExtra(EXTRA_CLIENT_ID, 0));
        billingUpdatedInfo.setClient_Name(resultIntent.getStringExtra(EXTRA_CLIENT_NAME));
        billingUpdatedInfo.setProduct_Name(resultIntent.getStringExtra(EXTRA_PRODUCT_NAME));
        billingUpdatedInfo.setPrd_Price(resultIntent.getDoubleExtra(EXTRA_PRODUCT_PRICE, 0.0));
        billingUpdatedInfo.setPrd_Qty(resultIntent.getIntExtra(EXTRA_PRODUCT_QUANTITY, 0));

        return billingUpdatedInfo;
    }
}