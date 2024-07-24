package com.example.coursesproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coursesproject.model.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseOperationActivity extends AppCompatActivity {

    private EditText opCourseNoEditText;
    private EditText opCourseNameEditText;
    private EditText opMaxEnrlEditText;
    private EditText opCourseCreditsEditText;
    private TextView opCourseListTextView;
    private Button opeAddCourseButton;
    private Button opSearchCourseButton;
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_operation);

        databaseRef = FirebaseDatabase.getInstance().getReference();

        opCourseNoEditText = (EditText) findViewById(R.id.course_no_op_edit_text);
        opCourseNameEditText = (EditText) findViewById(R.id.course_name_op_edit_text);
        opMaxEnrlEditText = (EditText) findViewById(R.id.max_enrl_op_edit_text);
        opCourseCreditsEditText = (EditText) findViewById(R.id.course_credits_op_edit_text);
        opCourseListTextView = (TextView) findViewById(R.id.course_list_op_text_view);
        opeAddCourseButton = (Button) findViewById(R.id.op_add_course_button);
        opSearchCourseButton = (Button) findViewById(R.id.op_search_course_button);

        opeAddCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseNo = opCourseNoEditText.getText().toString();
                String courseName = opCourseNameEditText.getText().toString();
                int maxEnrl = Integer.parseInt(opMaxEnrlEditText.getText().toString());

                Course addCourseToFirebase = new Course(courseNo, courseName, maxEnrl);

                databaseRef.child("ExtraCourses").child(addCourseToFirebase.getCourse_no()).setValue(addCourseToFirebase);

                Toast.makeText(CourseOperationActivity.this, "Course added to Firebase", Toast.LENGTH_SHORT).show();

                opCourseNoEditText.setText("Enter Course No");
                opCourseNameEditText.setText("");
                opMaxEnrlEditText.setText("");
                opCourseCreditsEditText.setText("");

                databaseRef.child("ExtraCourses").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String allCourses = "";

                        for(DataSnapshot ss:snapshot.getChildren())
                        {
                            allCourses += ss.getValue(Course.class).toString() +"\n";
                        }

                        opCourseListTextView.setText(allCourses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        opSearchCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseRef.child("ExtraCourses").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String courseNo = opCourseNoEditText.getText().toString();

                        for (DataSnapshot ss: snapshot.getChildren()) {
                            if (ss.getKey().equals(courseNo)) {
                                String courseName = ss.getValue(Course.class).getCourse_name();
                                int maxEnrl = ss.getValue(Course.class).getMax_enrl();

                                opCourseNameEditText.setText(courseName);
                                opMaxEnrlEditText.setText(maxEnrl+"");
                                opCourseCreditsEditText.setText(Course.credits+"");

                                opCourseListTextView.setText(ss.getValue(Course.class).toString());

                                Toast.makeText(CourseOperationActivity.this, "Course Found in Firebase", Toast.LENGTH_SHORT).show();

                                return;
                            }
                            else {
                                opCourseNameEditText.setText("Course Not Found");
                                opMaxEnrlEditText.setText("");
                                opCourseCreditsEditText.setText("");
                                opCourseCreditsEditText.setText("");

                                Toast.makeText(CourseOperationActivity.this, "Course Not Found in Firebase", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

    }
}