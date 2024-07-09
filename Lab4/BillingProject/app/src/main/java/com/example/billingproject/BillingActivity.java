package com.example.billingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.billingproject.model.Billing;

public class BillingActivity extends AppCompatActivity {

    private int clientIdRetrieve;
    private String clientNameRetrieve;
    private String prdNameRetrieve;
    private double prdPriceRetrieve;
    private int prdQtyRetrieve;

    private static final String EXTRA_CLIENT_ID = "com.example.billingproject.client_Id";
    private static final String EXTRA_CLIENT_NAME = "com.example.billingproject.client_Name";
    private static final String EXTRA_PRODUCT_NAME = "com.example.billingproject.product_Name";
    private static final String EXTRA_PRODUCT_PRICE = "com.example.billingproject.prd_Price";
    private static final String EXTRA_PRODUCT_QUANTITY = "com.example.billingproject.prd_Qty";

    public static Intent newIntent(Context packageContext, int client_Id, String client_Name, String product_Name, double prd_Price, int prd_Qty) {
        Intent intent = new Intent(packageContext, BillingActivity.class);
        intent.putExtra(EXTRA_CLIENT_ID, client_Id);
        intent.putExtra(EXTRA_CLIENT_NAME, client_Name);
        intent.putExtra(EXTRA_PRODUCT_NAME, product_Name);
        intent.putExtra(EXTRA_PRODUCT_PRICE, prd_Price);
        intent.putExtra(EXTRA_PRODUCT_QUANTITY, prd_Qty);
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

        Bundle bundle = new Bundle();
        bundle.putInt("client_Id", clientIdRetrieve);
        bundle.putString("client_Name", clientNameRetrieve);
        bundle.putString("product_Name", prdNameRetrieve);
        bundle.putDouble("prd_Price", prdPriceRetrieve);
        bundle.putInt("prd_Qty", prdQtyRetrieve);

        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.billing_fragment_container);

        //Switches to BillingFragment()
        if (fragment == null) {
            fn.beginTransaction().add(R.id.billing_fragment_container, BillingFragment.class, bundle).commit();
        }

    }

}