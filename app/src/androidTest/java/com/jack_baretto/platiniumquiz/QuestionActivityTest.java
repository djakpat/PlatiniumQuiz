package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.baretto.mcq.datamodel.MCQ;
import fr.baretto.scrumquiz.QuestionActivity;
import fr.baretto.scrumquiz.QuestionFragment;

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
    public static final String QUESTION_1="{\"label\":\"Which of the fo||owing are ro|es on a Scrum Team?\",\"choices\":[{\"label\":\"Product Owner\"},{\"label\":\"Scrum Master\"},{\"label\":\"Users\"},{\"label\":\"Customers\"},{\"label\":\"Development Team\"}],\"selectedChoices\":[],\"answerConstraint\":\"ALL_THAT_APPLY\"}";
    public static final String QUESTION_2="{\"label\":\"Haw much work must a Development Team do to a Product Backlog item it selects for a Sprint?\",\"choices\":[{\"label\":\"As much as it has to|d the Product Owner wi|| be done for every Product Back|og item it se|ects in conformance with the\\ndeﬁnition of \\\"Done\\\".\"},{\"label\":\"A proportiona| amount of time on ana|ysis, design, programming, testing, and documentation.\"},{\"label\":\"A|| deve|opment work and at |east some testing.\"},{\"label\":\"As much as it can ﬁt into the Sprint. Any remaining work wi|| be transferred to a subsequent Sprint.\"}],\"selectedChoices\":[],\"answerConstraint\":\"ONE_RESPONSE\"}";
    public static final String JSON = "["+ QUESTION_1 +","+QUESTION_2+"]";
    @Rule
    public ActivityTestRule<QuestionActivity> questionActivityRule = new ActivityTestRule<>(QuestionActivity.class, true, false);

    /**
     * Creation of a Question Activity with one question.
     */
    @Before
    public void before() {
        Intent startIntent = new Intent();
        startIntent.putExtra("Mcq", new MCQ(JSON,2));
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
