package com.example.studycardcreator;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studycardcreator.ui.theme.ViewFlashcardActivity;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Flashcard> flashcards;
    private ViewFlashcardActivity viewFlashcardActivity;

    public FlashcardAdapter(List<Flashcard> flashcards, ViewFlashcardActivity viewFlashcardActivity) {
        this.flashcards = flashcards;
        this.viewFlashcardActivity = viewFlashcardActivity;
    }

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

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_view_item, parent, false);
        return new FlashcardViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);

        holder.textViewSubject.setText(flashcard.getSubject());
        holder.textViewQuestion.setText(flashcard.getQuestion());
        holder.textViewAnswer.setText(flashcard.getAnswer());
        holder.checkBox.setChecked(flashcard.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> flashcard.setSelected(isChecked));
        holder.editButton.setOnClickListener(v -> viewFlashcardActivity.editFlashcard(flashcard));

        holder.itemView.setBackgroundColor(getColorForSubject(flashcard.getSubject()));
    }



    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSubject;
        TextView textViewQuestion;
        TextView textViewAnswer;
        CheckBox checkBox;
        Button editButton;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSubject = itemView.findViewById(R.id.text_view_subject);
            textViewQuestion = itemView.findViewById(R.id.text_view_question);
            textViewAnswer = itemView.findViewById(R.id.text_view_answer);
            checkBox = itemView.findViewById(R.id.checkbox);
            editButton = itemView.findViewById(R.id.edit_flashcard_button);
        }

    }
}
