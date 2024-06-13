package com.example.studentgradeproject;

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

import com.example.studentgradeproject.model.Grade;

public class MainActivity extends AppCompatActivity {

    private TextView studentNameTextView;
    private TextView gradeAverageTextView;
    private Button displayStudentGradeAverageButton;
    private Button previousStudentButton;
    private Button nextStudentButton;

    private int currentIndex=0;
    public static String TAG;
    public static String KEY_INDEX="index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Grade s1 = new Grade(1, "Grahan", "Bill", 60, 70, 98,80,90);
        Grade s2 = new Grade(2, "Sanchez", "Jim", 88, 72, 90,83,93);
        Grade s3 = new Grade(3, "White", "Peter", 85, 80, 45,93,70);
        Grade s4 = new Grade(4, "Phelp", "David", 70, 60, 60,90,70);
        Grade s5 = new Grade(5, "Lewis", "Sheila", 50, 76, 87,59,72);
        Grade s6 = new Grade(6, "James", "Thomas", 89, 99, 97,98,99);

        Grade[] studentGrades = new Grade[]{s1,s2,s3,s4,s5,s6};

        //Retrieve currentIndex
        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        studentNameTextView = (TextView) findViewById(R.id.student_name_text_view);
        studentNameTextView.setText("Student: " + studentGrades[currentIndex].getStudent_lname() + " " + studentGrades[currentIndex].getStudent_fname());

        //get the view of the courseTotalFeesButton
        displayStudentGradeAverageButton = (Button) findViewById(R.id.display_student_grade_average_button);
        displayStudentGradeAverageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gradeAverageTextView = (TextView) findViewById(R.id.grade_average_text_view);
                gradeAverageTextView.setText("Grade Average is: " + studentGrades[currentIndex].calculate_GradeAverage());

                //Use Toast class (to show the popup message)
                Toast.makeText(MainActivity.this,"Grade Average is: " + studentGrades[currentIndex].calculate_GradeAverage(), Toast.LENGTH_SHORT).show();
            }
        });

        previousStudentButton = (Button) findViewById(R.id.previous_student_button);
        previousStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex - 1 + studentGrades.length) % studentGrades.length;
                studentNameTextView.setText("Student: " + studentGrades[currentIndex].getStudent_lname() + " " + studentGrades[currentIndex].getStudent_fname());
            }
        });

        nextStudentButton = (Button) findViewById(R.id.next_student_button);
        nextStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % studentGrades.length;
                studentNameTextView.setText("Student: " + studentGrades[currentIndex].getStudent_lname() + " " + studentGrades[currentIndex].getStudent_fname());
            }
        });

    }

    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    //to restart the app
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    protected void onSaveInstanceState(Bundle onSavedInstanceState)
    {
        super.onSaveInstanceState(onSavedInstanceState);
        Log.d(TAG,"onSaveInstanceState() called");

        onSavedInstanceState.putInt(KEY_INDEX, currentIndex);
        //Retrieve the index on "onCreate"

    }
}