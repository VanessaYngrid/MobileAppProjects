package com.example.coursesproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.coursesproject.database.CourseBaseHelper;
import com.example.coursesproject.model.Course;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CourseFragment extends Fragment {

    //All widgest will be managed by the fragment
    private TextView courseTextView;
    private TextView courseTotalFeesTextView;
    private TextView courseListTextView;
    private Button courseTotalFeesButton;
    private Button courseNextButton;

    private Button courseDetailButton;
    private int currentIndex = 0;

    private Course[] all_courses;
    private ArrayList<Course> courseModalArrayList;
    private Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Course.credits = 3;

        Course c1 = new Course("MIS 101", "Intro. to Info. Systems", 140);
        Course c2 = new Course("MIS 301", "Systems Analysis", 35);
        Course c3 = new Course("MIS 441", "Database Management", 12);
        Course c4 = new Course("CS 155", "Programming in C++", 90);
        Course c5 = new Course("MIS 451", "Web-Based Systems", 30);
        Course c6 = new Course("MIS 551", "Advanced Web", 30);
        Course c7 = new Course("MIS 651", "Advanced Java", 30);

        all_courses = new Course[]{c1, c2, c3, c4, c5, c6, c7};

        //Instantiate an object from CourseBaseHelper
        //save the data courseBaseHelper.addNewCourse(c1);
        context = getContext().getApplicationContext();
        CourseBaseHelper courseBaseHelper = new CourseBaseHelper(context);
        courseBaseHelper.addNewCourse(c1);
        courseBaseHelper.addNewCourse(c2);
        courseBaseHelper.addNewCourse(c3);
        courseBaseHelper.addNewCourse(c4);
        courseBaseHelper.addNewCourse(c5);
        courseBaseHelper.addNewCourse(c6);
        courseBaseHelper.addNewCourse(c7);

        //Update record 3
        c3.setCourse_name(("Android Data System"));
        courseBaseHelper.updateCourse(c3);

    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedIntanceState) {

        View v = inflater.inflate(R.layout.fragment_course, container, false);

        //to be able to see the first course
        courseTextView = (TextView) v.findViewById(R.id.course_text_view);
        courseTextView.setText("Course:" + all_courses[currentIndex].getCourse_no() +
                " " + all_courses[currentIndex].getCourse_name());

        courseTotalFeesTextView = (TextView) v.findViewById(R.id.course_total_fees_text_view);

        //get the view of the courseTotalFeesButton
        courseTotalFeesButton = (Button) v.findViewById(R.id.total_fees_button);
        courseTotalFeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                courseTotalFeesTextView.setText("Total Course Fees is " + all_courses[currentIndex].calculateTotalFees());

                //Use Toast class
                Toast.makeText(getActivity(), "Total Course Fees is " + all_courses[currentIndex].calculateTotalFees(), Toast.LENGTH_SHORT).show();
            }
        });

        //get the view of the courseNextButton
        courseNextButton = (Button) v.findViewById(R.id.course_next_button);
        courseNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % all_courses.length;

                courseTextView.setText("Course:" + all_courses[currentIndex].getCourse_no() +
                        " " + all_courses[currentIndex].getCourse_name());
            }
        });

        //get the view of the courseDetailButton
        courseDetailButton = (Button) v.findViewById(R.id.course_detail_button);
        courseDetailButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {

                //Get the view of the texView inside the ScrollView
                courseListTextView = (TextView) v.findViewById(R.id.courseList_text_view);

                //Read table rows calling the method readCourses
                courseModalArrayList = new CourseBaseHelper(context).readCourses();

                //Read the content of courseModalArrayList and load into courseListTextView
                String allCourses = "";
                for (Course course : courseModalArrayList) {
                    allCourses += course.toString();
                }

                //set the content of courseListTextView
                courseListTextView.setText(allCourses);
            }
        }));

        return v;
    }
}


