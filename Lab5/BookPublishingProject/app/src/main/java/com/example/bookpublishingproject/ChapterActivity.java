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

public class ChapterActivity extends AppCompatActivity {

    int bookIdRetrieve;
    String bookAuthorRetrieve;
    double bookPriceRetrieve;

    private static final String EXTRA_BOOK_ID = "com.example.bookpublishingproject.book_id";
    private static final String EXTRA_BOOK_AUTHOR = "com.example.bookpublishingproject.book_author";
    private static final String EXTRA_BOOK_PRICE = "com.example.bookpublishingproject.book_price";

    public static Intent newIntent(Context packageContext, int bookId, String bookAuthor, double bookPrice) {
        Intent intent = new Intent(packageContext, ChapterActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        intent.putExtra(EXTRA_BOOK_AUTHOR, bookAuthor);
        intent.putExtra(EXTRA_BOOK_PRICE, bookPrice);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chapter);

        // Decoding Extra parameters
        bookIdRetrieve = getIntent().getIntExtra(EXTRA_BOOK_ID, 0);
        bookAuthorRetrieve = getIntent().getStringExtra(EXTRA_BOOK_AUTHOR);
        bookPriceRetrieve = getIntent().getDoubleExtra(EXTRA_BOOK_PRICE, 0.0);

        // Bundle for arguments
        Bundle bundle = new Bundle();
        bundle.putInt("BOOK_ID", bookIdRetrieve);
        bundle.putString("BOOK_AUTHOR", bookAuthorRetrieve);
        bundle.putDouble("BOOK_PRICE", bookPriceRetrieve);


        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.chapter_fragment_container);

        //Switches to ChapterFragment()
        if(fragment == null)
        {
            fn.beginTransaction().add(R.id.chapter_fragment_container, ChapterFragment.class, bundle).commit();
        }

    }
}