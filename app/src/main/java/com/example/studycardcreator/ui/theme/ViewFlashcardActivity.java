package com.example.studycardcreator.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.studycardcreator.R;
import com.example.studycardcreator.Flashcard;
import android.widget.LinearLayout;

public class ViewFlashcardActivity extends AppCompatActivity {


    private List<Flashcard> flashcards = new ArrayList<>();
    private TextView flashcardListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flashcards);

        flashcardListTextView = findViewById(R.id.flashcard_textview);

        LinearLayout flashcardsLayout = findViewById(R.id.flashcards_layout);

        SharedPreferences sharedPreferences = getSharedPreferences("flashcards", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Gson gson = new Gson();
            Flashcard flashcard = gson.fromJson(entry.getValue().toString(), Flashcard.class);
            flashcards.add(flashcard);
        }


        StringBuilder sb = new StringBuilder();
        for (Flashcard flashcard : flashcards) {
            sb.append("Subject: ").append(flashcard.getSubject()).append("\n");
            sb.append("Question: ").append(flashcard.getQuestion()).append("\n");
            sb.append("Answer: ").append(flashcard.getAnswer()).append("\n\n");
        }

        flashcardListTextView.setText(sb.toString());
    }
}