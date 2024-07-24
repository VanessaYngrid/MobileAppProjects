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

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);

        //Use FragmentManager to add BookFragment to book_fragment_container of BookActivity
        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.book_fragment_container);

      /*  //Switches to PublisherUpdateFragment()
        if(fragment == null)
        {
            fn.beginTransaction().add(R.id.book_fragment_container, BookFragment.class, bundle).commit();
        }*/

        if(fragment == null)
        {
            fragment = new BookFragment();
            fn.beginTransaction().add(R.id.book_fragment_container, fragment).commit();
        }


    }
}