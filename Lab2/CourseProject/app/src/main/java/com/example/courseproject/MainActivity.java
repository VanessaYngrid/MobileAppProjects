package com.example.courseproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.courseproject.model.Course;

public class MainActivity extends AppCompatActivity {

    private TextView courseTextView;
    private TextView courseTotalFeesTextView;

    private Button courseTotalFeesButton;
    private Button courseNextButton;

    private Button courseDetailButton;
    private int currentIndex = 0;

    public static String TAG;
    public static String KEY_INDEX="index";
    private Course[] all_courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Course.credits = 3;

        Course c1 = new Course("MIS 101", "Intro. to Info. Systems", 140);
        Course c2 = new Course("MIS 301", "Systems Analysis", 35);
        Course c3 = new Course("MIS 441", "Database Management", 12);
        Course c4 = new Course("CS 155", "Programming in C++", 90);
        Course c5 = new Course("MIS 451", "Web-Based Systems", 30);
        Course c6 = new Course("MIS 551", "Advanced Web", 30);
        Course c7 = new Course("MIS 651", "Advanced Java", 30);

        all_courses = new Course[]{c1,c2,c3,c4,c5,c6,c7};

        //Retrieve currentIndex
        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        //to be able to see the first course
        courseTextView = (TextView) findViewById(R.id.course_text_view);
        courseTextView.setText("Course:" + all_courses[currentIndex].getCourse_no() +
                " " + all_courses[currentIndex].getCourse_name());

        courseTotalFeesTextView = (TextView) findViewById(R.id.course_total_fees_text_view);

        //get the view of the courseTotalFeesButton
        courseTotalFeesButton = (Button) findViewById(R.id.total_fees_button);
        courseTotalFeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                courseTotalFeesTextView.setText("Total Course Fees is " + all_courses[currentIndex].calculateTotalFees());

                //Use Toast class
                Toast.makeText(MainActivity.this,"Total Course Fees is " + all_courses[currentIndex].calculateTotalFees(), Toast.LENGTH_SHORT).show();
            }
        });

        //get the view of the courseNextButton
        courseNextButton = (Button) findViewById(R.id.course_next_button);
        courseNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % all_courses.length;

                courseTextView.setText("Course:" + all_courses[currentIndex].getCourse_no() +
                        " " + all_courses[currentIndex].getCourse_name());
            }
        });

        //get the view of the courseDetailButton
        courseDetailButton = (Button) findViewById(R.id.course_detail_button);
        courseDetailButton.setOnClickListener((new View.OnClickListener(){
            public void onClick(View v)
            {
                //start the CourseActivity (Controller)
                //specify the data to be sent as key, value (from the parent activity to the child)
                String courseId = all_courses[currentIndex].getCourse_no();
                String courseName = all_courses[currentIndex].getCourse_name();
                int courseMaxEnrl = all_courses[currentIndex].getMax_enrl();
                int courseCredits = Course.credits; //credit is a static value
                //call method newIntent() for coding parameters
                Intent intent = CourseActivity.newIntent(MainActivity.this, courseId, courseName, courseMaxEnrl, courseCredits);
                //startActivity(intent); - This method is used when you expect no result data from child activity

                //Create an object when sending data from parent MainActivity
                //When expect result data from the ChildActivity (CourseActivity)
                startActivityIntent.launch(intent);

                //the coding parameters occurs in the MainActivity and the decoding in the ChildActivity(ex: CourseActivity)
                //puede ser al contrario tambien (ambos casos son verdaderos)
            }
        }));

    } //End of onCreate method

    //Launch activity - To use for bidirectional comunication
    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
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
                        Course courseUpdateInfo = CourseActivity.decodeMessageCourseUpdateResult(result.getData());
                        all_courses[currentIndex].setCourse_no(courseUpdateInfo.getCourse_no());
                        all_courses[currentIndex].setCourse_name(courseUpdateInfo.getCourse_name());
                        all_courses[currentIndex].setMax_enrl(courseUpdateInfo.getMax_enrl());
                        all_courses[currentIndex].credits = courseUpdateInfo.credits;

                        Toast.makeText(MainActivity.this, all_courses[currentIndex].getCourse_no()
                                +" " + all_courses[currentIndex].getCourse_name(), Toast.LENGTH_SHORT).show();

                        courseTextView.setText("Course:" + all_courses[currentIndex].getCourse_no() +
                                " " + all_courses[currentIndex].getCourse_name());

                        courseTotalFeesTextView.setText("Total Course Fees is " + all_courses[currentIndex].calculateTotalFees());
                    }
                }
            }
    );

    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
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
        //Retrieve the index on "onCreate
    }
}