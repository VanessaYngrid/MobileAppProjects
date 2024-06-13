package com.example.facultyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.facultyproject.model.Faculty;

public class FacultyActivity extends AppCompatActivity {

    private EditText facultyIdEditText;
    private EditText facultyFNameEditText;
    private EditText facultyLNameEditText;
    private EditText facultySalaryEditText;
    private EditText facultyRateBonusEditText;
    private TextView facultyAmountBonusTextView;
    private Button updateDisplayFacultyButton;
    private int facultyIdRetrieve;
    private String facultyFNameRetrieve;
    private String facultyLNameRetrieve;
    private double facultySalaryRetrieve;
    private double facultyRateBonusRetrieve;
    private static String EXTRA_FACULTY_ID="com.example.facultyproject.f_Id";
    private static String EXTRA_FACULTY_FNAME="com.example.facultyproject.f_Fname";
    private static String EXTRA_FACULTY_LNAME="com.example.facultyproject.f_Lname";
    private static String EXTRA_FACULTY_SALARY="com.example.facultyproject.f_Salary";
    private static String EXTRA_FACULTY_RATE_BONUS="com.example.facultyproject.f_bonus";

    public static Intent newIntent(Context packageContext, int f_Id, String f_Fname, String f_Lname, double f_Salary, double f_bonus)
    {
        Intent intent = new Intent(packageContext, FacultyActivity.class);
        intent.putExtra(EXTRA_FACULTY_ID,f_Id);
        intent.putExtra(EXTRA_FACULTY_FNAME,f_Fname);
        intent.putExtra(EXTRA_FACULTY_LNAME,f_Lname);
        intent.putExtra(EXTRA_FACULTY_SALARY,f_Salary);
        intent.putExtra(EXTRA_FACULTY_RATE_BONUS,f_bonus);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faculty);

        facultyIdRetrieve = getIntent().getIntExtra(EXTRA_FACULTY_ID,0 );
        facultyFNameRetrieve = getIntent().getStringExtra(EXTRA_FACULTY_FNAME);
        facultyLNameRetrieve = getIntent().getStringExtra(EXTRA_FACULTY_LNAME);
        facultySalaryRetrieve = getIntent().getDoubleExtra(EXTRA_FACULTY_SALARY, 0.0);
        facultyRateBonusRetrieve = getIntent().getDoubleExtra(EXTRA_FACULTY_RATE_BONUS, 0.0);

        //get the view of courseIdEditText
        facultyIdEditText= (EditText) findViewById(R.id.faculty_id_edit_text);
        facultyIdEditText.setText(facultyIdRetrieve + ""); //Add " " to convert to string

        facultyFNameEditText= (EditText) findViewById(R.id.faculty_fname_edit_text);
        facultyFNameEditText.setText(facultyFNameRetrieve);

        facultyLNameEditText= (EditText) findViewById(R.id.faculty_lname_edit_text);
        facultyLNameEditText.setText(facultyLNameRetrieve);

        facultySalaryEditText= (EditText) findViewById(R.id.faculty_salary_edit_text);
        facultySalaryEditText.setText(facultySalaryRetrieve + ""); //Add " " to convert to string

        facultyRateBonusEditText= (EditText) findViewById(R.id.faculty_rate_bonus_edit_text);
        facultyRateBonusEditText.setText(facultyRateBonusRetrieve + ""); //Add " " to convert to string

        facultyAmountBonusTextView= (TextView) findViewById(R.id.f_amount_bonus2_text_view);

        updateDisplayFacultyButton= (Button) findViewById(R.id.update_faculty_button);
        updateDisplayFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send or coding parameters the update course info to Parent Activity
                int facultyId = Integer.parseInt(facultyIdEditText.getText().toString());
                String facultyFName = facultyFNameEditText.getText().toString();
                String facultyLName = facultyLNameEditText.getText().toString();
                double facultySalary = Double.parseDouble(facultySalaryEditText.getText().toString());
                double facultyRateBonus = Double.parseDouble(facultyRateBonusEditText.getText().toString());

                Faculty faculty = new Faculty(facultyId, facultyFName, facultyLName, facultySalary, facultyRateBonus);

                double bonus = faculty.calculate_Bonus();
                facultyAmountBonusTextView.setText(bonus +""); //to convert to string

                // Guarda los datos actualizados para ser enviados de vuelta a MainActivity
                setFacultyUpdateResult(facultyId, facultyFName, facultyLName, facultySalary, facultyRateBonus);
            }
        });
    }

    private void setFacultyUpdateResult(int f_Id, String f_Fname, String f_Lname, double f_Salary, double f_bonus)
    {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_FACULTY_ID,f_Id);
        dataIntent.putExtra(EXTRA_FACULTY_FNAME,f_Fname);
        dataIntent.putExtra(EXTRA_FACULTY_LNAME,f_Lname);
        dataIntent.putExtra(EXTRA_FACULTY_SALARY,f_Salary);
        dataIntent.putExtra(EXTRA_FACULTY_RATE_BONUS,f_bonus);
        setResult(RESULT_OK, dataIntent);
    }

    public static Faculty decodeMessageFacultyUpdateResult(Intent resultIntent)
    {
        Faculty facultyUpdatedInfo = new Faculty();
        facultyUpdatedInfo.setF_Id(resultIntent.getIntExtra(EXTRA_FACULTY_ID,0));
        facultyUpdatedInfo.setF_Fname(resultIntent.getStringExtra(EXTRA_FACULTY_FNAME));
        facultyUpdatedInfo.setF_Lname(resultIntent.getStringExtra(EXTRA_FACULTY_LNAME));
        facultyUpdatedInfo.setF_Salary(resultIntent.getDoubleExtra(EXTRA_FACULTY_SALARY, 0.0));
        facultyUpdatedInfo.setF_bonus(resultIntent.getDoubleExtra(EXTRA_FACULTY_RATE_BONUS, 0.0));

        return facultyUpdatedInfo;
    }
}