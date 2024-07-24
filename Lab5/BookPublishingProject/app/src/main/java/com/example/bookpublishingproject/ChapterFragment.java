package com.example.bookpublishingproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookpublishingproject.database.BookPubBHelper;
import com.example.bookpublishingproject.model.Chapter;

public class ChapterFragment extends Fragment {

    private TextView displayBookInfoTextView;
    private EditText chapterIdEditText;
    private EditText chapterTitleEditText;
    private EditText chapterPriceEditText;
    private Button addChapterButton;
    private BookPubBHelper bookPubBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookPubBHelper = new BookPubBHelper(this.getActivity());

        //Asegurar que el menú se cree para el fragmento
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chapter, container, false);

        Toolbar bookPubToolbar = (Toolbar) v.findViewById(R.id.book_pub_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(bookPubToolbar);

        displayBookInfoTextView = (TextView) v.findViewById(R.id.display_book_info_text_view);
        chapterIdEditText = (EditText) v.findViewById(R.id.chapter_id_edit_text);
        chapterTitleEditText = (EditText) v.findViewById(R.id.chapter_title_edit_text);
        chapterPriceEditText = (EditText) v.findViewById(R.id.chapter_price_edit_text);
        addChapterButton = (Button) v.findViewById(R.id.add_chapter_button);

        Bundle args = getArguments();
        if (args != null) {
            int bookId = args.getInt("BOOK_ID");
            String bookAuthor = args.getString("BOOK_AUTHOR");
            double bookPrice = args.getDouble("BOOK_PRICE");

            double bookPriceEuro = bookPrice * 1.45;

            String bookInfo = "B_id: " + bookId + "\n" +
                    "Author: " + bookAuthor + "\n" +
                    "Price: $CAD " + bookPrice + "\n" +
                    "Price: $EUR " + String.format("%.2f", bookPriceEuro);

            displayBookInfoTextView.setText(bookInfo);
        }

        addChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the inputs
                int chapterId = Integer.parseInt(chapterIdEditText.getText().toString());
                String chapterTitle = chapterTitleEditText.getText().toString();
                double chapterPrice = Double.parseDouble(chapterPriceEditText.getText().toString());

                // Retrieve book ID from arguments
                Bundle args = getArguments();
                if (args != null) {
                    int bookId = args.getInt("BOOK_ID");

                    // Create a Chapter object
                    Chapter chapter = new Chapter(bookId, chapterId, chapterTitle, chapterPrice);

                    // Save the chapter to the database
                    bookPubBHelper.addNewChapter(chapter);

                    // Show a toast message
                    Toast.makeText(getActivity(), "Chapter Added", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Inflate course_menu
        inflater.inflate(R.menu.menu_book_pub, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.mainoption) {
            Intent intent = new Intent(getActivity(), PublisherActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.bookoption) {
            Intent intent = new Intent(getActivity(), BookActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStart() {
        super.onStart();

        Log.d("ChapterFragment", "onStart() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("ChapterFragment", "onPause() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ChapterFragment", "onResume() is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ChapterFragment", "onStop() is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ChapterFragment", "onDestroy() is called");
    }

}
