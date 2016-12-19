package com.jack_baretto.platiniumquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
     * View for the answer choices.
     */
    private ListView choicesView;

    /**
     * View for the MCQ question.
     */
    private TextView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choicesView = (ListView) findViewById(R.id.choicesView);
        List<Choice> choices = generateChoices();
        ChoiceAdapter adapter = new ChoiceAdapter(MainActivity.this, choices);
        choicesView.setAdapter(adapter);
        questionView = (TextView) findViewById(R.id.question);
        questionView.setText(this.loadQuestion());
    }

    /**
     * Load the MCQ question to print.
     * @return The question to print.
     */
    private String loadQuestion(){
        return "Any question ?";
    }

    /**
     * Generate a list of {@link Choice}.
     * @return list of {@link Choice}.
     */
    private List<Choice> generateChoices() {
        List<Choice> choices = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            choices.add(new Choice("Réponse " + i));
        }
        return choices;
    }
}
