package com.example.studycardcreator.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.studycardcreator.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createButton = findViewById(R.id.button_create_flashcard);
        Button viewButton = findViewById(R.id.button_view_flashcards);
        Button studyButton = findViewById(R.id.button_study_flashcards);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateFlashcardActivity.class);
                startActivity(intent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewFlashcardActivity.class);
                startActivity(intent);
            }
        });

        studyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudyFlashcardActivity.class);
                startActivity(intent);
            }
        });
    }
}

