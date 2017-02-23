package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baretto.mcq.datamodel.MCQ;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity {

    /** Increase the progress bar's progress by this specified amount. */
    private static final int QUESTION_INCREMENT = 10;

    /** Number of questions progress bar. */
    private SeekBar seekBar;

    /** Number of questions title. */
    private TextView numbersOfQuestion;

    /** Give the instructions to the user. */
    private TextView instructions;

    /** Print the number of questions */
    private TextView instructionsQuestionsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.incrementProgressBy(QUESTION_INCREMENT);
        numbersOfQuestion = (TextView) findViewById(R.id.numbersOfQuestion);
        numbersOfQuestion.setText(Html.fromHtml(getString(R.string.instructions_question_number) + "<b>" + Integer.toString(progressConverter(seekBar.getProgress())) + "</b>"));
        instructions = (TextView) findViewById(R.id.instructions);
        instructions.setText(getString(R.string.instructions_title) + "\n");
        instructionsQuestionsNumber = (TextView) findViewById(R.id.instructionsQuestionsNumber);
        instructionsQuestionsNumber.setText("\u25CF" + getString(R.string.instructions_question_number) + progressConverter(seekBar.getProgress()));
        TextView instructionsPassMark = (TextView) findViewById(R.id.instructionsPassMark);
        instructionsPassMark.setText("\u25CF" + getString(R.string.instructions_pass_mark) + getString(R.string.pass_mark_limit) + "%");
        seekBar.setOnSeekBarChangeListener(getQuestionNumberSeekBarListener());
    }

    /**
     *
     * @return the question seekbar listener.
     */
    @NonNull
    private SeekBar.OnSeekBarChangeListener getQuestionNumberSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                numbersOfQuestion.setText(getString(R.string.instructions_question_number) + progressConverter(progress));
                numbersOfQuestion.setText(Html.fromHtml(getString(R.string.instructions_question_number) + "<b>" + Integer.toString(progressConverter(seekBar.getProgress())) + "</b>"));
                instructionsQuestionsNumber.setText("\u25CF" + getString(R.string.instructions_question_number) + progressConverter(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.restart).setVisible(false);
        return true;
    }

    //@FIXME : code duplicate from QuestionActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent aboutUs = new Intent(this, AboutActivity.class);
                startActivity(aboutUs);
                break;
            case R.id.contact:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:scrumquiz.contact@baretto.fr"));
                startActivity(Intent.createChooser(emailIntent,
                        "Send Email Using: "));
                break;
            default:
                break;
        }

        return true;
    }


    /**
     * Convert progress value in order to retrieve discret value with by {@link MainActivity#QUESTION_INCREMENT}.
     * @TODO find an other way to have this behaviour. Mayne seekbar isn't appropriate.
     */
    private int progressConverter(int progress) {
        progress = progress * QUESTION_INCREMENT + QUESTION_INCREMENT;
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
