package com.example.facultymobileproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.facultymobileproject.model.Faculty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView facultyIdTextView;
    private TextView facultyLastNameTextView;
    private TextView facultyFirstNameTextView;
    private TextView facultySalaryTextView;
    private TextView facultyBonusTextView;
    private TextView totalBonusTextView;
    private Button displayBonusButton;
    private Button nextFacultyButton;
    private Button previousFacultyButton;
    private int currentIndex = 0;

    public static String TAG;
    public static String KEY_INDEX = "index";

    Map<Integer, Faculty> facultyMap;
    private List<Integer> keyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Faculty faculty1 = new Faculty(101, "Robertson", "Myra", "98121", 60000.0, 2.5);
        Faculty faculty2 = new Faculty(212, "Smith", "Neal", "85001", 40000.0, 3.0);
        Faculty faculty3 = new Faculty(315, "Arlec", "Lisa", "71601", 55000.0, 1.5);
        Faculty faculty4 = new Faculty(857, "Filipo", "Paul", "90001", 30000.0, 5.0);
        Faculty faculty5 = new Faculty(370, "Denkan", "Anais", "15001", 95000.0, 1.5);

        //LinkedHashMap to make sure the information will display in the right order
        facultyMap = new LinkedHashMap<>();

        facultyMap.put(101, faculty1);
        facultyMap.put(212, faculty2);
        facultyMap.put(315, faculty3);
        facultyMap.put(857, faculty4);
        facultyMap.put(370, faculty5);

        //convert the set of keys into an ArrayList
        keyList = new ArrayList<>(facultyMap.keySet());

        //Retrieve currentIndex
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        Faculty facultyObj = getCurrentFaculty();

        //to be able to see the first faculty information
        facultyIdTextView = (TextView) findViewById(R.id.facullty_id_text_view);
        facultyIdTextView.setText("Faculty ID: " + facultyObj.getF_Id());

        facultyLastNameTextView = (TextView) findViewById(R.id.faculty_last_name_text_view);
        facultyLastNameTextView.setText("Faculty LName: " + facultyObj.getF_Lname());

        facultyFirstNameTextView = (TextView) findViewById(R.id.faculty_first_name_text_view);
        facultyFirstNameTextView.setText("Faculty FName: " + facultyObj.getF_Fname());

        facultySalaryTextView = (TextView) findViewById(R.id.faculty_salary_text_view);
        facultySalaryTextView.setText("Faculty Salary: " + facultyObj.getF_Salary());

        facultyBonusTextView = (TextView) findViewById(R.id.faculty_bonus_text_view);
        facultyBonusTextView.setText("Faculty Bonus: " + facultyObj.getF_bonus());

        //Another way to display the faculty member's information is using the method displayFacultyInfo
        //displayFacultyInfo(getCurrentFaculty());

        //get the view of the displayBonusButton
        displayBonusButton = (Button) findViewById(R.id.display_bonus_button);
        displayBonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Faculty facultyObj = getCurrentFaculty();

                totalBonusTextView = (TextView) findViewById(R.id.total_bonus_text_view);
                totalBonusTextView.setText("Bonus:  " + facultyObj.calculate_Bonus());

                //Use Toast class
                Toast.makeText(MainActivity.this, "Bonus: " + facultyObj.calculate_Bonus(), Toast.LENGTH_SHORT).show();
            }
        });

        //get the view of the nextFacultyButton
        nextFacultyButton = (Button) findViewById(R.id.next_faculty_button);
        nextFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % keyList.size();
                Faculty facultyObj = getCurrentFaculty();

                facultyIdTextView.setText("Faculty ID: " + facultyObj.getF_Id());
                facultyLastNameTextView.setText("Faculty LName: " + facultyObj.getF_Lname());
                facultyFirstNameTextView.setText("Faculty FName: " + facultyObj.getF_Fname());
                facultySalaryTextView.setText("Faculty Salary: " + facultyObj.getF_Salary());
                facultyBonusTextView.setText("Faculty Bonus: " + facultyObj.getF_bonus());

                //Another way to display the member's faculty information is
                // displayFacultyInfo(getCurrentFaculty());
            }
        });

        //get the view of the previousFacultyButton
        previousFacultyButton = (Button) findViewById(R.id.previous_faculty_button);
        previousFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add keyList.size() to ensures that the result is always non-negative.
                currentIndex = (currentIndex - 1 + keyList.size()) % keyList.size();
                Faculty facultyObj = getCurrentFaculty();

                facultyIdTextView.setText("Faculty ID: " + facultyObj.getF_Id());
                facultyLastNameTextView.setText("Faculty LName: " + facultyObj.getF_Lname());
                facultyFirstNameTextView.setText("Faculty FName: " + facultyObj.getF_Fname());
                facultySalaryTextView.setText("Faculty Salary: " + facultyObj.getF_Salary());
                facultyBonusTextView.setText("Faculty Bonus: " + facultyObj.getF_bonus());

                //Another way to display the member's faculty information is
                // displayFacultyInfo(getCurrentFaculty());
            }
        });

    }

    private Faculty getCurrentFaculty()
    {
        return facultyMap.get(keyList.get(currentIndex));
    }

    /*
    private void displayFacultyInfo(Faculty faculty) {
        facultyIdTextView.setText("Faculty ID: " + faculty.getF_Id());
        facultyLastNameTextView.setText("Faculty LName: " + faculty.getF_Lname());
        facultyFirstNameTextView.setText("Faculty FName: " + faculty.getF_Fname());
        facultySalaryTextView.setText("Faculty Salary: " + faculty.getF_Salary());
        facultyBonusTextView.setText("Faculty Bonus: " + faculty.getF_bonus());
    }
     */

    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    //to restart the app
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    protected void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        Log.d(TAG, "onSaveInstanceState() called");

        onSavedInstanceState.putInt(KEY_INDEX, currentIndex);
        //Retrieve the index on "onCreate"
    }
}