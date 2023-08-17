package com.example.studycardcreator.ui.theme;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studycardcreator.Flashcard;
import com.example.studycardcreator.FlashcardAdapter;
import com.example.studycardcreator.FlashcardUtils;
import com.example.studycardcreator.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ViewFlashcardActivity extends AppCompatActivity {
    public static List<Flashcard> flashcard;
    private static final List<Flashcard> flashcards = new ArrayList<>();
    private RecyclerView recyclerView;
    private static FlashcardAdapter adapter;
    private SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flashcards);

        recyclerView = findViewById(R.id.flashcard_list_view);
        TextView deleteSelectedButton = findViewById(R.id.delete_selected_button);
        TextView deleteAllButton = findViewById(R.id.delete_all_button);
        sharedPreferences = getSharedPreferences("flashcards", MODE_PRIVATE);

        setupRecyclerView();
        retrieveFlashcards();

        deleteSelectedButton.setOnClickListener(v -> deleteSelectedFlashcards());
        deleteAllButton.setOnClickListener(v -> showDeleteAllConfirmationDialog());

        TextView exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> finish());


    }


    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FlashcardAdapter(flashcards, this);

        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void retrieveFlashcards() {
        new Thread(() -> {
            Map<String, ?> allEntries = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Gson gson = new Gson();
                Flashcard flashcard = gson.fromJson(entry.getValue().toString(), Flashcard.class);
                flashcards.add(flashcard);
            }

            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }

    @SuppressLint("NotifyDataSetChanged")
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
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ViewFlashcardActivity.this, "Selected flashcards deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @SuppressLint("NotifyDataSetChanged")

    private void showDeleteAllConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Flashcards")
                .setMessage("Are you sure you want to delete all flashcards?")
                .setPositiveButton("Delete All", (dialog, which) -> {
                    deleteAllFlashcards();
                    flashcards.clear();
                    adapter.notifyDataSetChanged();
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

    @SuppressLint("NotifyDataSetChanged")

    public void editFlashcard(Flashcard flashcard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_flashcard, null);
        final EditText inputSubject = viewInflated.findViewById(R.id.input_subject);
        final EditText inputQuestion = viewInflated.findViewById(R.id.input_question);
        final EditText inputAnswer = viewInflated.findViewById(R.id.input_answer);

        inputSubject.setText(flashcard.getSubject());
        inputQuestion.setText(flashcard.getQuestion());
        inputAnswer.setText(flashcard.getAnswer());

        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String oldSubject = flashcard.getSubject();

            String newSubject = inputSubject.getText().toString();
            String newQuestion = inputQuestion.getText().toString();
            String newAnswer = inputAnswer.getText().toString();

            flashcard.setSubject(newSubject);
            flashcard.setQuestion(newQuestion);
            flashcard.setAnswer(newAnswer);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (!oldSubject.equals(newSubject)) {
                editor.remove(oldSubject);
            }

            Gson gson = new Gson();
            String flashcardJson = gson.toJson(flashcard);
            editor.putString(flashcard.getSubject(), flashcardJson);
            editor.apply();

            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @SuppressLint("NotifyDataSetChanged")

    public void sortFlashcardsBySubject() {
        FlashcardUtils.sortFlashcardsBySubject(flashcards);
        adapter.notifyDataSetChanged();
        Toast.makeText(ViewFlashcardActivity.this, "Flashcards sorted by subject", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")

    public void sortFlashcardsByLatest() {
        FlashcardUtils.sortFlashcardsByLatest(flashcards);
        adapter.notifyDataSetChanged();
        Toast.makeText(ViewFlashcardActivity.this, "Flashcards sorted by latest", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flashcards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == com.example.studycardcreator.R.id.action_sort_subject) {
            sortFlashcardsBySubject();
            return true;
        }
        if (item.getItemId() == com.example.studycardcreator.R.id.action_sort_latest) {
            sortFlashcardsByLatest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
