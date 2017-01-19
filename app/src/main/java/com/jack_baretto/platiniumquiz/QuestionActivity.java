package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baretto.mcq.datamodel.Question;
import com.baretto.mcq.datamodel.internals.MCQ;

import java.io.Serializable;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);
        MCQ mcq = (MCQ) getIntent().getSerializableExtra("Mcq");
        }

    public void sendMessage(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);

    }
}
