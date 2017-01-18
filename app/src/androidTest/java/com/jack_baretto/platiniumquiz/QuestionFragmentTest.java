package com.jack_baretto.platiniumquiz;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;

/**
 *  Test of {@link QuestionFragment}.
 * Created by GGI on 12/01/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuestionFragmentTest {

    @Rule
    public ActivityTestRule<QuestionActivity> activityRule = new ActivityTestRule<>(QuestionActivity.class);

    /**
     * Check if the button to go to the next question is available.
     */
    @Test
    public void hasNextButton(){
        Espresso.onView(ViewMatchers.withId(R.id.next)).check(ViewAssertions.matches(isEnabled()));
    }
}
