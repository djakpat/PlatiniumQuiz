package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.baretto.mcq.datamodel.MCQ;
import com.baretto.mcq.datamodel.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private MCQ mcq;

    private int minimumSucessRate = 85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();

        Map<Question, Integer> questionNumberByQuestion = new HashMap();
        List<Question> failedQuestions = new ArrayList<Question>();
        int goodResponse = 0;
        int questionNumber =1;
        for (Question question : mcq.getQuestions()) {
            if (question.answerIsCorrect()) {
                goodResponse++;
            } else {
                failedQuestions.add(question);
                questionNumberByQuestion.put(question, questionNumber);
            }
            questionNumber++;
        }

        showResult(goodResponse, mcq.getQuestions().size());

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.resultQuestionView);
        QuestionResultAdaptater questionResultAdaptater = new QuestionResultAdaptater(this, failedQuestions, questionNumberByQuestion);

        expandableListView.setAdapter(questionResultAdaptater);


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
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(failed);
        result.setTextColor(red);

        TextView sucessRate = (TextView) findViewById(R.id.sucessRate);
        sucessRate.setText(TheSucessRate);
        sucessRate.setTextColor(red);

        TextView resultTest = (TextView) findViewById(R.id.resultText);
        resultTest.setText(getString(R.string.you_have) + " : " + goodResponse + " / " + size + " " + getString(R.string.good_answers));


        TextView wrongAnswers = (TextView) findViewById(R.id.wrong_answer);
        wrongAnswers.setText(R.string.wrong_answers);
    }


}
