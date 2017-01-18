package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);

    }
}
