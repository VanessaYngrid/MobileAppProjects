package com.example.billingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.billingproject.database.BillingBaseHelper;
import com.example.billingproject.model.Billing;

import java.util.ArrayList;

public class BillingFragment extends Fragment {

    private EditText clientIdUpdateEditText;
    private EditText clientNameUpdateEditText;
    private EditText prdNameUpdateEditText;
    private EditText prdPriceUpdateEditText;
    private EditText prdQtyUpdateEditText;
    private Button updateBillingButton;
    private Button deleteBillingButton;
    private Button searchBillingButton;
    private Button viewBillingButton;

    private BillingBaseHelper bBaseHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_billing, container, false);

        // Initialize UI elements
        clientIdUpdateEditText = (EditText) v.findViewById(R.id.client_id_update_edit_text);
        clientNameUpdateEditText = (EditText) v.findViewById(R.id.client_name_update_edit_text);
        prdNameUpdateEditText = (EditText) v.findViewById(R.id.prd_name_update_edit_text);
        prdPriceUpdateEditText = (EditText) v.findViewById(R.id.prd_price_update_edit_text);
        prdQtyUpdateEditText = (EditText) v.findViewById(R.id.prd_qty_update_edit_text);

        int client_id = requireArguments().getInt("client_Id");
        String client_name = requireArguments().getString("client_Name");
        String product_name = requireArguments().getString("product_Name");
        double prd_price = requireArguments().getDouble("prd_Price");
        int prd_qty = requireArguments().getInt("prd_Qty");

        clientIdUpdateEditText.setText(client_id+"");
        clientNameUpdateEditText.setText(client_name);
        prdNameUpdateEditText.setText(product_name);
        prdPriceUpdateEditText.setText(prd_price+"");
        prdQtyUpdateEditText.setText(prd_qty+"");

        updateBillingButton = (Button) v.findViewById(R.id.billing_update_button);
        updateBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int client_id = Integer.parseInt(clientIdUpdateEditText.getText().toString());
                String client_name = clientNameUpdateEditText.getText().toString();
                String product_name = prdNameUpdateEditText.getText().toString();
                double prd_price = Double.parseDouble(prdPriceUpdateEditText.getText().toString());
                int prd_qty = Integer.parseInt(prdQtyUpdateEditText.getText().toString());

                Billing updatedBilling = new Billing(client_id, client_name, product_name, prd_price, prd_qty);

                bBaseHelper.updateBilling(updatedBilling);

                Toast.makeText(getActivity(), "Client: "+client_id+" updated", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBillingButton = (Button) v.findViewById(R.id.billing_delete_button);
        deleteBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int client_id = Integer.parseInt(clientIdUpdateEditText.getText().toString());

                // Create a Billing object with just the client_id (it's the primary key)
                Billing billingToDelete = new Billing(client_id, "", "", 0.0, 0);

                bBaseHelper.deleteBilling(billingToDelete);

                Toast.makeText(getActivity(), "Client: " + client_id + " deleted", Toast.LENGTH_SHORT).show();
            }
        });

        searchBillingButton = (Button) v.findViewById(R.id.billing_search_button);
        searchBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int client_id = Integer.parseInt(clientIdUpdateEditText.getText().toString());

                Billing billingToSearch = new Billing(client_id,"","",0.0,0);

                Billing foundBilling = bBaseHelper.searchBilling(billingToSearch);

                if(foundBilling != null){
                    clientNameUpdateEditText.setText(foundBilling.getClient_Name());
                    prdNameUpdateEditText.setText(foundBilling.getProduct_Name());
                    prdPriceUpdateEditText.setText(foundBilling.getPrd_Price()+"");
                    prdQtyUpdateEditText.setText(foundBilling.getPrd_Qty()+"");

                    Toast.makeText(getActivity(), "Client: " + client_id + " found", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Clear the EditText fields if billing is not found
                    clientNameUpdateEditText.setText("Not Found in Database");
                    prdNameUpdateEditText.setText("");
                    prdPriceUpdateEditText.setText("");
                    prdQtyUpdateEditText.setText("");

                    Toast.makeText(getActivity(), "Client not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewBillingButton = (Button) v.findViewById(R.id.billing_view_button);
        viewBillingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Leer todos los registros de la base de datos
                ArrayList<Billing> billings = bBaseHelper.readBilling();

                //Create a new instance of billingViewFragment and send all the data
                BillingViewFragment billingViewFragment = new BillingViewFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("billings", billings);
                billingViewFragment.setArguments(bundle);

                // Create new fragment and transaction
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                //Replace whatever is in the fragment_container view with billingViewFragment
                transaction.replace(R.id.billing_fragment_container, billingViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return v;

    }

    @Override
    public void onStart() {
        //could add the business logic inside of onCreate() into onStart() instead, since they are all apart of the Life Cycle Activity
        super.onStart();

        bBaseHelper = new BillingBaseHelper(getContext().getApplicationContext());

        //to see output in Logcat (for debugging purposes)
        Log.d("BillingFragment", "onStart() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BillingFragment", "onPause() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BillingFragment", "onResume() is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("BillingFragment", "onStop() is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BillingFragment", "onDestroy() is called");
    }

}
