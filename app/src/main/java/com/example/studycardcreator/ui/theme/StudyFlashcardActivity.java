package com.example.studycardcreator.ui.theme;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studycardcreator.R;
import com.example.studycardcreator.Flashcard;
import com.google.gson.Gson;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudyFlashcardActivity extends AppCompatActivity {
    private List<Flashcard> flashcards = new ArrayList<>();
    private Iterator<Flashcard> flashcardIterator;
    private Flashcard currentFlashcard;
    private MaterialCardView exitButton;
    private MaterialCardView showAnswerButton;
    private MaterialCardView nextButton;
    private TextView questionTextView;
    private TextView answerTextView;
    private TextView subjectTextView;

    private int getColorForSubject(String subject) {
        final int baseColor = 0xFFAAAAAA;

        int hash = subject.hashCode();
        int red = (hash & 0xFF0000) >> 16;
        int green = (hash & 0x00FF00) >> 8;
        int blue = hash & 0x0000FF;

        red = (red + Color.red(baseColor)) / 2;
        green = (green + Color.green(baseColor)) / 2;
        blue = (blue + Color.blue(baseColor)) / 2;

        return Color.rgb(red, green, blue);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_flashcard);

        subjectTextView = findViewById(R.id.textView_subject);

        SharedPreferences sharedPreferences = getSharedPreferences("flashcards", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Gson gson = new Gson();
            Flashcard flashcard = gson.fromJson(entry.getValue().toString(), Flashcard.class);
            flashcards.add(flashcard);
        }

        flashcardIterator = flashcards.iterator();

        exitButton = findViewById(R.id.card_exit);
        showAnswerButton = findViewById(R.id.card_show_answer);
        nextButton = findViewById(R.id.card_next_flashcard);
        questionTextView = findViewById(R.id.textView_question);
        answerTextView = findViewById(R.id.textView_answer);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextView.setVisibility(View.VISIBLE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextFlashcard();
            }
        });

        showNextFlashcard();
    }

    private void showNextFlashcard() {
        if (flashcardIterator.hasNext()) {
            currentFlashcard = flashcardIterator.next();
            subjectTextView.setText(currentFlashcard.getSubject());

            int color = getColorForSubject(currentFlashcard.getSubject());
            ((LinearLayout) findViewById(R.id.flashcard_layout)).setBackgroundColor(color);

            questionTextView.setText(currentFlashcard.getQuestion());
            answerTextView.setText(currentFlashcard.getAnswer());
            answerTextView.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "No more flashcards!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
