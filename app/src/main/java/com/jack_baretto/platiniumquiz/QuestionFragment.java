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

import com.baretto.mcq.datamodel.AnswerConstraint;
import com.baretto.mcq.datamodel.Choice;
import com.baretto.mcq.datamodel.Question;
import com.baretto.mcq.datamodel.internals.ChoiceImpl;
import com.baretto.mcq.datamodel.internals.QuestionImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fragment for a MCQ question.
 * Created by Work on 06/01/2017.
 */
public class QuestionFragment extends Fragment {

    /**
     * Adapter used for the print the choices.
     */
    ChoiceAdapter adapter;
    /*
     * View for the answer choices.
     */
    private ListView choicesView;
    /**
     * View for the MCQ question.
     */
    private TextView questionView;
    /**
     * Button to go to the previous question.
     */
    private Button previousButton;
    /**
     * Button to go to the next question.
     */
    private Button nextButton;
    /**
     * Button to view the result.
     */
    private Button resultButton;
    /**
     * Questions of the MCQ.
     **/
    private List<Question> questions = new ArrayList<>();
    /**
     * Index of the current page of the MCQ.
     */
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questions = this.getMCQQuestions();

        questionView.setText(questions.get(currentPageIndex).getLabel());

        choicesView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        List<Choice> datas = new ArrayList<>();
        datas.addAll(questions.get(0).getChoices());
        adapter = new ChoiceAdapter(this.getActivity(), R.layout.choice, datas, true);
        choicesView.setAdapter(adapter);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionView = (TextView) view.findViewById(R.id.question);
        choicesView = (ListView) view.findViewById(R.id.choicesView);
        resultButton = (Button) view.findViewById(R.id.result);
        addPreviousButton(view);
        addNextButton(view);
    }


    /**
     * Add and initialize the previous button.
     *
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
     *
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
        currentPageIndex++;
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
        currentPageIndex--;
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
        questionView.setText(questions.get(currentPageIndex).getLabel());
        adapter.clear();
        adapter.addAll(questions.get(currentPageIndex).getChoices());
        adapter.notifyDataSetChanged();
    }

    /**
     * Get all the questions for the MCQ.
     *
     * @return The {@link Question} list.
     */
    private List<Question> getMCQQuestions() {

        QuestionActivity activity = (QuestionActivity) this.getActivity();

        List<Question> questions = new ArrayList<>(activity.getMcq().getQuestions());
        return questions;
    }

    /**
     * Indicates if it's the last page of the MCQ.
     *
     * @return Vrai if it's the last page of the MCQ.
     */
    private boolean isLastPage() {
        return currentPageIndex < questions.size();
    }

    /**
     * Indicates if it's the first page of the MCQ.
     *
     * @return Vrai if it's the first page of the MCQ.
     */
    private boolean isFirstPage() {
        return currentPageIndex == 0;
    }

    /**
     * Generate a {@link Question}for a question.
     *
     * @return a {@link Question}.
     */
    private Question generateQuestionModel(int i) {
        Set<Choice> choices = new HashSet<>();
        choices.add(new ChoiceImpl("Réponse " + i + ".1"));
        choices.add(new ChoiceImpl("Réponse " + i + ".2"));
        QuestionImpl questionModel = new QuestionImpl("Question " + i + " :", choices, new HashSet<Choice>(), AnswerConstraint.ONE_RESPONSE);
        return questionModel;
    }
}
