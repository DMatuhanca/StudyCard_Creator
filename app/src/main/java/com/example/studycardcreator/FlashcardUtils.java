package com.example.studycardcreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FlashcardUtils {

    public static void sortFlashcardsBySubject(List<Flashcard> flashcards) {
        flashcards.sort((fc1, fc2) -> fc1.getSubject().compareToIgnoreCase(fc2.getSubject()));
    }

    public static void sortFlashcardsByLatest(List<Flashcard> flashcards) {
        flashcards.sort((fc1, fc2) -> Long.compare(fc2.getTimestamp(), fc1.getTimestamp()));
    }
    public static List<Flashcard> filterFlashcardsBySelectedSubjects(List<Flashcard> flashcards, List<String> selectedSubjects) {
        List<Flashcard> filteredFlashcards = new ArrayList<>();

        if (selectedSubjects.isEmpty()) {
            return new ArrayList<>(flashcards);
        }

        for (Flashcard flashcard : flashcards) {
            if (selectedSubjects.contains(flashcard.getSubject())) {
                filteredFlashcards.add(flashcard);
            }
        }

        return filteredFlashcards;
    }
}

