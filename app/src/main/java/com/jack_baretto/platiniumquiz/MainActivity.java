package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baretto.mcq.datamodel.internals.MCQ;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){

        MCQ mcq = new MCQ(3);

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("Mcq",mcq);
        startActivity(intent);

    }
}
