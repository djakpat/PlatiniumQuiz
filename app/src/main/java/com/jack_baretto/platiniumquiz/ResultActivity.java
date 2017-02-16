package com.jack_baretto.platiniumquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        updateResultText(goodResponse, mcq.getQuestions().size());

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.resultQuestionView);
        QuestionResultAdaptater questionResultAdaptater = new QuestionResultAdaptater(this, failedQuestions, questionNumberByQuestion);

        expandableListView.setAdapter(questionResultAdaptater);


    }

    private void updateResultText(int goodResponse, int questionsSize) {

        int resulRate = Math.round(((float) goodResponse / (float) questionsSize) * 100);

        if (resulRate < minimumSucessRate) {

            X(goodResponse, "FAILED", Color.RED, resulRate + "%");


        } else {

            X(goodResponse, "PASSED", Color.GREEN, resulRate + "%");


        }


    }

    private void X(int goodResponse, String failed, int red, String text) {
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(failed);
        result.setTextColor(red);

        TextView sucessRate = (TextView) findViewById(R.id.sucessRate);
        sucessRate.setText(text);
        sucessRate.setTextColor(red);
        TextView resultTest = (TextView) findViewById(R.id.resultText);

        resultTest.setText("You have : " + goodResponse + " good answers");
    }


}
