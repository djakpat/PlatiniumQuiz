package fr.baretto.scrumquiz.psm1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baretto.mcq.datamodel.MCQ;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends BaseActivity {

    /** Increase the progress bar's progress by this specified amount. */
    private static final int QUESTION_INCREMENT = 10;
    public static final String PARAMETER_SPEC = "RandomInitVector";
    public static final String KEY = "123azertyuhgfq'(";
    Tracker tracker;
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

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        tracker = application.getDefaultTracker();
        tracker.setScreenName("MainActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.incrementProgressBy(QUESTION_INCREMENT);
        seekBar.setProgress(1);
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

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("BUTTON")
                .setAction("Start_QUIZ")
                .setValue(mcq.getQuestions().size())
                .build());

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("Mcq", mcq);
        Timer.getInstance().start();
        startActivity(intent);
    }

    /**
     * Extract mcq from json file.
     *
     * @return MCQ
     * @throws IOException
     */
    private MCQ generateMCQ() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.encrypted);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int length;
        while ((length = inputStream.read()) != -1) {
            stream.write(length);
        }
        String json = decrypt(stream.toString());
        final int numberOfQuestions = progressConverter(seekBar.getProgress());
        return new MCQ(json, numberOfQuestions);

    }


    private String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(PARAMETER_SPEC.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");


            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] bytes = android.util.Base64.decode(encrypted, android.util.Base64.DEFAULT);
            byte[] original = cipher.doFinal(bytes);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
