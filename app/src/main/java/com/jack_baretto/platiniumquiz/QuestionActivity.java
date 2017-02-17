package com.jack_baretto.platiniumquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baretto.mcq.datamodel.MCQ;

public class QuestionActivity extends AppCompatActivity {
    MCQ mcq;
    private final Context context = this;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
        Boolean timerOption = (Boolean) getIntent().getSerializableExtra("timerOption");
        if(timerOption){
            timer = new CountDownTimer(5000,1000)  {
                @Override
                public void onTick(long millisUntilFinished) {
                    QuestionFragment questionFragment =  (QuestionFragment) getFragmentManager().findFragmentById(R.id.list);
                    if(questionFragment!=null){
                        questionFragment.updateTimer(millisUntilFinished / 1000);
                    }
                }

                @Override
                public void onFinish() {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("End of time");
                    alert.setMessage("Test :  ");
                    alert.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishQuiz();
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            };

        }

    }

    public void sendMessage(View view) {
        finishQuiz();
    }

    private void finishQuiz() {
        QuestionFragment fragmentById = (QuestionFragment) this.getFragmentManager().findFragmentById(R.id.list);
        fragmentById.updateSelectedChoice();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Mcq", mcq);
        startActivity(intent);
    }

    public MCQ getMcq() {
        return mcq;
    }


    public void initializeTimer() {
        if(timer!=null){
            timer.start();
        }
    }
}
