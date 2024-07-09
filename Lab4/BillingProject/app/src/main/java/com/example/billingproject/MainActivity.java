package com.example.billingproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //Use FragmentManager to add MainFragment to fragment_container of MainActivity
        //Look for the fragment container inside MainActivity
        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.main_fragment_container);

        //Switches to BillingFragment()
        if(fragment == null)
        {
            //Always important to commit
            fragment = new MainFragment();
            fn.beginTransaction().add(R.id.main_fragment_container, fragment).commit();
        }

    }
}