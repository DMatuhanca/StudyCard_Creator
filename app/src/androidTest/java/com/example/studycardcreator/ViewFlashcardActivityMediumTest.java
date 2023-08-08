package com.example.studycardcreator;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.studycardcreator.ui.theme.ViewFlashcardActivity;
import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ViewFlashcardActivityMediumTest {

    @Rule
    public ActivityScenarioRule<ViewFlashcardActivity> activityScenarioRule =
            new ActivityScenarioRule<>(ViewFlashcardActivity.class);

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified ID.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }


    @Test
    public void testEditFlashcard() {
        String subject = "English";
        String question = "How are you?";
        String answer = "Good";

        String newSubject = "Physics";
        String newQuestion = "What is the speed of light?";
        String newAnswer = "299,792 km/s";

        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("flashcards", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Flashcard flashcard = new Flashcard(subject, question, answer);
        Gson gson = new Gson();
        String flashcardJson = gson.toJson(flashcard);
        editor.putString(subject, flashcardJson);
        editor.apply();

        onView(withId(R.id.flashcard_list_view))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.edit_flashcard_button)));

        onView(withId(R.id.input_subject)).perform(clearText(), typeText(newSubject));
        onView(withId(R.id.input_question)).perform(clearText(), typeText(newQuestion));
        onView(withId(R.id.input_answer)).perform(clearText(), typeText(newAnswer), closeSoftKeyboard());

        onView(withText("OK")).perform(click());


        String newFlashcardJson = sharedPreferences.getString(newSubject, null);
        if (newFlashcardJson != null) {
            Flashcard actualFlashcard = gson.fromJson(newFlashcardJson, Flashcard.class);
            Flashcard expectedFlashcard = new Flashcard(newSubject, newQuestion, newAnswer);
            assertEquals(expectedFlashcard, actualFlashcard);
        }
    }

}


