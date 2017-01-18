package com.jack_baretto.platiniumquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baretto.mcq.datamodel.Choice;

import java.util.ArrayList;
import java.util.List;

public class ChoiceAdapter extends ArrayAdapter<Choice> {

    public ChoiceAdapter(Context context, List<Choice> choices) {
        super(context, 0, choices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.choice, parent, false);
        }
        PropositionViewHolder viewHolder = (PropositionViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PropositionViewHolder();
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        }
        //getItem(position) va récupérer l'item [position] de la liste
        Choice choice = getItem(position);
        //il ne reste plus qu'à remplir notre vue
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                Choice currentChoice = (Choice) checkBox.getTag();
                currentChoice.setSelected(((CheckBox) v).isChecked());
            }
        });
        viewHolder.checkBox.setTag(choice);
        viewHolder.checkBox.setText(choice.getLabel());
        viewHolder.checkBox.setChecked(choice.isSelected());
        return convertView;
    }


    private class PropositionViewHolder{
        public CheckBox checkBox;
    }
}