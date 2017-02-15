package com.jack_baretto.platiniumquiz;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
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

        TextView result = (TextView) findViewById(R.id.textView);
        result.setText("vous avez : " + goodResponse + " Bonnes Réponses");


        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.resultQuestionView);
        QuestionResultAdaptater questionResultAdaptater = new QuestionResultAdaptater(this, failedQuestions, questionNumberByQuestion);

        expandableListView.setAdapter(questionResultAdaptater);
    }


}
