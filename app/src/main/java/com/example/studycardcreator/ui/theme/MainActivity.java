package com.example.studycardcreator.ui.theme;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studycardcreator.R;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView createCard = findViewById(R.id.card_create_flashcard);
        MaterialCardView viewCard = findViewById(R.id.card_view_flashcards);
        MaterialCardView studyCard = findViewById(R.id.card_study_flashcards);


        createCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateFlashcardActivity.class);
            startActivity(intent);
        });

        viewCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewFlashcardActivity.class);
            startActivity(intent);
        });

        studyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudyFlashcardActivity.class);
            startActivity(intent);
        });
    }
}
