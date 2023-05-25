package com.example.studycardcreator.ui.theme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button checkButton;
    private TextView subjectTextView;
    private TextView questionTextView;
    private EditText answerEditText;

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
        checkButton = findViewById(R.id.button_check);
        subjectTextView = findViewById(R.id.textview_subject);
        questionTextView = findViewById(R.id.textview_question);
        answerEditText = findViewById(R.id.edittext_answer);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = answerEditText.getText().toString();
                String correctAnswer = currentFlashcard.getAnswer();
                if (userAnswer.equals(correctAnswer)) {
                    Toast.makeText(StudyFlashcardActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudyFlashcardActivity.this, "Incorrect! The correct answer is: " + correctAnswer, Toast.LENGTH_SHORT).show();
                }
                answerEditText.setText("");
                showNextFlashcard();
            }
        });

        showNextFlashcard();
    }

    private void showNextFlashcard() {
        if (flashcardIterator.hasNext()) {
            currentFlashcard = flashcardIterator.next();
            subjectTextView.setText(currentFlashcard.getSubject());
            questionTextView.setText(currentFlashcard.getQuestion());
        } else {
            Toast.makeText(this, "No more flashcards!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
