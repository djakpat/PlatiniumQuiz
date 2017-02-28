package fr.baretto.scrumquiz;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baretto.mcq.datamodel.MCQ;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.jack_baretto.platiniumquiz.R;


public class QuestionActivity extends AppCompatActivity {
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
            // add the fragment
            questionFragment = new QuestionFragment();
            fragmentManager.beginTransaction().add(questionFragment, RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            //questionFragment.setData(loadMyData());
        }



        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    //@FIXME : code duplicate in QuestionActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent aboutUs = new Intent(this, AboutActivity.class);
                startActivity(aboutUs);
                break;
            case R.id.contact:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:scrumquiz.contact@baretto.fr"));
                startActivity(Intent.createChooser(emailIntent,
                        "Send Email Using: "));
                break;
            default:
                break;
        }

        return true;
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
