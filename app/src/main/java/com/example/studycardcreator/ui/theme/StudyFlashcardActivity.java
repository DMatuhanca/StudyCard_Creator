package com.example.studycardcreator.ui.theme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studycardcreator.Flashcard;
import com.example.studycardcreator.R;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudyFlashcardActivity extends AppCompatActivity {
    private final List<Flashcard> flashcards = new ArrayList<>();
    private Iterator<Flashcard> flashcardIterator;
    private TextView questionTextView;
    private TextView answerTextView;
    private TextView subjectTextView;
    private final ArrayList<String> subjectList = new ArrayList<>();
    private final ArrayList<String> selectedSubjects = new ArrayList<>();


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
            if (!subjectList.contains(flashcard.getSubject())) {
                subjectList.add(flashcard.getSubject());
            }
        }


        flashcardIterator = flashcards.iterator();

        MaterialCardView exitButton = findViewById(R.id.card_exit);
        MaterialCardView showAnswerButton = findViewById(R.id.card_show_answer);
        MaterialCardView nextButton = findViewById(R.id.card_next_flashcard);
        questionTextView = findViewById(R.id.textView_question);
        answerTextView = findViewById(R.id.textView_answer);

        exitButton.setOnClickListener(v -> finish());

        showAnswerButton.setOnClickListener(v -> answerTextView.setVisibility(View.VISIBLE));

        nextButton.setOnClickListener(v -> showNextFlashcard());

        showNextFlashcard();

        Button btnShowOnly = findViewById(R.id.btn_show_only);
        btnShowOnly.setOnClickListener(v -> showSubjectSelectionDialog());

    }

    private void showNextFlashcard() {
        if (flashcardIterator.hasNext()) {
            Flashcard currentFlashcard = flashcardIterator.next();
            subjectTextView.setText(currentFlashcard.getSubject());

            int color = getColorForSubject(currentFlashcard.getSubject());
            findViewById(R.id.flashcard_layout).setBackgroundColor(color);

            questionTextView.setText(currentFlashcard.getQuestion());
            answerTextView.setText(currentFlashcard.getAnswer());
            answerTextView.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "No more flashcards!", Toast.LENGTH_SHORT).show();
        }
    }


    private void showSubjectSelectionDialog() {
        boolean[] checkedItems = new boolean[subjectList.size()];
        for (int i = 0; i < subjectList.size(); i++) {
            checkedItems[i] = selectedSubjects.contains(subjectList.get(i));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Subjects to Study");
        builder.setMultiChoiceItems(subjectList.toArray(new CharSequence[0]), checkedItems, (dialog, which, isChecked) -> {
            String selectedSubject = subjectList.get(which);
            if (isChecked) {
                selectedSubjects.add(selectedSubject);
            } else {
                selectedSubjects.remove(selectedSubject);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> filterFlashcardsBySelectedSubjects());

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void filterFlashcardsBySelectedSubjects() {
        if (selectedSubjects.isEmpty()) {
            flashcardIterator = flashcards.iterator();
            showNextFlashcard();
            return;
        }

        List<Flashcard> filteredFlashcards = new ArrayList<>();
        for (Flashcard flashcard : flashcards) {
            if (selectedSubjects.contains(flashcard.getSubject())) {
                filteredFlashcards.add(flashcard);
            }
        }

        flashcards.clear();
        flashcards.addAll(filteredFlashcards);
        flashcardIterator = flashcards.iterator();
        showNextFlashcard();
    }




}
