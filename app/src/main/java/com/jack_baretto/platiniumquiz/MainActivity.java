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

    /** Button to view the result. */
    private Button resultButton;

    /** Questions of the MCQ. **/
    List<QuestionModel> questions = new ArrayList<>();

    // Index of the current page of the MCQ.
    private int currentPageIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questions = this.getMCQQuestions();
        questionView = (TextView) findViewById(R.id.question);
        questionView.setText(questions.get(currentPageIndex).getQuestion());
        choicesView = (ListView) findViewById(R.id.choicesView);
        final ChoiceAdapter adapter = new ChoiceAdapter(MainActivity.this, questions.get(0).getChoices());
        choicesView.setAdapter(adapter);
        resultButton = (Button) findViewById(R.id.result);
        nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPageIndex ++;
                questionView.setText(questions.get(currentPageIndex).getQuestion());
                adapter.clear();
                adapter.addAll(questions.get(currentPageIndex).getChoices());
                adapter.notifyDataSetChanged();
                if (isLastPage()) {
                    nextButton.setEnabled(false);
                    resultButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean isLastPage() {
        return currentPageIndex < questions.size();
    }


    /**
     * Generate a {@link QuestionModel}for a question.
     * @return a {@link QuestionModel}.
     */
    private QuestionModel generateQuestionModel(int i) {
        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestion("Question " + i + " :");
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("Réponse " + i + ".1"));
        choices.add(new Choice("Réponse " + i + ".2"));
        questionModel.setChoices(choices);
        return questionModel;
    }

    /**
     * Get all the questions for the MCQ.
     * @return The {@link QuestionModel} list.
     */
    private List<QuestionModel> getMCQQuestions(){
        List<QuestionModel> questions = new ArrayList<>();
        questions.add(generateQuestionModel(1));
        questions.add(generateQuestionModel(2));
        return questions;
    }
}
