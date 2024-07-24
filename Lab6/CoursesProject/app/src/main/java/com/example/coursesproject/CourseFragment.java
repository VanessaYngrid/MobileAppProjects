package com.example.coursesproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.coursesproject.database.CourseBaseHelper;
import com.example.coursesproject.model.Course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseFragment extends Fragment {

    //All widgest will be managed by the fragment
    private TextView courseTextView;
    private TextView courseTotalFeesTextView;
    private TextView courseListTextView;
    private Button courseTotalFeesButton;
    private Button courseNextButton;
    private Button courseLinkButton;

    private Button courseDetailButton;
    private Button startServiceButton;
    private Button stopServiceButton;
    private int currentIndex = 0;

    private Button logoutButton;
    private Button writeToFirebaseButton;
    private Button readFromFirebaseButton;

    private Course[] all_courses;
    private ArrayList<Course> courseModalArrayList;
    private Context context;

    //Lab 6
    DatabaseReference databaseRef;
    Map<String, Object> courseHm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Lab 6
        databaseRef = FirebaseDatabase.getInstance().getReference();

        //Fragment will receive callback from Fragment manager
        setHasOptionsMenu(true); //called onCreate

        Course.credits = 3;

        Course c1 = new Course("MIS 101", "Intro. to Info. Systems", 140);
        Course c2 = new Course("MIS 301", "Systems Analysis", 35);
        Course c3 = new Course("MIS 441", "Database Management", 12);
        Course c4 = new Course("CS 155", "Programming in C++", 90);
        Course c5 = new Course("MIS 451", "Web-Based Systems", 30);
        Course c6 = new Course("MIS 551", "Advanced Web", 30);
        Course c7 = new Course("MIS 651", "Advanced Java", 30);

        all_courses = new Course[]{c1, c2, c3, c4, c5, c6, c7};

        //Lab 6
        courseHm = new HashMap<>();
        courseHm.put(c1.getCourse_no(), c1);
        courseHm.put(c2.getCourse_no(), c2);
        courseHm.put(c3.getCourse_no(), c3);
        courseHm.put(c4.getCourse_no(), c4);
        courseHm.put(c5.getCourse_no(), c5);
        courseHm.put(c6.getCourse_no(), c6);
        courseHm.put(c7.getCourse_no(), c7);

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

        Toolbar courseToolbar = (Toolbar) v.findViewById(R.id.course_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(courseToolbar);

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

        startServiceButton = (Button) v.findViewById(R.id.course_start_service_button);
        startServiceButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {

                //Start the audio MediaPlayer service
                getActivity().startService(new Intent(getActivity(), CourseService.class));


            }
        }));

        stopServiceButton = (Button) v.findViewById(R.id.course_stop_service_button);
        stopServiceButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {

                //Stop the audio MediaPlayer service
                getActivity().stopService(new Intent(getActivity(), CourseService.class));

            }
        }));

        courseLinkButton = (Button) v.findViewById(R.id.course_link_button);
        courseLinkButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {

               //Start intent Browsing Activity
                //Use implicit Intent
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vaniercollege.qc.ca/"));
                startActivity(intent);
            }
        }));

        //Lab 6
        logoutButton = (Button) v.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(activity, "Logout successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), StartActivity.class));
                //getActivity because it is a fragment
            }
        }));

        writeToFirebaseButton = (Button) v.findViewById(R.id.write_to_firebase_button);
        writeToFirebaseButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {

                /* To add the firs element of the Array
                databaseRef.child("Courses").child(all_courses[0].getCourse_no()).setValue(all_courses[0]);
                Toast.makeText(activity, "Added to Firebase", Toast.LENGTH_SHORT).show();*/

                //To store all collection (All the elements)
                databaseRef.child("Courses").updateChildren(courseHm);
                Toast.makeText(activity, "Map added to Firebase", Toast.LENGTH_SHORT).show();
            }
        }));

        readFromFirebaseButton = (Button) v.findViewById(R.id.read_from_firebase_button);
        readFromFirebaseButton.setOnClickListener((new View.OnClickListener() {
            public void onClick(View view) {
                //Read from Firebase database
                databaseRef.child("Courses").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String allCourses = "";

                        for(DataSnapshot ss:snapshot.getChildren())
                        {
                            allCourses += ss.getValue(Course.class).toString() +"\n";
                        }

                        courseListTextView = (TextView) v.findViewById(R.id.courseList_text_view);
                        courseListTextView.setText(allCourses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }));


        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Inflate course_menu
        inflater.inflate(R.menu.menu_course, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.courseoption1) {
            Toast.makeText(getActivity(), "Course Option 1 ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), CourseMapsActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.courseoption2) {
            Toast.makeText(getActivity(), "Course Option 2 ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), CourseContextActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.courseoption3) {
            Toast.makeText(getActivity(), "Course Option 3 ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), CourseOperationActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.courseoption4)
            Toast.makeText(getActivity(), "Course Option 4 " , Toast.LENGTH_SHORT).show();
        else if(item.getItemId()==R.id.courseoption5)
            Toast.makeText(getActivity(), "Course Option 5 " , Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}


