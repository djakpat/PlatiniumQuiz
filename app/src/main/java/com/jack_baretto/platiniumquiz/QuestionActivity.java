package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baretto.mcq.datamodel.MCQ;

public class QuestionActivity extends AppCompatActivity {
    MCQ mcq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity_layout);
        mcq = (MCQ) getIntent().getSerializableExtra("Mcq");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

    public void sendMessage(View view) {
        finishQuiz();
    }

    private void finishQuiz() {
        QuestionFragment fragmentById = (QuestionFragment) this.getFragmentManager().findFragmentById(R.id.list);
        fragmentById.updateSelectedChoice();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Mcq", mcq);
        startActivity(intent);
    }

    public MCQ getMcq() {
        return mcq;
    }
}
