package com.example.studycardcreator;

import java.util.Objects;
import java.util.UUID;

public class Flashcard {

    private String id;
    private String subject;
    private String question;
    private String answer;
    private boolean selected;

    private long timestamp;


    public Flashcard(String subject, String question, String answer) {
        this.subject = subject;
        this.question = question;
        this.answer = answer;
        this.selected = false;
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSubject() {
        return subject;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getTimestamp() {
        return timestamp;
    }



    @Override
    public String toString() {
        return "Subject: " + subject + "\nQuestion: " + question + "\nAnswer: " + answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(subject, flashcard.subject) &&
                Objects.equals(question, flashcard.question) &&
                Objects.equals(answer, flashcard.answer);
    }


    @Override
    public int hashCode() {
        return Objects.hash(subject, question, answer, selected);
    }
}
