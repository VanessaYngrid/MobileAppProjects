package com.example.facultyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyproject.model.Faculty;

import java.util.ArrayList;

public class AllFacultiesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FacultyAdapter adapter;
    private ArrayList<Faculty> facultyList;

    public static Intent newIntent(Context context, ArrayList<Faculty> facultyList) {
        Intent intent = new Intent(context, AllFacultiesActivity.class);
        //intent.putExtra("facultyList", facultyList);
        intent.putParcelableArrayListExtra("facultyList", facultyList);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_faculties);

        // Get the list of faculties from the intent
        //facultyList = (ArrayList<Faculty>) getIntent().getSerializableExtra("facultyList");
        facultyList = getIntent().getParcelableArrayListExtra("facultyList");

        recyclerView = findViewById(R.id.recycler_view_faculty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new FacultyAdapter(facultyList, this);
        recyclerView.setAdapter(adapter);
    }

    public void showBonus(double bonus) {
        Toast.makeText(this, "Faculty Bonus: " + bonus, Toast.LENGTH_SHORT).show();
    }

}


