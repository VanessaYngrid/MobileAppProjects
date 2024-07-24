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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginUserButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //Instantiate and reference Object FirebaseAuth
        auth = FirebaseAuth.getInstance(); //Get the instance of FireBaseAuth

        //Get the resources
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        loginUserButton = (Button) findViewById(R.id.login_user_button);
        loginUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check the email and password from Firebase database
                String email_txt = emailEditText.getText().toString();
                String password_txt = passwordEditText.getText().toString();

                login(email_txt, password_txt);

            }
        });
    }

    private void login(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}