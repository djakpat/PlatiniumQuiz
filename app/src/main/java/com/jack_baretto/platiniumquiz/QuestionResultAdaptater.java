package com.jack_baretto.platiniumquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baretto.mcq.datamodel.Choice;
import com.baretto.mcq.datamodel.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 27/01/2017.
 */

public class QuestionResultAdaptater extends BaseExpandableListAdapter {


    private final Map<Question, Integer> questionNumberByQuestion;
    private Context _context;

    private List<Question> dataQuestions;


    public QuestionResultAdaptater(ResultActivity resultActivity, List<Question> failedQuestions, Map<Question, Integer> questionNumberByQuestion) {
        this._context = resultActivity;
        this.dataQuestions = failedQuestions;
        this.questionNumberByQuestion = questionNumberByQuestion;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        final Question question = dataQuestions.get(groupPosition);
        List<Choice> choices = new ArrayList<>(question.getChoices());
        if (childPosititon < (this.getChildrenCount(groupPosition)) - 1) {
            return choices.get(childPosititon);
        } else {
            return question.getCorrection();
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        final Object child = getChild(groupPosition, childPosition);

        ImageView selectedImage = (ImageView) convertView.findViewById(R.id.selected);
        ImageView answerImage = (ImageView) convertView.findViewById(R.id.answer);
        ImageView infoImage = (ImageView) convertView.findViewById(R.id.info);
        selectedImage.setVisibility(View.GONE);
        answerImage.setVisibility(View.GONE);
        infoImage.setVisibility(View.GONE);
        if (child instanceof Choice) {
            updateChoiceView(groupPosition, convertView, txtListChild, (Choice) child, selectedImage, answerImage);
        } else {
            final String childString = (String) child;
            txtListChild.setText(childString);
            infoImage.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    private void updateChoiceView(int groupPosition, View convertView, TextView txtListChild, Choice child, ImageView selectedImage, ImageView answerImage) {
        final Choice childChoice = child;
        Question question = (Question) getGroup(groupPosition);


        txtListChild.setText(childChoice.getLabel());


        if (childChoice.isSelected()) {

            selectedImage.setVisibility(View.VISIBLE);
        }

        if (question.choiceIsCorrect(childChoice) && !childChoice.isSelected()) {
            answerImage.setImageResource(R.drawable.ic_action_done);
            answerImage.setVisibility(View.VISIBLE);


        } else if (!(question.choiceIsCorrect(childChoice)) && childChoice.isSelected()) {
            answerImage.setImageResource(R.drawable.ic_content_clear);
            answerImage.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        final Question question = dataQuestions.get(groupPosition);
        List<Choice> choices = new ArrayList<>(question.getChoices());
        if (question.getCorrection()!=null) {
            return choices.size();
        } else {
            return choices.size() + 1;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return (dataQuestions.get(groupPosition));
    }

    @Override
    public int getGroupCount() {
        return dataQuestions.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Question headerQuestion = (Question) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerQuestion.getLabel());
        TextView questioNumber = (TextView) convertView.findViewById(R.id.questionNumber);
        String questionNumberValue = String.valueOf(questionNumberByQuestion.get(headerQuestion));
        questioNumber.setText("Question " + questionNumberValue);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
