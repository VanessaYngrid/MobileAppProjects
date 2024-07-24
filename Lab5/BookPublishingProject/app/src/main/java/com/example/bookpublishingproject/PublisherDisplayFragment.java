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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookpublishingproject.database.BookPubBHelper;
import com.example.bookpublishingproject.model.Publisher;

import java.util.ArrayList;


public class PublisherDisplayFragment extends Fragment {

    private TextView pubIdTextView;
    private TextView pubNameTextView;
    private TextView pubAddressTextView;
    private Button prevPubButton;
    private Button nextPubButton;
    private Button pubDetailsButton;
    private Button showBookButton;
    private ArrayList<Publisher> pubArray;
    //private Context context;
    private BookPubBHelper bookPubBaseHelper;
    private int currentIndex = 0; // Track the current publisher index
    private int updateIndex = 0;
    public static String KEY_INDEX = "index";
    public static String KEY_UPDATE_INDEX = "update index";

    Publisher p1 = new Publisher(1,"Microsoft Press USA" , "Canada");
    Publisher p2 = new Publisher(2, "OReilly Media USA", "Canada");
    Publisher p3 = new Publisher(3, "Pearson Education", "Canada");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookPubBaseHelper = new BookPubBHelper(this.getContext());

        //Asegurar que el menú se cree para el fragmento
        setHasOptionsMenu(true);

        //Instantiate an object from BookPubBHelper
        //save the data BookPubBHelper
        if(bookPubBaseHelper.readPublishers().isEmpty())
        {
            bookPubBaseHelper.addNewPublisher(p1);
            bookPubBaseHelper.addNewPublisher(p2);
            bookPubBaseHelper.addNewPublisher(p3);

            pubArray = new ArrayList<>();

            pubArray.add(p1);
            pubArray.add(p2);
            pubArray.add(p3);
        } else{
            pubArray = new ArrayList<>();
            pubArray.addAll(bookPubBaseHelper.readPublishers());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_publisher_display, container, false);

        Toolbar bookPubToolbar = (Toolbar) v.findViewById(R.id.book_pub_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(bookPubToolbar);

        pubIdTextView = (TextView) v.findViewById(R.id.pub_id_text);
        pubNameTextView = (TextView) v.findViewById(R.id.pub_name_text);
        pubAddressTextView = (TextView) v.findViewById(R.id.pub_address_text);
        prevPubButton = (Button) v.findViewById(R.id.prev_pub_button);
        nextPubButton = (Button) v.findViewById(R.id.next_pub_button);
        pubDetailsButton = (Button) v.findViewById(R.id.pub_details_button);
        showBookButton = (Button) v.findViewById(R.id.show_book_button);

        // Set the initial publisher details
        displayPublisher(currentIndex);

        prevPubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = ((currentIndex - 1) + pubArray.size()) % pubArray.size();

                displayPublisher(currentIndex);
            }
        });

        nextPubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % pubArray.size();

                displayPublisher(currentIndex);
            }
        });

        pubDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the data of the current element of pubArray
                int pubId = pubArray.get(currentIndex).getP_id();
                String pubName = pubArray.get(currentIndex).getP_name();
                String pubAddress = pubArray.get(currentIndex).getP_address();

                updateIndex = currentIndex;

                //Intent to start PublisherActivity
                Intent intent = PublisherActivity.newIntent(getActivity(), pubId, pubName, pubAddress);
                // Iniciar la actividad o fragmento según tu estructura
                startActivity(intent);
            }
        });

        showBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Intent to start BookActivity
                    Intent intent = new Intent(getActivity(), BookActivity.class);

                    startActivity(intent);
                }

        });

        return v;
    }

    public void displayPublisher(int index)
    {
        // Set the initial publisher details
        Publisher initialPublisher = pubArray.get(index);

        pubIdTextView.setText(initialPublisher.getP_id() + "");
        pubNameTextView.setText(initialPublisher.getP_name());
        pubAddressTextView.setText(initialPublisher.getP_address());
    }

    //To save the index
    public void onSaveInstanceState(@NonNull Bundle onSavedInstanceState) {

        super.onSaveInstanceState(onSavedInstanceState);

        onSavedInstanceState.putInt(KEY_INDEX, currentIndex);
        onSavedInstanceState.putInt(KEY_UPDATE_INDEX, updateIndex);
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

    // Ensures that when the fragment first becomes visible, it initializes correctly and loads the data
    @Override
    public void onStart() {
        super.onStart();

        bookPubBaseHelper = new BookPubBHelper(this.getContext());

        if(bookPubBaseHelper.readPublishers().isEmpty())
        {
            bookPubBaseHelper.addNewPublisher(p1);
            bookPubBaseHelper.addNewPublisher(p2);
            bookPubBaseHelper.addNewPublisher(p3);

            pubArray = new ArrayList<>();

            pubArray.add(p1);
            pubArray.add(p2);
            pubArray.add(p3);
        } else{
            pubArray = new ArrayList<>();
            pubArray.addAll(bookPubBaseHelper.readPublishers());
        }

        displayPublisher(currentIndex);

        Log.d("PublishDisplayFragment", "onStart() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MainFragment", "onPause() is called");
    }

    //Ensures that whenever the fragment comes back to the foreground, it updates with the most current data.
    //This is particularly useful if the data might have changed while the fragment was not visible.
    @Override
    public void onResume() {
        super.onResume();

        bookPubBaseHelper = new BookPubBHelper(this.getContext());

        if(bookPubBaseHelper.readPublishers().isEmpty())
        {
            bookPubBaseHelper.addNewPublisher(p1);
            bookPubBaseHelper.addNewPublisher(p2);
            bookPubBaseHelper.addNewPublisher(p3);

            pubArray = new ArrayList<>();

            pubArray.add(p1);
            pubArray.add(p2);
            pubArray.add(p3);
        } else{
            pubArray = new ArrayList<>();
            pubArray.addAll(bookPubBaseHelper.readPublishers());
        }

        displayPublisher(currentIndex);

        Log.d("PublishDisplayFragment", "onResume() is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("PublishDisplayFragment", "onStop() is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("PublishDisplayFragment", "onDestroy() is called");
    }
}