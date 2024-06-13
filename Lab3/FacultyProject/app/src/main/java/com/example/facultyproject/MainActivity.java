package com.example.facultyproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.facultyproject.model.Faculty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView facultyIdTextView;
    private TextView facultyLastNameTextView;
    private TextView facultyFirstNameTextView;
    private TextView facultySalaryTextView;
    private TextView facultyRateBonusTextView;
    private TextView facultyAmountBonusTextView;
    private Button previousFacultyButton;
    private Button nextFacultyButton;
    private Button facultyDetailsButton;
    private Button allFacultiesButton;

    private int currentIndex = 0;
    private int updateIndex;
    public static String TAG;
    public static String KEY_INDEX = "index";

    public static String KEY_UPDATE_INDEX="Update index";
    //To save the data when rotate to the normal orientation from the landscape
    public static String KEY_UPDATE_INFO = "updated info array";
    private Faculty[] all_faculties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Faculty f1 = new Faculty(101, "Robertson", "Myra", 60000.0, 2.5);
        Faculty f2 = new Faculty(212, "Smith", "Neal", 40000.0, 3.0);
        Faculty f3 = new Faculty(315, "Arlec", "Lisa", 55000.0, 1.5);
        Faculty f4 = new Faculty(857, "Filipo", "Paul", 30000.0, 5.0);
        Faculty f5 = new Faculty(370, "Denkan", "Anais", 95000.0, 1.5);
        Faculty f6 = new Faculty(246, "Vargas", "Vanessa", 10000.0, 5.0);
        Faculty f7 = new Faculty(135, "Vilela", "Janitza", 45000.0, 1.5);
        Faculty f8 = new Faculty(468, "Zegarra", "Kevin", 34000.0, 2.5);
        Faculty f9 = new Faculty(579, "Gomez", "Alonso", 85000.0, 1.5);

        all_faculties = new Faculty[]{f1,f2,f3,f4,f5,f6,f7,f8,f9};

        //Retrieve currentIndex
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            //To save the data when rotate to the normal orientation from the landscape
            updateIndex = savedInstanceState.getInt(KEY_UPDATE_INDEX);

            String[] updateInfo = savedInstanceState.getStringArray(KEY_UPDATE_INFO);

            for (int i = 0; i < updateInfo.length; i++) {
                all_faculties[updateIndex].setF_Id(Integer.parseInt(updateInfo[i]));
                all_faculties[updateIndex].setF_Fname(updateInfo[++i]);
                all_faculties[updateIndex].setF_Lname(updateInfo[++i]);
                all_faculties[updateIndex].setF_Salary(Double.parseDouble(updateInfo[++i]));
                all_faculties[updateIndex].setF_bonus(Double.parseDouble(updateInfo[++i]));
            }
        }

        //to be able to see the first faculty information
        facultyIdTextView = (TextView) findViewById(R.id.facullty_no_text_view);
        facultyIdTextView.setText("Faculty No: " + all_faculties[currentIndex].getF_Id());

        facultyLastNameTextView = (TextView) findViewById(R.id.faculty_last_name_text_view);
        facultyLastNameTextView.setText("Faculty LName: " + all_faculties[currentIndex].getF_Lname());

        facultyFirstNameTextView = (TextView) findViewById(R.id.faculty_first_name_text_view);
        facultyFirstNameTextView.setText("Faculty FName: " + all_faculties[currentIndex].getF_Fname());

        facultySalaryTextView = (TextView) findViewById(R.id.faculty_salary_text_view);
        facultySalaryTextView.setText("Faculty Salary: " + all_faculties[currentIndex].getF_Salary());

        facultyRateBonusTextView = (TextView) findViewById(R.id.faculty_rate_bonus_text_view);
        facultyRateBonusTextView.setText("Faculty Rate Bonus: " + all_faculties[currentIndex].getF_bonus());

        facultyAmountBonusTextView = (TextView) findViewById(R.id.faculty_amount_bonus_text_view);
        facultyAmountBonusTextView.setText("Faculty Amount Bonus: " + all_faculties[currentIndex].calculate_Bonus());


        //get the view of the nextFacultyButton
        nextFacultyButton = (Button) findViewById(R.id.next_faculty_button);
        nextFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % all_faculties.length;

                facultyIdTextView.setText("Faculty ID: " + all_faculties[currentIndex].getF_Id());
                facultyLastNameTextView.setText("Faculty LName: " + all_faculties[currentIndex].getF_Lname());
                facultyFirstNameTextView.setText("Faculty FName: " + all_faculties[currentIndex].getF_Fname());
                facultySalaryTextView.setText("Faculty Salary: " + all_faculties[currentIndex].getF_Salary());
                facultyRateBonusTextView.setText("Faculty Rate Bonus: " + all_faculties[currentIndex].getF_bonus());
                facultyAmountBonusTextView.setText("Faculty Amount Bonus: " + all_faculties[currentIndex].calculate_Bonus());

                //Another way to display the member's faculty information is
                // displayFacultyInfo(getCurrentFaculty());
            }
        });

        //get the view of the previousFacultyButton
        previousFacultyButton = (Button) findViewById(R.id.prev_faculty_button);
        previousFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add keyList.size() to ensures that the result is always non-negative.
                currentIndex = (currentIndex - 1 + all_faculties.length) % all_faculties.length;

                facultyIdTextView.setText("Faculty ID: " + all_faculties[currentIndex].getF_Id());
                facultyLastNameTextView.setText("Faculty LName: " + all_faculties[currentIndex].getF_Lname());
                facultyFirstNameTextView.setText("Faculty FName: " + all_faculties[currentIndex].getF_Fname());
                facultySalaryTextView.setText("Faculty Salary: " + all_faculties[currentIndex].getF_Salary());
                facultyRateBonusTextView.setText("Faculty Rate Bonus: " + all_faculties[currentIndex].getF_bonus());
            }
        });

        //get the view of the FacultyDetailButton
        facultyDetailsButton = (Button) findViewById(R.id.faculty_details_button);
        facultyDetailsButton.setOnClickListener((new View.OnClickListener(){
            public void onClick(View v)
            {
                int facultyId = all_faculties[currentIndex].getF_Id();
                String facultyFname = all_faculties[currentIndex].getF_Fname();
                String facultyLname = all_faculties[currentIndex].getF_Lname();
                double facultySalary = all_faculties[currentIndex].getF_Salary();
                double facultyRateBonus = all_faculties[currentIndex].getF_bonus();

                //call method newIntent() for coding parameters
                Intent intent = FacultyActivity.newIntent(MainActivity.this, facultyId, facultyFname, facultyLname, facultySalary, facultyRateBonus);
                startActivityIntent.launch(intent);

            }
        }));

        allFacultiesButton = findViewById(R.id.all_faculties_button);
        allFacultiesButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            ArrayList<Faculty> facultyList = new ArrayList<>(Arrays.asList(all_faculties));
            Intent intent = AllFacultiesActivity.newIntent(MainActivity.this, facultyList);
            startActivity(intent);
        }
        });

    }

    ActivityResultLauncher<Intent>startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    //Decoding Result coming from child activity CourseActivity
                    if (result.getResultCode() != Activity.RESULT_OK)
                    {
                        return;
                    }
                    else
                    {
                        //Calling decoded extra parameters
                        Faculty facultyUpdateInfo = FacultyActivity.decodeMessageFacultyUpdateResult(result.getData());
                        all_faculties[currentIndex].setF_Id(facultyUpdateInfo.getF_Id());
                        all_faculties[currentIndex].setF_Fname(facultyUpdateInfo.getF_Fname());
                        all_faculties[currentIndex].setF_Lname(facultyUpdateInfo.getF_Lname());
                        all_faculties[currentIndex].setF_Salary(facultyUpdateInfo.getF_Salary());
                        all_faculties[currentIndex].setF_bonus(facultyUpdateInfo.getF_bonus());

                        updateIndex = currentIndex;

                    }
                }
            }
    );


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

    protected void onSaveInstanceState(@NonNull Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        Log.d(TAG, "onSaveInstanceState() called");

        //Retrieve the index on "onCreate"
        onSavedInstanceState.putInt(KEY_INDEX, currentIndex);

        onSavedInstanceState.putInt(KEY_UPDATE_INDEX, updateIndex);

        String facultyIdUpdate = all_faculties[updateIndex].getF_Id()+"";
        String facultyFnameUpdate = all_faculties[updateIndex].getF_Fname();
        String facultyLnameUpdate = all_faculties[updateIndex].getF_Lname();
        String facultySalaryUpdate = all_faculties[updateIndex].getF_Salary()+"";
        String facultyRateBonusUpdate = all_faculties[updateIndex].getF_bonus()+"";

        String[] updateInfo = new String[]{facultyIdUpdate,facultyFnameUpdate,facultyLnameUpdate,facultySalaryUpdate,facultyRateBonusUpdate};

        onSavedInstanceState.putStringArray(KEY_UPDATE_INFO, updateInfo);

    }
}