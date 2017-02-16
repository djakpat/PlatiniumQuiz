package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baretto.mcq.datamodel.MCQ;

public class QuestionActivity extends AppCompatActivity {

    MCQ mcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
        Boolean timerOption = (Boolean) getIntent().getSerializableExtra("timerOption");
        if(timerOption){
            CountDownTimer timer = new CountDownTimer(300000,1000)  {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                }
            };
        }

    }

    public void sendMessage(View view) {

        QuestionFragment fragmentById = (QuestionFragment) this.getFragmentManager().findFragmentById(R.id.list);
        fragmentById.updateSelectedChoice();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Mcq", mcq);
        startActivity(intent);

    }

    public MCQ getMcq() {
        return mcq;
    }
}
