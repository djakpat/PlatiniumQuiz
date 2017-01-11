package com.jack_baretto.platiniumquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ResultActivity extends AppCompatActivity {
    QuizResultManager quizResultManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        quizResultManager= QuizResultManager.getInstance();
        Log.i("INFOS", String.valueOf(quizResultManager.getTest()));
    }




}
