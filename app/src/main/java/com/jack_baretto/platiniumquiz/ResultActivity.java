package com.jack_baretto.platiniumquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baretto.mcq.datamodel.Question;
import com.baretto.mcq.datamodel.MCQ;

public class ResultActivity extends AppCompatActivity {

    private MCQ mcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");

        int goodResponse = 0;
        for (Question question : mcq.getQuestions()) {
            if (question.answerIsCorrect()) {
                goodResponse++;
            }
        }

        TextView result = (TextView) findViewById(R.id.textView);
        result.setText("vous avez : " + goodResponse + " Bonnes Réponses");
    }




}
