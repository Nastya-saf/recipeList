package com.example.recipelist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class IngredientsAdapter extends ArrayAdapter<IngredientForAdapter> {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<IngredientForAdapter> customList;

    IngredientsAdapter(Context context, int resource, ArrayList<IngredientForAdapter> customData) {
        super(context, resource, customData);
        this.customList = customData;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final IngredientsAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new IngredientsAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IngredientsAdapter.ViewHolder) convertView.getTag();
        }
        final IngredientForAdapter customData = this.customList.get(position);

        viewHolder.nameView.setText(customData.name);
        viewHolder.countView.setText(customData.quantity);
        viewHolder.selectedView.setChecked(customData.selected);

        viewHolder.selectedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customData.selected=!customData.selected;
            }
        });

        viewHolder.nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customList.get(position).name=viewHolder.nameView.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        viewHolder.countView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customList.get(position).quantity=viewHolder.countView.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final CheckBox selectedView;
        final EditText nameView, countView;
        ViewHolder(View view){
            selectedView = (CheckBox) view.findViewById(R.id.selected);
            nameView = (EditText) view.findViewById(R.id.name);
            countView = (EditText) view.findViewById(R.id.quantity);

        }
    }

}
