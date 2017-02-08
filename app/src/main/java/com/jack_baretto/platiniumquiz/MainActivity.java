package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.baretto.mcq.datamodel.MCQ;


import com.baretto.mcq.datamodel.MCQ;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


    }


    public void sendMessage(View view) throws IOException {
       int progress = seekBar.getProgress();
        MCQ mcq = generateMCQ();
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("Mcq", mcq);
        startActivity(intent);

    }

    /**
     * Extract mcq from json file.
     *
     * @return MCQ
     * @throws IOException
     */
    private MCQ generateMCQ() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.mcqs);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int length;
        while ((length = inputStream.read()) != -1) {
            stream.write(length);
        }
        String json = stream.toString();
        return new MCQ(json);
    }


}
