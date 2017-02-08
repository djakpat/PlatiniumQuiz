package com.jack_baretto.platiniumquiz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.baretto.mcq.datamodel.Choice;

import java.util.List;

public class ChoiceAdapter extends ArrayAdapter<Choice> {

    private List<Choice> choices;
    private Activity activity;
    private int selectedPosition = -1;
    private boolean isSingleChoice;

    public ChoiceAdapter(Activity context, int resource, List<Choice> objects, boolean isSingleChoice) {
        super(context, resource, objects);
        this.activity = context;
        this.choices = objects;
        this.isSingleChoice = isSingleChoice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.choice, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setText(getItem(position).getLabel());
        Choice currentChoice = choices.get(position);
        // Checked if the choice is already selected
        holder.checkBox.setChecked(currentChoice.isSelected());
        if(this.isSingleChoice){
            holder.checkBox.setOnClickListener(onStateChangedSingleChoiceListener(holder.checkBox, position));
        } else {
            holder.checkBox.setOnClickListener(onStateChangedMultipleChoiceListener(holder.checkBox, position));
        }
        return convertView;
    }

    private View.OnClickListener onStateChangedMultipleChoiceListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choices.get(position).setSelected(checkBox.isChecked());
                notifyDataSetChanged();
            }
        };
    }

    private View.OnClickListener onStateChangedSingleChoiceListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    choices.get(position).setSelected(true);
                }
                for(Choice choice : choices){
                    if(!choice.equals(choices.get(position))){
                        choice.setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        };
    }

    private static class ViewHolder {
        private CheckBox checkBox;

        public ViewHolder(View v) {
            checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        }
    }
}