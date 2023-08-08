package com.example.studycardcreator.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.studycardcreator.R;
import android.content.Intent;
import android.view.View;
import com.google.android.material.card.MaterialCardView; // Import MaterialCardView

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView createCard = findViewById(R.id.card_create_flashcard);
        MaterialCardView viewCard = findViewById(R.id.card_view_flashcards);
        MaterialCardView studyCard = findViewById(R.id.card_study_flashcards);

        createCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateFlashcardActivity.class);
                startActivity(intent);
            }
        });

        viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewFlashcardActivity.class);
                startActivity(intent);
            }
        });

        studyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudyFlashcardActivity.class);
                startActivity(intent);
            }
        });
    }
}
