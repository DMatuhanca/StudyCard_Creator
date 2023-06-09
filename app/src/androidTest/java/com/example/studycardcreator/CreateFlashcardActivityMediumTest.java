package com.example.studycardcreator;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.studycardcreator.ui.theme.CreateFlashcardActivity;
import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;

@RunWith(AndroidJUnit4.class)
public class CreateFlashcardActivityMediumTest {

    @Rule
    public ActivityScenarioRule<CreateFlashcardActivity> activityRule =
            new ActivityScenarioRule<>(CreateFlashcardActivity.class);

    @Test
    public void testCreateFlashcardAndSave() {
        String subject = "Math";
        String question = "What is 2 + 2?";
        String answer = "4";
        Flashcard expectedFlashcard = new Flashcard(subject, question, answer);

        onView(withId(R.id.editTextSubject)).perform(typeText(subject));
        onView(withId(R.id.editTextQuestion)).perform(typeText(question));
        onView(withId(R.id.editTextAnswer)).perform(typeText(answer), closeSoftKeyboard());
        onView(withText("Create")).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("flashcards", Context.MODE_PRIVATE);

        String flashcardJson = sharedPreferences.getString(subject, "");

        Flashcard actualFlashcard = new Gson().fromJson(flashcardJson, Flashcard.class);

        assertEquals(expectedFlashcard, actualFlashcard);
    }

    @Test
    public void testRefusesToCreateIncompleteFlashcard() {
        String subject = "Math";
        String question = "";
        String answer = "4";

        onView(withId(R.id.editTextSubject)).perform(typeText("UniqueSubject"), closeSoftKeyboard());
        onView(withId(R.id.editTextQuestion)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editTextAnswer)).perform(typeText("4"), closeSoftKeyboard());
        onView(withText("Create")).perform(click());

        ActivityScenario<CreateFlashcardActivity> scenario = activityRule.getScenario();

        scenario.onActivity(activity -> {
            Context context = activity.getApplicationContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences("flashcards", Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();

            String flashcardJson = sharedPreferences.getString(subject, null);

            assertNull(flashcardJson);
        });
    }
}
