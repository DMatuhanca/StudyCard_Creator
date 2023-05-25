package com.example.studycardcreator.ui.theme;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studycardcreator.R;
import com.example.studycardcreator.Flashcard;
import com.google.gson.Gson;

public class CreateFlashcardActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);
        sharedPreferences = getSharedPreferences("flashcards", MODE_PRIVATE);
        showCreateFlashcardDialog();
    }


    private void showCreateFlashcardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_flashcard, null);
        builder.setView(dialogView);

        final EditText subjectEditText = dialogView.findViewById(R.id.editTextSubject);
        final EditText questionEditText = dialogView.findViewById(R.id.editTextQuestion);
        final EditText answerEditText = dialogView.findViewById(R.id.editTextAnswer);

        builder.setTitle("Create Flashcard")
                .setPositiveButton("Create", (dialog, id) -> {
                    String subject = subjectEditText.getText().toString();
                    String question = questionEditText.getText().toString();
                    String answer = answerEditText.getText().toString();

                    Flashcard flashcard = new Flashcard(subject, question, answer);
                    saveFlashcard(flashcard);

                    String message = "Flashcard created:\n" + flashcard;
                    Toast.makeText(CreateFlashcardActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void saveFlashcard(Flashcard flashcard) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String flashcardJson = gson.toJson(flashcard);
        editor.putString(flashcard.getSubject(), flashcardJson);
        editor.apply();
    }
}
