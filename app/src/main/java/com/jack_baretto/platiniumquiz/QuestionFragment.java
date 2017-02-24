package com.jack_baretto.platiniumquiz;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for a MCQ question.
 * Created by Work on 06/01/2017.
 */
public class QuestionFragment extends Fragment {

    /**
     * Adapter used for the print the choices.
     */
    ChoiceAdapter adapter;
    Tracker tracker;
    /*
     * View for the answer choices.
     */
    private ListView choicesView;
    /**
     * View for the MCQ question.
     */
    private TextView questionView;
    /**
     * View for the MCQ question constraint.
     */
    private TextView constraintView;
    /**
     * Vie for the MCQ question number.
     */
    private TextView questionNumber;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        tracker = application.getDefaultTracker();
        tracker.setScreenName("QuestionFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment_layout,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionView = (TextView) view.findViewById(R.id.question);
        constraintView = (TextView) view.findViewById(R.id.constraint);
        choicesView = (ListView) view.findViewById(R.id.choicesView);
        resultButton = (Button) view.findViewById(R.id.result);
        questionNumber = (TextView) view.findViewById(R.id.questionNumber);
        addPreviousButton(view);
        addNextButton(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questions = this.getMCQQuestions();

        Question question = questions.get(currentPageIndex);
        questionView.setText(question.getLabel());
        constraintView.setText(retrieveConstraintLabel(question));
        choicesView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        String questionNumberValue = retrieveQuestionNumberValue();
        questionNumber.setText(questionNumberValue);

        List<Choice> datas = new ArrayList<>();
        datas.addAll(question.getChoices());

        boolean isSingleChoice = isSingleChoice(question);


        adapter = new ChoiceAdapter(this.getActivity(), R.layout.choice, datas, isSingleChoice);
        choicesView.setAdapter(adapter);

    }

    private boolean isSingleChoice(Question question) {
        boolean isSingleChoice = false;
        if (question.getAnswerConstraint().equals(AnswerConstraint.ONE_RESPONSE)) {
            isSingleChoice = true;
        }
        return isSingleChoice;
    }


    @NonNull
    private String retrieveQuestionNumberValue() {
        return "Question " + String.valueOf(currentPageIndex + 1) + " of " + questions.size();
    }


    /**
     * Add and initialize the previous button.
     *
     * @param view
     */
    private void addPreviousButton(View view) {
        previousButton = (Button) view.findViewById(R.id.previous);
        if (isFirstPage()) {
            previousButton.setEnabled(false);
        }
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
                nextButtonAction();
            }
        });
        if (isLastPage()) {
            nextButton.setEnabled(false);
            resultButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Refresh the MCQ question, actualize buttons state.
     */
    private void nextButtonAction() {

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("BUTTON")
                .setAction("NEXT")
                .build());

        this.updateSelectedChoice();
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

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("BUTTON")
                .setAction("PREV")
                .build());

        this.updateSelectedChoice();
        currentPageIndex--;
        refreshQuestion();
        nextButton.setEnabled(true);
        resultButton.setVisibility(View.GONE);
        if (isFirstPage()) {
            previousButton.setEnabled(false);
        }
    }

    /**
     * Refresh the MCQ question and it's choices.
     */
    private void refreshQuestion() {
        Question question = questions.get(currentPageIndex);
        questionView.setText(question.getLabel());
        constraintView.setText(retrieveConstraintLabel(question));
        String questionNumberValue = retrieveQuestionNumberValue();
        questionNumber.setText(questionNumberValue);
        final boolean isSingleChoice = isSingleChoice(question);
        adapter.addChoices(question.getChoices(), isSingleChoice);

    }

    private String retrieveConstraintLabel(Question question) {
        AnswerConstraint answerConstraint = questions.get(currentPageIndex).getAnswerConstraint();
        if (AnswerConstraint.ALL_THAT_APPLY == answerConstraint) {
            return "Check all that apply.";
        } else if (AnswerConstraint.ONE_RESPONSE == answerConstraint) {
            return "Check 1 choice.";
        } else if (AnswerConstraint.N_RESPONSES == answerConstraint) {
            int numberOfCorrectChoices = question.retrieveNumberOfCorrectChoices();
            return "Check " + numberOfCorrectChoices + " choices.";
        }
        return answerConstraint.toString();
    }

    public void updateSelectedChoice() {
        Question question = questions.get(currentPageIndex);
        List<Choice> selectedChoices = new ArrayList();
        for (Choice choice : question.getChoices()) {
            if (choice.isSelected()) {
                selectedChoices.add(choice);
            }
        }
        question.setSelectedChoices(selectedChoices);
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
        return currentPageIndex == questions.size() - 1;
    }

    /**
     * Indicates if it's the first page of the MCQ.
     *
     * @return Vrai if it's the first page of the MCQ.
     */
    private boolean isFirstPage() {
        return currentPageIndex == 0;
    }

}
