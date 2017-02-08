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

/**
 * Created by WORK on 27/01/2017.
 */

public class QuestionResultAdaptater extends BaseExpandableListAdapter {


    private Context _context;
    // private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    // private HashMap<String, List<String>> _listDataChild;

    private List<Question> dataQuestions;


    public QuestionResultAdaptater() {
        super();
    }

    /*public QuestionResultAdaptater(Context context, List<String> listDataHeader,
                                   HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }*/

    public QuestionResultAdaptater(ResultActivity resultActivity, List<Question> failedQuestions) {
        this._context = resultActivity;
        this.dataQuestions = failedQuestions;


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        List<Choice> choices = new ArrayList<>(dataQuestions.get(groupPosition).getChoices());
        return choices.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Choice childChoice = (Choice) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childChoice.getLabel());

        ImageView selectedImage = (ImageView) convertView.findViewById(R.id.selected);
        ImageView answerImage = (ImageView) convertView.findViewById(R.id.answer);
        selectedImage.setVisibility(View.GONE);
        answerImage.setVisibility(View.GONE);

        if (childChoice.isSelected()) {

            selectedImage.setVisibility(View.VISIBLE);
        }
        Question question = (Question) getGroup(groupPosition);
        if (question.choiceIsCorrect(childChoice)) {
            answerImage.setImageResource(R.drawable.ic_action_done);
            answerImage.setVisibility(View.VISIBLE);
            //myMenuItem.setIcon(android.R.drawable.ic_menu_save);ic_action_done

        } else {
            answerImage.setImageResource(R.drawable.ic_content_clear);
            answerImage.setVisibility(View.VISIBLE);

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Choice> choices = new ArrayList<>(dataQuestions.get(groupPosition).getChoices());
        return choices.size();

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
