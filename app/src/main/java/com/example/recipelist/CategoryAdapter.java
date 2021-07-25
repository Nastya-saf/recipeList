package com.example.recipelist;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        Log.d("","----------------- ADAPTER !!!!!!! customData: "+customData);
        this.customList = customData;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final CategoryAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new CategoryAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }
        final CategoryForAdapter customData = this.customList.get(position);
        Log.d("","----------------- ADAPTER  this.customList: "+this.customList);
        Log.d("","----------------- ADAPTER  position: "+position+" this.customList.get(position): "+this.customList.get(position));

        viewHolder.nameView.setText(customData.name);
        viewHolder.selectedView.setChecked(customData.selected);

        viewHolder.selectedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customData.selected = !customData.selected;
            }
        });


//        viewHolder.nameView.getOnFocusChangeListener()

//        viewHolder.nameView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // действия после того, как что то введено
//                // editable - то, что введено. В строку - editable.toString()
//                Log.d("","--------------- !!!!! afterTextChanged !!!!! editable: "+editable);
//                if(customData.name==editable.toString()){
//                    Log.d("","--------------- !!!!! IF !!!!! customData.name: "+customData.name+" editable: "+editable.toString());
//                    customData.name=editable.toString();
//                }
//
//            }
//        });

        Log.d("","--------------- !!!!! convertView: "+convertView);

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
