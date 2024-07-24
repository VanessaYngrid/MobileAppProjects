package com.example.bookpublishingproject;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookpublishingproject.database.BookPubBHelper;
import com.example.bookpublishingproject.model.Book;
import com.example.bookpublishingproject.model.Publisher;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {

    private EditText bookIdEditText;
    private EditText bookAuthorEditText;
    private EditText bookTitleEditText;
    private EditText bookIsbnEditText;
    private Spinner bookTypeSpinner;
    private EditText bookPriceEditText;
    private Button addBookButton;
    private Button addChapterToTheBook;
    private Spinner publisherIdSpinner;
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

        View v = inflater.inflate(R.layout.fragment_book, container, false);

        Toolbar bookPubToolbar = (Toolbar) v.findViewById(R.id.book_pub_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(bookPubToolbar);

        bookIdEditText = (EditText) v.findViewById(R.id.book_id_edit_text);
        bookAuthorEditText = (EditText) v.findViewById(R.id.book_author_edit_text);
        bookTitleEditText = (EditText) v.findViewById(R.id.book_title_edit_text);
        bookIsbnEditText = (EditText) v.findViewById(R.id.book_isbn_edit_text);
        bookTypeSpinner = (Spinner) v.findViewById(R.id.book_type_spinner);
        bookPriceEditText = (EditText) v.findViewById(R.id.book_price_edit_text);
        publisherIdSpinner = (Spinner) v.findViewById(R.id.spinner_select_publisher);
        addBookButton = (Button) v.findViewById(R.id.add_book_button);
        addChapterToTheBook = (Button) v.findViewById(R.id.add_chapter_list_button);

        // Retrieve publisher data from the database and set up the Spinner
        setupPublisherSpinner();

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bookId = Integer.parseInt(bookIdEditText.getText().toString());
                String author = bookAuthorEditText.getText().toString();
                String title = bookTitleEditText.getText().toString();
                String isbn = bookIsbnEditText.getText().toString();
                String type = bookTypeSpinner.getSelectedItem().toString();
                double price = Double.parseDouble(bookPriceEditText.getText().toString());

                // Extract publisher ID from the spinner
                String selectedPublisher = publisherIdSpinner.getSelectedItem().toString();
                int publisherId = Integer.parseInt(selectedPublisher.split(" ")[0]);

                //new Book object
                Book book = new Book(bookId, author, title, isbn, type, price, publisherId);

                //Add the book to the database
                bookPubBHelper.addNewBook(book);

                // Show a toast message
                Toast.makeText(getActivity(), "Book Added", Toast.LENGTH_SHORT).show();

            }
        });

        addChapterToTheBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bookId = Integer.parseInt(bookIdEditText.getText().toString());
                String author = bookAuthorEditText.getText().toString();
                double price = Double.parseDouble(bookPriceEditText.getText().toString());

                Intent intent = ChapterActivity.newIntent(getActivity(), bookId, author, price);
                startActivity(intent);
            }
        });

        return v;
    }

    private void setupPublisherSpinner() {
        List<Publisher> publisherList = bookPubBHelper.readPublishers();
        List<String> publisherNames = new ArrayList<>();
        for (Publisher publisher : publisherList) {
            publisherNames.add(publisher.getP_id() + " " + publisher.getP_name());  // Get the publisher name
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, publisherNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        publisherIdSpinner.setAdapter(adapter);

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

        Log.d("BookFragment", "onStart() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BookFragment", "onPause() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BookFragment", "onResume() is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("BookFragment", "onStop() is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BookFragment", "onDestroy() is called");
    }

}