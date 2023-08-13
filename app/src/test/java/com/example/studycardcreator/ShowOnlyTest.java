package com.example.studycardcreator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.studycardcreator.ui.theme.ViewFlashcardActivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ShowOnlyTest {
    @Test
    public void testFilterFlashcardsBySelectedSubjects() {
        List<Flashcard> flashcards = new ArrayList<>();
        flashcards.add(new Flashcard("Math", "Question1", "Answer1"));
        flashcards.add(new Flashcard("Physics", "Question2", "Answer2"));
        flashcards.add(new Flashcard("Biology", "Question3", "Answer3"));

        Set<String> selectedSubjects = new HashSet<>();
        selectedSubjects.add("Math");
        selectedSubjects.add("Biology");

        List<Flashcard> filteredFlashcards = FlashcardUtils.filterFlashcardsBySelectedSubjects(flashcards, selectedSubjects);

        assertEquals(2, filteredFlashcards.size());
        assertTrue(filteredFlashcards.contains(new Flashcard("Math", "Question1", "Answer1")));
        assertTrue(filteredFlashcards.contains(new Flashcard("Biology", "Question3", "Answer3")));
    }
}