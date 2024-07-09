package com.example.employeeproject;

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

import com.example.employeeproject.model.Employee;

public class MainActivity extends AppCompatActivity {

    private TextView employeeIdTextView;
    private TextView employeeNameTextView;
    private TextView employeeSalaryTextView;
    private TextView employeeTotalTaxTextView;
    private Button prevEmployeeButton;
    private Button nextEmployeeButton;
    private Button employeeTotalTaxButton;

    private int currentIndex = 0;

    public static String TAG;

    public static String KEY_INDEX="index";
    private Employee[] all_employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Employee e1 = new Employee(340, "Mendy Anais", 99000.0);
        Employee e2 = new Employee(120, "Vanessa Vargas", 75000.0);
        Employee e3 = new Employee(890, "Eric Wou", 49000.0);
        Employee e4 = new Employee(240, "Ever Bellido", 85000.0);
        Employee e5 = new Employee(123, "Janitza Vilela", 78000.0);

        all_employees = new Employee[]{e1,e2,e3,e4,e5};

        //Retrieve currentIndex
        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        employeeIdTextView = (TextView) findViewById(R.id.employee_id_text_view);
        employeeIdTextView.setText("Employee ID: " + all_employees[currentIndex].getEmp_id());

        employeeNameTextView = (TextView) findViewById(R.id.employee_name_text_view);
        employeeNameTextView.setText("Employee Name: " + all_employees[currentIndex].getEmp_name());

        employeeSalaryTextView = (TextView) findViewById(R.id.employee_salary_text_view);
        employeeSalaryTextView.setText("Employee Salary: " + all_employees[currentIndex].getEmp_salary());

        employeeTotalTaxTextView = (TextView) findViewById(R.id.display_total_tax_text_view);

        prevEmployeeButton = (Button) findViewById(R.id.prev_employee_button);
        nextEmployeeButton = (Button) findViewById(R.id.next_employee_button);
        employeeTotalTaxButton = (Button) findViewById(R.id.employee_tax_button);

        prevEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = ((currentIndex - 1) + all_employees.length) % all_employees.length;

                employeeIdTextView.setText("Employee ID :" + all_employees[currentIndex].getEmp_id());
                employeeNameTextView.setText("Employee Name :" + all_employees[currentIndex].getEmp_name());
                employeeSalaryTextView.setText("Employee Salary :" + all_employees[currentIndex].getEmp_salary());

            }
        });

        nextEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex = (currentIndex + 1) % all_employees.length;

                employeeIdTextView.setText("Employee ID: " + all_employees[currentIndex].getEmp_id());
                employeeNameTextView.setText("Employee Name: " + all_employees[currentIndex].getEmp_name());
                employeeSalaryTextView.setText("Employee Salary: " + all_employees[currentIndex].getEmp_salary());
            }
        });

        employeeTotalTaxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                employeeTotalTaxTextView.setText("Total Employee Tax is: " + all_employees[currentIndex].calculateTotalTax());

                //Use Toast class
                Toast.makeText(MainActivity.this,"Total Employee Tax is " + all_employees[currentIndex].calculateTotalTax(), Toast.LENGTH_SHORT).show();
            }
        });

    }

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