package com.example.billingproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.billingproject.database.BillingBaseHelper;
import com.example.billingproject.model.Billing;

import java.util.ArrayList;

public class BillingViewFragment extends Fragment {

    private TextView billingListTextView;
    private BillingBaseHelper bBaseHelper;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bBaseHelper = new BillingBaseHelper(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_billing_view, container, false);

        billingListTextView = v.findViewById(R.id.billingList_text_view);

        //Get the data from the database using BillingBaseHelper
        ArrayList<Billing> billingsModalArrayList = bBaseHelper.readBilling();

        // Construir el texto a mostrar en billingListTextView utilizando toString() de Billing
        String allBillings="";
        for (Billing billing : billingsModalArrayList) {
            allBillings += billing.toString();
        }

        // Mostrar los registros en billingListTextView
        billingListTextView.setText(allBillings);


        return v;


    }
}
