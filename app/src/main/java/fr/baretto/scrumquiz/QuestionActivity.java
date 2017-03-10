package fr.baretto.scrumquiz;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baretto.mcq.datamodel.MCQ;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;



public class QuestionActivity extends BaseActivity {
    private static final String RETAINED_FRAGMENT = "QuestionFragment";
    Tracker tracker;
    private MCQ mcq;
    private QuestionFragment questionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        tracker = application.getDefaultTracker();
        tracker.setScreenName("QuestionActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        FragmentManager fragmentManager = getFragmentManager();
        questionFragment = (QuestionFragment) fragmentManager.findFragmentByTag(RETAINED_FRAGMENT);

        if (questionFragment == null) {
            questionFragment = new QuestionFragment();
            fragmentManager.beginTransaction().add(questionFragment, RETAINED_FRAGMENT).commit();
        }



        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");


    }




    public void sendMessage(View view) {
        finishQuiz();
    }

    private void finishQuiz() {
        QuestionFragment fragmentById = (QuestionFragment) this.getFragmentManager().findFragmentById(R.id.list);
        fragmentById.updateSelectedChoice();


        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("BUTTON")
                .setAction("Finish_QUIZ")
                .build());

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Mcq", mcq);
        startActivity(intent);
    }

    public MCQ getMcq() {
        return mcq;
    }
}
