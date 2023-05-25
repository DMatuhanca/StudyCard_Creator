package com.example.studycardcreator.ui.theme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studycardcreator.R;
import com.example.studycardcreator.Flashcard;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudyFlashcardActivity extends AppCompatActivity {
    private List<Flashcard> flashcards = new ArrayList<>();
    private Iterator<Flashcard> flashcardIterator;
    private Flashcard currentFlashcard;
    private Button exitButton;
    private Button showAnswerButton;
    private Button nextButton;
    private TextView questionTextView;
    private TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_flashcard);

        SharedPreferences sharedPreferences = getSharedPreferences("flashcards", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Gson gson = new Gson();
            Flashcard flashcard = gson.fromJson(entry.getValue().toString(), Flashcard.class);
            flashcards.add(flashcard);
        }

        flashcardIterator = flashcards.iterator();

        exitButton = findViewById(R.id.button_exit);
        showAnswerButton = findViewById(R.id.button_show_answer);
        nextButton = findViewById(R.id.button_next);
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
            questionTextView.setText(currentFlashcard.getQuestion());
            answerTextView.setText(currentFlashcard.getAnswer());
            answerTextView.setVisibility(View.GONE);  // Initially hide the answer
        } else {
            Toast.makeText(this, "No more flashcards!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
