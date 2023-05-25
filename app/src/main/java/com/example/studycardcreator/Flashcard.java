package com.example.studycardcreator;

public class Flashcard {
    private String subject;
    private String question;
    private String answer;
    private boolean selected;

    public Flashcard(String subject, String question, String answer) {
        this.subject = subject;
        this.question = question;
        this.answer = answer;
        this.selected = false;
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

    @Override
    public String toString() {
        return "Subject: " + subject + "\nQuestion: " + question + "\nAnswer: " + answer;
    }
}
