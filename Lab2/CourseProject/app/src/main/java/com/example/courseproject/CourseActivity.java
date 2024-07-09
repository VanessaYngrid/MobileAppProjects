package com.example.courseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.courseproject.model.Course;

public class CourseActivity extends AppCompatActivity {

    private EditText courseIdEditText;
    private EditText courseNameEditText;
    private EditText courseMaxEnrlEditText;
    private EditText courseCreditsEditText;
    private Button courseUpdateButton;
    private String courseIdRetrieve;
    private String courseNameRetrieve;
    private int courseMaxEnrlRetrieve;
    private int courseCreditsRetrieve;
    private static String EXTRA_COURSE_NO="com.example.courseproject.course_no";
    private static String EXTRA_COURSE_NAME="com.example.courseproject.course_name";
    private static String EXTRA_COURSE_MAX_ENRL="com.example.courseproject.max_enrl";
    private static String EXTRA_COURSE_CREDITS="com.example.courseproject.course_credits";

    //Use for coding extra parameters from the MainActivity to child CourseActivity
    public static Intent newIntent(Context packageContext, String course_id, String course_name, int max_enrl, int course_credits)
    {
        Intent intent = new Intent(packageContext, CourseActivity.class);
        intent.putExtra(EXTRA_COURSE_NO,course_id);
        intent.putExtra(EXTRA_COURSE_NAME,course_name);
        intent.putExtra(EXTRA_COURSE_MAX_ENRL,max_enrl);
        intent.putExtra(EXTRA_COURSE_CREDITS,course_credits);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);

        //Decoding Extra parameters (Retrieve coded parameter from the MainActivity)
        courseIdRetrieve = getIntent().getStringExtra(EXTRA_COURSE_NO);
        courseNameRetrieve = getIntent().getStringExtra(EXTRA_COURSE_NAME);
        courseMaxEnrlRetrieve = getIntent().getIntExtra(EXTRA_COURSE_MAX_ENRL, 0); //for the int is 2 parameters
        courseCreditsRetrieve = getIntent().getIntExtra(EXTRA_COURSE_CREDITS, 0);//for the int is 2 parameters

        //get the view of courseIdEditText
        courseIdEditText= (EditText) findViewById(R.id.course_no_edit_text);
        courseIdEditText.setText(courseIdRetrieve);

        courseNameEditText= (EditText) findViewById(R.id.course_name_edit_text);
        courseNameEditText.setText(courseNameRetrieve);

        courseMaxEnrlEditText= (EditText) findViewById(R.id.course_max_enrl_edit_text);
        courseMaxEnrlEditText.setText(courseMaxEnrlRetrieve + ""); //Add " " to convert to string

        courseCreditsEditText= (EditText) findViewById(R.id.course_credits_edit_text);
        courseCreditsEditText.setText(courseCreditsRetrieve + ""); //Add " " to convert to string

        //get the view of courseUpdateButton
        courseUpdateButton= (Button) findViewById(R.id.course_update_button);
        courseUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send or coding parameters the update course info to Parent Activity
                setCourseUpdateResult(courseIdEditText.getText().toString(),
                        courseNameEditText.getText().toString(),
                       Integer.parseInt(courseMaxEnrlEditText.getText().toString()),
                        Integer.parseInt(courseCreditsEditText.getText().toString()));
            }
        });

        //Why coding and decoding in the same file (ChildActivity)? -
        //to avoid repetition, to be able to use another class implicitly and reusability
        //How many classes - Class Bundle and Intent

    } //End of onCreate Method

    //coding extra parameters from child CourseActivity to parent MainActivity

    private void setCourseUpdateResult(String course_id, String course_name, int max_enrl, int course_credits)
    {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_COURSE_NO,course_id);
        dataIntent.putExtra(EXTRA_COURSE_NAME,course_name);
        dataIntent.putExtra(EXTRA_COURSE_MAX_ENRL,max_enrl);
        dataIntent.putExtra(EXTRA_COURSE_CREDITS,course_credits);
        setResult(RESULT_OK, dataIntent);
    }

    //Used when I update an info and I want to go back to the previous screen
    //Decoding extra parameters in Parent MainActivity
    public static Course decodeMessageCourseUpdateResult(Intent resultIntent)
    {
        Course courseUpdatedInfo = new Course();
        courseUpdatedInfo.setCourse_no(resultIntent.getStringExtra(EXTRA_COURSE_NO));
        courseUpdatedInfo.setCourse_name(resultIntent.getStringExtra(EXTRA_COURSE_NAME));
        courseUpdatedInfo.setMax_enrl(resultIntent.getIntExtra(EXTRA_COURSE_MAX_ENRL, 0));
        courseUpdatedInfo.credits = resultIntent.getIntExtra(EXTRA_COURSE_CREDITS, 0);

        return courseUpdatedInfo;
    }
}