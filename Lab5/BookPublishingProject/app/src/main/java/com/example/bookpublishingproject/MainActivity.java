package com.example.bookpublishingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        //Use FragmentManager to add PublisherDisplayFragment to main_fragment_container of MainActivity
        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.main_fragment_container);

        if(fragment == null)
        {
            fragment = new PublisherDisplayFragment();
            fn.beginTransaction().add(R.id.main_fragment_container, fragment).commit();
        }

    }
}