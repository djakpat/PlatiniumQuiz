package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baretto.mcq.datamodel.MCQ;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView numbersOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.incrementProgressBy(10);
        numbersOfQuestion = (TextView) findViewById(R.id.numbersOfQuestion);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = progressConverter(progress);
                numbersOfQuestion.setText("nombres de questions : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    /**
     * Convert progress value in order to retrieve discret value with by 10.
     * @TODO find an other way to have this behaviour. Mayne seekbar isn't appropriate.
     */
    private int progressConverter(int progress) {
        progress = progress * 10 + 10;
        return progress;
    }


    public void sendMessage(View view) throws IOException {
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
        final int numberOfQuestions = progressConverter(seekBar.getProgress());
        return new MCQ(json, numberOfQuestions);
    }


}
