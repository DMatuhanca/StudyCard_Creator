package com.example.studycardcreator.ui.theme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studycardcreator.Flashcard;
import com.example.studycardcreator.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ViewFlashcardActivity extends AppCompatActivity {
    private final List<Flashcard> flashcards = new ArrayList<>();
    private ListView flashcardListView;
    private ArrayAdapter<Flashcard> arrayAdapter;
    private SharedPreferences sharedPreferences;
    private Button deleteSelectedButton;
    private Button deleteAllButton;

    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flashcards);

        flashcardListView = findViewById(R.id.flashcard_list_view);
        deleteSelectedButton = findViewById(R.id.delete_selected_button);
        deleteAllButton = findViewById(R.id.delete_all_button);
        sharedPreferences = getSharedPreferences("flashcards", MODE_PRIVATE);

        retrieveFlashcards();
        setupListView();

        deleteSelectedButton.setOnClickListener(v -> deleteSelectedFlashcards());
        deleteAllButton.setOnClickListener(v -> showDeleteAllConfirmationDialog());

        exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> finish());
    }

    private void retrieveFlashcards() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Gson gson = new Gson();
            Flashcard flashcard = gson.fromJson(entry.getValue().toString(), Flashcard.class);
            flashcards.add(flashcard);
        }
    }

    private void setupListView() {
        arrayAdapter = new ArrayAdapter<Flashcard>(this, R.layout.flashcard_view_item, flashcards) {

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.flashcard_view_item, parent, false);
                }

                TextView textViewSubject = convertView.findViewById(R.id.text_view_subject);
                TextView textViewQuestion = convertView.findViewById(R.id.text_view_question);
                TextView textViewAnswer = convertView.findViewById(R.id.textView_answer);
                CheckBox checkBox = convertView.findViewById(R.id.checkbox);

                final Flashcard flashcard = flashcards.get(position);
                textViewSubject.setText(flashcard.getSubject());
                textViewQuestion.setText(flashcard.getQuestion());
                textViewAnswer.setText(flashcard.getAnswer());

                checkBox.setChecked(flashcard.isSelected());
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    flashcard.setSelected(isChecked);
                });

                return convertView;
            }
        };

        flashcardListView.setAdapter(arrayAdapter);
    }


    private void deleteSelectedFlashcards() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Selected Flashcards")
                .setMessage("Are you sure you want to delete the selected flashcards?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    List<Flashcard> selectedFlashcards = new ArrayList<>();

                    for (Flashcard flashcard : flashcards) {
                        if (flashcard.isSelected()) {
                            editor.remove(flashcard.getSubject());
                            selectedFlashcards.add(flashcard);
                        }
                    }

                    flashcards.removeAll(selectedFlashcards);

                    editor.apply();
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(ViewFlashcardActivity.this, "Selected flashcards deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }



    private void showDeleteAllConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Flashcards")
                .setMessage("Are you sure you want to delete all flashcards?")
                .setPositiveButton("Delete All", (dialog, which) -> {
                    deleteAllFlashcards();
                    flashcards.clear();
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(ViewFlashcardActivity.this, "All flashcards deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteAllFlashcards() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
