package com.example.studycardcreator;

public class Flashcard {
    private String subject;
    private String question;
    private String answer;

    public Flashcard(String subject, String question, String answer) {
        this.subject = subject;
        this.question = question;
        this.answer = answer;
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
}
