package com.example.bookpublishingproject;

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

public class PublisherActivity extends AppCompatActivity {

    private int pubIdRetrieve;
    private String pubNameRetrieve;
    private String pubAddressRetrieve;

    private static final String EXTRA_PUBLISHER_ID = "com.example.bookpublishingproject.p_id";
    private static final String EXTRA_PUBLISHER_NAME = "com.example.bookpublishingproject.p_name";
    private static final String EXTRA_PUBLISHER_ADDRESS = "com.example.bookpublishingproject.p_address";

    public static Intent newIntent(Context packageContext, int p_id, String p_name, String p_address) {
        Intent intent = new Intent(packageContext, PublisherActivity.class);
        intent.putExtra(EXTRA_PUBLISHER_ID, p_id);
        intent.putExtra(EXTRA_PUBLISHER_NAME, p_name);
        intent.putExtra(EXTRA_PUBLISHER_ADDRESS, p_address);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_publisher);

        //Decoding Extra parameters (Retrieve coded parameter from the MainActivity)
        pubIdRetrieve = getIntent().getIntExtra(EXTRA_PUBLISHER_ID, 0);
        pubNameRetrieve = getIntent().getStringExtra(EXTRA_PUBLISHER_NAME);
        pubAddressRetrieve = getIntent().getStringExtra(EXTRA_PUBLISHER_ADDRESS);

        //Bundle for arguments
        Bundle bundle = new Bundle();
        bundle.putInt("p_id", pubIdRetrieve);
        bundle.putString("p_name", pubNameRetrieve);
        bundle.putString("p_address", pubAddressRetrieve);


        //Use FragmentManager to add PublisherUpdateFragment to publisher_fragment_container of PublisherActivity
        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.publisher_fragment_container);

        //Switches to PublisherUpdateFragment()
        if(fragment == null)
        {
            fn.beginTransaction().add(R.id.publisher_fragment_container, PublisherUpdateFragment.class, bundle).commit();
        }

    }
}