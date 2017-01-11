package com.jack_baretto.platiniumquiz;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment destiné à afficher la question une question.
 * Created by Work on 06/01/2017.
 */
public class QuestionFragment extends Fragment {

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

    private QuizResultManager quizResultManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        quizResultManager = QuizResultManager.getInstance();
        return inflater.inflate(R.layout.question_fragment_layout,
                container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questions = this.getMCQQuestions();
        questionView = (TextView) view.findViewById(R.id.question);
        questionView.setText(questions.get(currentPageIndex).getQuestion());
        choicesView = (ListView) view.findViewById(R.id.choicesView);
        final ChoiceAdapter adapter = new ChoiceAdapter(this.getActivity(), questions.get(0).getChoices());
        choicesView.setAdapter(adapter);
        resultButton = (Button) view.findViewById(R.id.result);
        nextButton = (Button) view.findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizResultManager.add(5);
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
}
