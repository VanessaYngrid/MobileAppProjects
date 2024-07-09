package com.example.billingproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.billingproject.database.BillingBaseHelper;
import com.example.billingproject.model.Billing;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MainFragment extends Fragment {

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
    private int updateIndex = 0;
    private BillingBaseHelper bBaseHelper;
    private ArrayList<Billing> billingArray;
    public static String KEY_INDEX = "index";
    public static String KEY_UPDATE_INDEX = "update index";

    //private Context context; why not?

    Billing b1 = new Billing(101, "Johnston Jane", "Chair", 99.99, 2);
    Billing b2 = new Billing(105, "Fikhali Samuel", "Table", 139.99, 1);
    Billing b3 = new Billing(107, "Samson Amina", "KeyUSB", 14.99, 2);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //in case need to redo table
        //baseHelper.dropTable();

        //Why not getActivity?
        //billingBaseHelper = new BillingBaseHelper(getActivity());
        bBaseHelper = new BillingBaseHelper(this.getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Change view to fragment_main
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        billingInfoTextView = (TextView) v.findViewById(R.id.billing_info_text_view);
        clientIdEditText = (EditText) v.findViewById(R.id.client_id_edit_text);
        clientNameEditText = (EditText) v.findViewById(R.id.client_name_edit_text);
        prdNameEditText = (EditText) v.findViewById(R.id.prd_name_edit_text);
        prdPriceEditText = (EditText) v.findViewById(R.id.prd_price_edit_text);
        prdQtyEditText = (EditText) v.findViewById(R.id.prd_qty_edit_text);


        totalInputBillingButton = (Button) v.findViewById(R.id.total_input_billing_button);
        totalInputBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientID = Integer.parseInt(clientIdEditText.getText().toString());
                String clientName = clientNameEditText.getText().toString();
                String productName = prdNameEditText.getText().toString();
                double price = Double.parseDouble(prdPriceEditText.getText().toString());
                int quantity = Integer.parseInt(prdQtyEditText.getText().toString());

                Billing newBilling = new Billing(clientID, clientName, productName, price, quantity);

                billingArray.add(newBilling);
                bBaseHelper.createBilling(newBilling);

                String displayUserInput = "Client: " + clientID + ", " +
                        clientName + ", Product: " +
                        productName + " is " +
                        String.format("%.2f", newBilling.totalBilling()) + " $";

                billingInfoTextView.setText(displayUserInput);
                Toast.makeText(getActivity(), displayUserInput, Toast.LENGTH_SHORT).show();

            }
        });

        totalRecordBillingButton = (Button) v.findViewById(R.id.total_record_billing_button);
        totalRecordBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billingInfoTextView.setText(displayInfo(currentIndex));

                Toast.makeText(getActivity(), (displayInfo(currentIndex)), Toast.LENGTH_SHORT).show();
            }
        });

        //get the view of the nextBillingButton
        nextBillingButton = (Button) v.findViewById(R.id.next_billing_button);
        nextBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % billingArray.size();

                billingInfoTextView.setText(displayInfo(currentIndex));
            }
        });

        //get the view of the previousBillingButton
        previousBillingButton = (Button) v.findViewById(R.id.prev_billing_button);
        previousBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = ((currentIndex - 1) + billingArray.size()) % billingArray.size();

                billingInfoTextView.setText(displayInfo(currentIndex));
            }
        });

        BillingDetailsButton = (Button) v.findViewById(R.id.billing_details_button);
        BillingDetailsButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {

                // Obtener los datos del elemento actual en billingArray
                int clientID = billingArray.get(currentIndex).getClient_Id();
                String clientName = billingArray.get(currentIndex).getClient_Name();
                String productName = billingArray.get(currentIndex).getProduct_Name();
                double productPrice = billingArray.get(currentIndex).getPrd_Price();
                int productQuantity = billingArray.get(currentIndex).getPrd_Qty();

                updateIndex = currentIndex;

                //Intent to start BillingActivity
                Intent intent = BillingActivity.newIntent(getActivity(), clientID, clientName, productName, productPrice, productQuantity);

                // Iniciar la actividad o fragmento según tu estructura
                startActivity(intent);
            }
        }));
        return v;
    }

    private String displayInfo(int index) {
        return "Client: "+billingArray.get(index).getClient_Id()+", "+
                billingArray.get(index).getClient_Name()+", Product: "+
                billingArray.get(index).getProduct_Name()+" is "+
                String.format("%.2f",billingArray.get(index).totalBilling())+" $";
    }

    //To save the index
    public void onSaveInstanceState(@NonNull Bundle onSavedInstanceState) {

        super.onSaveInstanceState(onSavedInstanceState);

        onSavedInstanceState.putInt(KEY_INDEX, currentIndex);
        onSavedInstanceState.putInt(KEY_UPDATE_INDEX, updateIndex);
    }

    public void onStart() {
        //could add the business logic inside of onCreate() into onStart() instead, since they are all apart of the Life Cycle Activity
        super.onStart();

       bBaseHelper = new BillingBaseHelper(this.getContext());

        //this code is in onStart() so that it will run if returning to this activity
        //onCreate() will only be called the first time the app is created
        //initiating database if database is empty
       if (bBaseHelper.readBilling().isEmpty())
        {
            bBaseHelper.createBilling(b1);
            bBaseHelper.createBilling(b2);
            bBaseHelper.createBilling(b3);

            billingArray = new ArrayList<>();

            billingArray.add(b1);
            billingArray.add(b2);
            billingArray.add(b3);
        } else {
            billingArray = new ArrayList<>();
            billingArray.addAll(bBaseHelper.readBilling());
        }

        billingInfoTextView.setText(displayInfo(updateIndex));

        //to see output in Logcat (for debugging purposes)
        Log.d("MainFragment", "onStart() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MainFragment", "onPause() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MainFragment", "onResume() is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MainFragment", "onStop() is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MainFragment", "onDestroy() is called");
    }

}

