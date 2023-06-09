package com.example.studycardcreator;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlashcardTest {

    @Test
    public void testFlashcardCreation() {
        Flashcard flashcard = new Flashcard("Subject1", "Question1", "Answer1");
        assertNotNull(flashcard);
        assertEquals("Subject1", flashcard.getSubject());
        assertEquals("Question1", flashcard.getQuestion());
        assertEquals("Answer1", flashcard.getAnswer());
    }

    @Test
    public void testFlashcardDefaultSelected() {
        Flashcard flashcard = new Flashcard("Subject1", "Question1", "Answer1");
        assertFalse(flashcard.isSelected());
    }

    @Test
    public void testFlashcardSelected() {
        Flashcard flashcard = new Flashcard("Subject1", "Question1", "Answer1");
        flashcard.setSelected(true);
        assertTrue(flashcard.isSelected());
    }

    @Test
    public void testFlashcardToggleSelected() {
        Flashcard flashcard = new Flashcard("Subject1", "Question1", "Answer1");
        flashcard.setSelected(true);
        assertTrue(flashcard.isSelected());
        flashcard.setSelected(false);
        assertFalse(flashcard.isSelected());
    }

    @Test
    public void testFlashcardToString() {
        Flashcard flashcard = new Flashcard("Subject1", "Question1", "Answer1");
        String expected = "Subject: Subject1\nQuestion: Question1\nAnswer: Answer1";
        assertEquals(expected, flashcard.toString());
    }
}
