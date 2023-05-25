package com.example.studycardcreator.ui.theme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studycardcreator.R;

public class CreateFlashcardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);
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
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String subject = subjectEditText.getText().toString();
                        String question = questionEditText.getText().toString();
                        String answer = answerEditText.getText().toString();
                        String message = String.format("Subject: %s\nQuestion: %s\nAnswer: %s", subject, question, answer);
                        Toast.makeText(CreateFlashcardActivity.this, message, Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null);


        builder.create().show();
    }
}
