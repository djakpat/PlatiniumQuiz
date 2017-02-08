package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.baretto.mcq.datamodel.internals.MCQ;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.not;

/**
 * Test of {@link QuestionFragment}.
 * Created by GGI on 12/01/2017.
 */
@RunWith(AndroidJUnit4.class)
public class QuestionActivityTest {

    @Rule
    public ActivityTestRule<QuestionActivity> questionActivityRule = new ActivityTestRule<>(QuestionActivity.class, true, false);

    /**
     * Creation of a Question Activity with one question.
     */
    @Before
    public void before() {
        Intent startIntent = new Intent();
        startIntent.putExtra("Mcq", new MCQ(1));
        questionActivityRule.launchActivity(startIntent);
    }

    /**
     * Check if the button to go to the next question is available.
     */
    @Test
    public void hasNextButton() {
        Espresso.onView(ViewMatchers.withId(R.id.next)).check(ViewAssertions.matches(isEnabled()));
    }

    /**
     * Check if the button to go to the next question is available.
     */
    @Test
    public void hasPreviousButton() {
        Espresso.onView(ViewMatchers.withId(R.id.previous)).check(ViewAssertions.matches(not(isEnabled())));
    }

    /**
     * Check if the button to go to the next question is available.
     */
    @Test
    public void hasViewResultButton() {
        Espresso.onView(ViewMatchers.withId(R.id.result)).check(ViewAssertions.matches(isEnabled()));
    }
}
