package com.example.recipelist;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<CategoryForAdapter> {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<CategoryForAdapter> customList;

    CategoryAdapter(Context context, int resource, ArrayList<CategoryForAdapter> customData) {
        super(context, resource, customData);
        this.customList = customData;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final CategoryAdapter.ViewHolder viewHolder;

        convertView = inflater.inflate(this.layout, parent, false);
        viewHolder = new CategoryAdapter.ViewHolder(convertView);
        convertView.setTag(viewHolder);

        final CategoryForAdapter customData = this.customList.get(position);

        viewHolder.nameView.setText(customData.name);
        viewHolder.selectedView.setChecked(customData.selected);

        viewHolder.selectedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customData.selected = !customData.selected;
            }
        });
        
        viewHolder.nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customData.name=viewHolder.nameView.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final CheckBox selectedView;
        final EditText nameView;

        ViewHolder(View view) {
            selectedView = (CheckBox) view.findViewById(R.id.selected);
            nameView = (EditText) view.findViewById(R.id.name);
        }
    }
}
