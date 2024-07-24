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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookpublishingproject.database.BookPubBHelper;
import com.example.bookpublishingproject.model.Publisher;

public class PublisherUpdateFragment extends Fragment {

    private EditText pubIdEditText;
    private EditText pubNameEditText;
    private EditText pubAddressEditText;
    private Button updatePubButton;
    private Button clearPubButton;
    private Button addPubButton;
    private Button searchPubButton;
    private Button deletePubButton;
    private BookPubBHelper bookPubBaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookPubBaseHelper = new BookPubBHelper(getContext().getApplicationContext());

        //Asegurar que el menú se cree para el fragmento
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_publisher_update, container, false);

        Toolbar bookPubToolbar = (Toolbar) v.findViewById(R.id.book_pub_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(bookPubToolbar);

        //Initialize UI elements
        pubIdEditText = (EditText) v.findViewById(R.id.pub_id_edit_text);
        pubNameEditText = (EditText) v.findViewById(R.id.pub_name_edit_text);
        pubAddressEditText = (EditText) v.findViewById(R.id.pub_address_edit_text);
        updatePubButton = (Button) v.findViewById(R.id.update_pub_button);
        clearPubButton = (Button) v.findViewById(R.id.clear_pub_button);
        addPubButton = (Button) v.findViewById(R.id.add_pub_button);
        searchPubButton = (Button) v.findViewById(R.id.search_pub_button);
        deletePubButton = (Button) v.findViewById(R.id.delete_pub_button);

        if (getArguments() != null) {
            int pubIdRetrieve = getArguments().getInt("p_id");
            String pubNameRetrieve = getArguments().getString("p_name");
            String pubAddressRetrieve = getArguments().getString("p_address");

            pubIdEditText.setText(String.valueOf(pubIdRetrieve));
            pubNameEditText.setText(pubNameRetrieve);
            pubAddressEditText.setText(pubAddressRetrieve);
        }

        updatePubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pub_id = Integer.parseInt(pubIdEditText.getText().toString());
                String pub_name = pubNameEditText.getText().toString();
                String pub_address = pubAddressEditText.getText().toString();

                Publisher updatedPub = new Publisher(pub_id,pub_name,pub_address);
                bookPubBaseHelper.updatePublisher(updatedPub);
                getActivity().setResult(Activity.RESULT_OK);
                //to send data back to the calling activity when the second activity finishes.
                //to return data or status information to the activity that started the current activity.

                Toast.makeText(getActivity(), "Publisher updated", Toast.LENGTH_SHORT).show();
            }
        });

        clearPubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pubIdEditText.setText("");
                pubNameEditText.setText("");
                pubAddressEditText.setText("");
            }
        });

        addPubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = Integer.parseInt(pubIdEditText.getText().toString());
                String name = pubNameEditText.getText().toString();
                String address = pubAddressEditText.getText().toString();

                Publisher publisher = new Publisher(id, name, address);
                bookPubBaseHelper.addNewPublisher(publisher);
                getActivity().setResult(Activity.RESULT_OK);

                Toast.makeText(getContext(), "Publisher added", Toast.LENGTH_SHORT).show();
            }
        });

        searchPubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int p_id = Integer.parseInt(pubIdEditText.getText().toString());

                Publisher publisherToSearch = new Publisher(p_id,"","");

                Publisher foundPublisher = bookPubBaseHelper.searchPublisher(publisherToSearch);

                if(foundPublisher != null){
                    pubIdEditText.setText(foundPublisher.getP_id()+ "");
                    pubNameEditText.setText(foundPublisher.getP_name());
                    pubAddressEditText.setText(foundPublisher.getP_address());

                    Toast.makeText(getActivity(), "Publisher: " + p_id + " found", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Clear the EditText fields if billing is not found
                    pubIdEditText.setText("");
                    pubNameEditText.setText("Not Found in Database");
                    pubAddressEditText.setText("");

                    Toast.makeText(getActivity(), "Publisher not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deletePubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int p_id = Integer.parseInt(pubIdEditText.getText().toString());

                // Create a Publisher object with just the client_id (it's the primary key)
                Publisher publisherToDelete = new Publisher(p_id, "", "");

                bookPubBaseHelper.deletePublisher(publisherToDelete);
                getActivity().setResult(Activity.RESULT_OK);

                Toast.makeText(getActivity(), "Publisher deleted", Toast.LENGTH_SHORT).show();
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

        Log.d("PublisherUpdateFragment", "onStart() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("PublisherUpdateFragment", "onPause() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PublisherUpdateFragment", "onResume() is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("PublisherUpdateFragment", "onStop() is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("PublisherUpdateFragment", "onDestroy() is called");
    }

}
