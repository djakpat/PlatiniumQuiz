package com.jack_baretto.platiniumquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.baretto.mcq.datamodel.MCQ;
import com.baretto.mcq.datamodel.Question;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private MCQ mcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
        List<Question> failedQuestions = new ArrayList<Question>();
        int goodResponse = 0;
        for (Question question : mcq.getQuestions()) {
            if (question.answerIsCorrect()) {
                goodResponse++;
            } else {
                failedQuestions.add(question);
            }
        }

        TextView result = (TextView) findViewById(R.id.textView);
        result.setText("vous avez : " + goodResponse + " Bonnes Réponses");


        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.resultQuestionView);
        QuestionResultAdaptater questionResultAdaptater = new QuestionResultAdaptater(this, failedQuestions);

        expandableListView.setAdapter(questionResultAdaptater);
    }


}
