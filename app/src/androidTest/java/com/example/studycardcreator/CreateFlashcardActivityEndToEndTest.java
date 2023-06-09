package com.example.studycardcreator;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.NoActivityResumedException;

import com.example.studycardcreator.ui.theme.CreateFlashcardActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;

@RunWith(AndroidJUnit4.class)
public class CreateFlashcardActivityEndToEndTest {

    @Rule
    public ActivityScenarioRule<CreateFlashcardActivity> activityRule = new ActivityScenarioRule<>(CreateFlashcardActivity.class);

    private ActivityScenario<CreateFlashcardActivity> scenario;

    @Before
    public void before() {
        Intents.init();
        scenario = activityRule.getScenario();
    }

    @After
    public void after() {
        Intents.release();
    }

    @Test
    public void whenActivityStarts_thenDialogIsDisplayed() {
        onView(withId(R.id.editTextSubject)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextAnswer)).check(matches(isDisplayed()));
    }

    @Test
    public void whenCreateFlashcard_thenActivityFinishes() {
        onView(withId(R.id.editTextSubject)).perform(typeText("Subject1"));
        onView(withId(R.id.editTextQuestion)).perform(typeText("Question1"));
        onView(withId(R.id.editTextAnswer)).perform(typeText("Answer1"));
        onView(withText("Create")).perform(click());

        assertThrows(NoActivityResumedException.class, () -> onView(withId(R.id.editTextSubject)).check(matches(isDisplayed())));
    }

    @Test
    public void createFlashcardTest() {
        String subjectInput = "TestSubject";
        String questionInput = "TestQuestion";
        String answerInput = "TestAnswer";

        onView(withId(R.id.editTextSubject)).perform(typeText(subjectInput));
        onView(withId(R.id.editTextQuestion)).perform(typeText(questionInput));
        onView(withId(R.id.editTextAnswer)).perform(typeText(answerInput));
        closeSoftKeyboard();

        onView(withText("Create")).perform(click());

        activityRule.getScenario().onActivity(activity -> {
            assertTrue(activity.isFinishing());
        });
    }
}
