package com.example.storyproject;

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

import com.example.storyproject.model.Story;

public class MainActivity extends AppCompatActivity {

    private EditText inputNameEditText;
    private EditText inputAgeEditText;
    private EditText inputCityEditText;
    private EditText inputCollegeNameEditText;
    private EditText inputProfessionEditText;
    private EditText inputAnimalTypeEditText;
    private EditText inputPetNameEditText;
    private TextView storyTextView;
    private Button displayStoryButton;


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

        displayStoryButton = (Button) findViewById(R.id.display_story_button);
        displayStoryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                inputNameEditText = (EditText) findViewById(R.id.input_name_edit_text);
                String name = inputNameEditText.getText().toString();
                //declarar las variables afuera (TODAS) - hacer lo mismo para Temperature

                inputAgeEditText = (EditText) findViewById(R.id.input_age_edit_text);
                int age = Integer.parseInt(inputAgeEditText.getText().toString());

                inputCityEditText = (EditText) findViewById(R.id.input_city_edit_text);
                String city = inputCityEditText.getText().toString();

                inputCollegeNameEditText = (EditText) findViewById(R.id.input_college_edit_text);
                String collegeName = inputCollegeNameEditText.getText().toString();

                inputProfessionEditText = (EditText) findViewById(R.id.input_profession_edit_text);
                String profession = inputProfessionEditText.getText().toString();

                inputAnimalTypeEditText = (EditText) findViewById(R.id.input_animal_type_edit_text);
                String animalType = inputAnimalTypeEditText.getText().toString();

                inputPetNameEditText = (EditText) findViewById(R.id.input_pet_name_edit_text);
                String petName = inputPetNameEditText.getText().toString();

                Story story = new Story(name,age,city,collegeName,profession,animalType,petName);

                storyTextView = (TextView) findViewById(R.id.story_text_view);
                String storyText = story.storyString();
                storyTextView.setText(storyText);

            }

        });



    }
}