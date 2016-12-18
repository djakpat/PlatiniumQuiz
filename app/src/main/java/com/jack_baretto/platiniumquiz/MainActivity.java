package com.jack_baretto.platiniumquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
     * View for the answer choices.
     */
    ListView choicesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choicesView = (ListView) findViewById(R.id.choicesView);
        List<Choice> choices = generateChoices();
        ChoiceAdapter adapter = new ChoiceAdapter(MainActivity.this, choices);
        choicesView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    /**
     * Generate a list of {@link Choice}.
     *
     * @return list of {@link Choice}.
     */
    private List<Choice> generateChoices() {
        List<Choice> choices = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            choices.add(new Choice("Réponse " + i));

        }
        return choices;
    }
}
