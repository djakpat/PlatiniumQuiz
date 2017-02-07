package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.baretto.mcq.datamodel.MCQ;


public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


    }

    public void sendMessage(View view){


        int progress = seekBar.getProgress();
        MCQ mcq = new MCQ(progress);

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("Mcq",mcq);
        startActivity(intent);

    }
}
