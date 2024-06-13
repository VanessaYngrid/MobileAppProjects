package com.example.mathoperationproject;

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

import com.example.mathoperationproject.model.MathOperations;

public class MainActivity extends AppCompatActivity {

    private EditText inputXValueEditText;
    private EditText inputYValueEditText;
    private TextView sumTextView;
    private TextView subtTextView;
    private TextView multTextView;
    private TextView divTextView;
    private Button calculateOpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get the view of the button
        calculateOpButton = (Button) findViewById(R.id.calculate_button);
        calculateOpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double x=0.0;
                double y=0.0;
                //get the view of inputXValueEditText
                inputXValueEditText = (EditText) findViewById(R.id.input_x_edit_text);
                x = Double.parseDouble(inputXValueEditText.getText().toString());

                inputYValueEditText = (EditText) findViewById(R.id.input_y_edit_text);
                y = Double.parseDouble(inputYValueEditText.getText().toString());

                MathOperations mathOp = new MathOperations(x,y);

                sumTextView = (TextView) findViewById(R.id.sum_operation_text_view);
                String sum = mathOp.sumString();
                sumTextView.setText(sum);

                subtTextView = (TextView) findViewById(R.id.subtraction_operation_text_view);
                String subtraction = mathOp.subtractionString();
                subtTextView.setText(subtraction);

                multTextView = (TextView) findViewById(R.id.multiplication_operation_text_view);
                String multiplication = mathOp.multiplicationString();
                multTextView.setText(multiplication);

                divTextView = (TextView) findViewById(R.id.division_operation_text_view);
                String division = mathOp.divisionString();
                divTextView.setText(division);
            }

        });
    }
}