package com.example.coursesproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerUserButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //Instantiate and reference Object FirebaseAuth
        auth = FirebaseAuth.getInstance(); //Get the instance of FireBaseAuth

        //Get the resources
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        registerUserButton = (Button) findViewById(R.id.register_user_button);
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adding email and password to Cloud Google Firebase database
                String email_txt = emailEditText.getText().toString();
                String password_txt = passwordEditText.getText().toString();

                if(TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt))
                {
                    Toast.makeText(RegisterActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                }
                else if(password_txt.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(email_txt, password_txt);
                }
            }
        });
    }

    private void register(String email, String password)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "Register is successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}