package com.jack_baretto.platiniumquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            viewHolder.label = (TextView) convertView.findViewById(R.id.choice);
            convertView.setTag(viewHolder);
        }
        //getItem(position) va récupérer l'item [position] de la liste
        Choice choice = getItem(position);
        //il ne reste plus qu'à remplir notre vue
        viewHolder.label.setText(choice.getLabel());
        return convertView;
    }

    private class PropositionViewHolder{
        public TextView label;
    }
}