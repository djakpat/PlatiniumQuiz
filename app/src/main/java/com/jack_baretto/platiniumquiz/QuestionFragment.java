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
 * Fragment for a MCQ question.
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

    /** Button to go to the previous question. */
    private Button previousButton;

    /** Button to go to the next question. */
    private Button nextButton;

    /** Button to view the result. */
    private Button resultButton;

    /** Questions of the MCQ. **/
    private List<QuestionModel> questions = new ArrayList<>();

    /** Index of the current page of the MCQ. */
    private int currentPageIndex = 0;

    private QuizResultManager quizResultManager;

    /** Adapter used for the print the choices. */
    ChoiceAdapter adapter;

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
        List<Choice> datas = new ArrayList<>();
        datas.addAll(questions.get(0).getChoices());
        adapter = new ChoiceAdapter(this.getActivity(), datas);
        choicesView.setAdapter(adapter);
        resultButton = (Button) view.findViewById(R.id.result);
        addPreviousButton(view);
        addNextButton(view);
    }

    /**
     * Add and initialize the previous button.
     * @param view
     */
    private void addPreviousButton(View view) {
        previousButton = (Button) view.findViewById(R.id.previous);
        previousButton.setEnabled(false);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousButtonAction();
            }
        });
    }

    /**
     * Add and Initialize the next button.
     * @param view
     */
    private void addNextButton(View view) {
        nextButton = (Button) view.findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizResultManager.add(5);
                nextButtonAction();
            }
        });
    }

    /**
     * Refresh the MCQ question, actualize buttons state.
     */
    private void nextButtonAction() {
        currentPageIndex ++;
        refreshQuestion();
        previousButton.setEnabled(true);
        if (isLastPage()) {
            nextButton.setEnabled(false);
            resultButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Refresh the MCQ question, actualize buttons state.
     */
    private void previousButtonAction() {
        currentPageIndex --;
        refreshQuestion();
        nextButton.setEnabled(true);
        if (isFirstPage()) {
            previousButton.setEnabled(false);
        }
    }

    /**
     * Refresh the MCQ question and it's choices.
     */
    private void refreshQuestion() {
        questionView.setText(questions.get(currentPageIndex).getQuestion());
        adapter.clear();
        adapter.addAll(questions.get(currentPageIndex).getChoices());
        adapter.notifyDataSetChanged();
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

    /**
     * Indicates if it's the last page of the MCQ.
     * @return Vrai if it's the last page of the MCQ.
     */
    private boolean isLastPage() {
        return currentPageIndex < questions.size();
    }

    /**
     * Indicates if it's the first page of the MCQ.
     * @return Vrai if it's the first page of the MCQ.
     */
    private boolean isFirstPage() { return currentPageIndex == 0; }

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
