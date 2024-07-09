package com.example.coursesproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class CourseContextActivity extends AppCompatActivity {

    private TextView courseContentTextView;
    private Button callCourseWebServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_context);

        courseContentTextView = findViewById(R.id.courseContent_textView);
        callCourseWebServiceButton = findViewById(R.id.callCourseWebService_button);

        callCourseWebServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = "http://api.zippopotam.us/us/98121";

                new AsyncHttpClient().get(uri, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse = new String(responseBody);
                        courseContentTextView.setText(strResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        courseContentTextView.setText("Error in calling Web Service");
                    }
                });



            }
        });



    }
}