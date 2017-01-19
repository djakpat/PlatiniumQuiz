package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baretto.mcq.datamodel.internals.MCQ;

public class QuestionActivity extends AppCompatActivity {

    MCQ mcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
        }

    public void sendMessage(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public MCQ getMcq() {
        return mcq;
    }
}
