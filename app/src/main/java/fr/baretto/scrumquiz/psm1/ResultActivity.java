package fr.baretto.scrumquiz.psm1;

import android.app.FragmentManager;
import android.os.Bundle;

import com.baretto.mcq.datamodel.MCQ;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class ResultActivity extends BaseActivity {

    private static final String RETAINED_FRAGMENT ="RESULT_FRAGMENT" ;
    Tracker tracker;
    private MCQ mcq;

    private ResultFragment resultFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
        setContentView(R.layout.activity_result);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        tracker = application.getDefaultTracker();


        tracker.setScreenName("ResultActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());


        FragmentManager fragmentManager = this.getFragmentManager();
        resultFragment = (ResultFragment) fragmentManager.findFragmentByTag(RETAINED_FRAGMENT);
        if (resultFragment == null) {
            resultFragment = new ResultFragment();
            fragmentManager.beginTransaction().add(resultFragment, RETAINED_FRAGMENT).commit();
        }
    }

    public MCQ getMCQ() {
        return mcq;
    }
}
