package com.jack_baretto.platiniumquiz;

import com.baretto.mcq.datamodel.Choice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Work on 19/12/2016.
 */
public class QuestionModel {

    /** Question to ask. */
    private String question;

    /** Choices for the question. */
    private List<Choice> choices = new ArrayList<>();

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
