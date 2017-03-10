package fr.baretto.scrumquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;

import com.baretto.mcq.datamodel.MCQ;
import com.baretto.mcq.datamodel.Question;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultFragment extends Fragment {
    private Tracker tracker;
    private MCQ mcq;
    private Switch showAllSwitch;
    private int minimumSucessRate = 85;
    private TextView result;
    private TextView resultTest;
    private TextView sucessRate;
    private TextView wrongAnswers;
    private ExpandableListView expandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        tracker = application.getDefaultTracker();
        tracker.setScreenName("ResultFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());


        ResultActivity resultActivity = (ResultActivity) this.getActivity();

        mcq = resultActivity.getMCQ();




        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = (TextView) view.findViewById(R.id.result);
        sucessRate = (TextView) view.findViewById(R.id.sucessRate);
        resultTest = (TextView) view.findViewById(R.id.resultText);
        wrongAnswers = (TextView) view.findViewById(R.id.wrong_answer);
        showAllSwitch = (Switch) view.findViewById(R.id.showAll);
        expandableListView = (ExpandableListView) view.findViewById(R.id.resultQuestionView);
        showAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Feature")
                        .setAction("use")
                        .setLabel("showAllSwitch")
                        .setValue(1)
                        .build());

                initializeView(isChecked);

            }
        });
        initializeView(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initializeView(boolean checked) {
        Map<Question, Integer> questionNumberByQuestion = new HashMap();
        List<Question> failedQuestions = new ArrayList<Question>();
        int goodResponse = 0;
        int questionNumber = 1;
        for (Question question : mcq.getQuestions()) {
            questionNumberByQuestion.put(question, questionNumber);
            if (question.answerIsCorrect()) {
                goodResponse++;
            } else {
                failedQuestions.add(question);
            }
            questionNumber++;
        }

        showResult(goodResponse, mcq.getQuestions().size());

        updateResultListView(checked, questionNumberByQuestion, failedQuestions);
    }

    private void updateResultListView(boolean checked, Map<Question, Integer> questionNumberByQuestion, List<Question> failedQuestions) {

        QuestionResultAdaptater questionResultAdaptater = null;
        if (checked) {
            questionResultAdaptater = new QuestionResultAdaptater((ResultActivity) this.getActivity(), mcq.getQuestions(), questionNumberByQuestion);

        } else {
            questionResultAdaptater = new QuestionResultAdaptater((ResultActivity) this.getActivity(), failedQuestions, questionNumberByQuestion);
        }
        expandableListView.setAdapter(questionResultAdaptater);
        questionResultAdaptater.notifyDataSetChanged();
    }

    private void showResult(int goodResponse, int questionsSize) {

        int resulRate = Math.round(((float) goodResponse / (float) questionsSize) * 100);

        if (resulRate < minimumSucessRate) {

            updateResulText(goodResponse, getString(R.string.failed), Color.RED, resulRate + "%", questionsSize);

        } else {

            updateResulText(goodResponse, getString(R.string.passed), Color.GREEN, resulRate + "%", questionsSize);
        }


    }

    private void updateResulText(int goodResponse, String failed, int red, String TheSucessRate, int size) {

        result.setText(failed);
        result.setTextColor(red);

        sucessRate.setText(TheSucessRate);
        sucessRate.setTextColor(red);

        resultTest.setText(getString(R.string.you_have) + " : " + goodResponse + " / " + size + " " + getString(R.string.good_answers));


        wrongAnswers.setText(R.string.wrong_answers);
    }

}
