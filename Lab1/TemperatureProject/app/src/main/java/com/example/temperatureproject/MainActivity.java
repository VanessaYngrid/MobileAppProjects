package com.example.temperatureproject;

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

import com.example.temperatureproject.model.TemperatureConversion;

public class MainActivity extends AppCompatActivity {

    private EditText inputFahrenheitTempEditText;
    private TextView celsiusTempTextView;
    private Button convertTempButton;


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

        convertTempButton = (Button) findViewById(R.id.convert_temperature_button);
        convertTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double fahrenheitTemp;

                //get the view of inputXValueEditText
                inputFahrenheitTempEditText = (EditText) findViewById(R.id.input_temperature_F_edit_text);
                fahrenheitTemp = Double.parseDouble(inputFahrenheitTempEditText.getText().toString());

                TemperatureConversion tempConversion = new TemperatureConversion(fahrenheitTemp);

                //Output
                celsiusTempTextView = (TextView) findViewById(R.id.temperature_celsius_text_view);
                String celsiusTemp = tempConversion.conversionString();
                celsiusTempTextView.setText(celsiusTemp);

            }

        });


    }
}