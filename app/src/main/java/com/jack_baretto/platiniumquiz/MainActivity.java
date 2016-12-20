package com.jack_baretto.platiniumquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    /** Button to go to the next question. */
    private Button nextButton;

    /** Compteur. */
    int compteur = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QuestionModel data = generateQuestionModel("Any Question ?");
        questionView = (TextView) findViewById(R.id.question);
        questionView.setText(data.getQuestion());
        choicesView = (ListView) findViewById(R.id.choicesView);
        final ChoiceAdapter adapter = new ChoiceAdapter(MainActivity.this, data.getChoices());
        choicesView.setAdapter(adapter);
        nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionModel newQuestion = generateQuestionModel("AnotherQuestion");
                questionView.setText(newQuestion.getQuestion() + compteur);
                adapter.clear();
                adapter.addAll(newQuestion.getChoices());
                adapter.notifyDataSetChanged();
                compteur++;
            }
        });
    }

    /**
     * Generate a {@link QuestionModel}for a question.
     * @return a {@link QuestionModel}.
     */
    private QuestionModel generateQuestionModel(String question) {
        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestion(question);
        List<Choice> choices = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            choices.add(new Choice("Réponse " + compteur + " " + i));
        }
        questionModel.setChoices(choices);
        return questionModel;
    }
}
