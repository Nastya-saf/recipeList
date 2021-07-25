package com.example.recipelist;


import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.recipelist.ItemPageActivity.ID_CATEGORY;

public class CreateItemMainFragment extends Fragment {

    private View view;
    Spinner spinner;

    Item item;
    int idCategory;

    public static CreateItemMainFragment newInstance() {
        return new CreateItemMainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_item_main, container, false);


        // TODO: для CreateItemMainFragment
        this.spinner = (Spinner) this.view.findViewById(R.id.categories);

        List<String> categories= new ArrayList<String>();
        for(Category category: ArrayListItem.CATEGORIES){
            categories.add(category.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

        this.item = getArguments().getParcelable(Item.class.getSimpleName());
        this.idCategory=getArguments().getInt(ID_CATEGORY);
        if(this.item != null){
            this.settingFieldValues();
        }

        return this.view;
    }

    private void settingFieldValues(){

        TextView nameItem=(TextView) this.view.findViewById(R.id.nameItem);
        nameItem.setText(""+this.item.name);
        TextView time=(TextView) this.view.findViewById(R.id.time);
        time.setText(""+this.item.time);
        TextView url=(TextView) this.view.findViewById(R.id.url);
        url.setText(""+this.item.url);
        int idCategory=ArrayListItem.searchIndexCategoryByIdCategory(this.idCategory);
        if(idCategory!=-1){
            this.spinner.setSelection(idCategory);
        }
    }

    public String getCategory(){
        try {
            return ((Spinner) this.view.findViewById(R.id.categories)).getSelectedItem().toString();
        }catch (Exception e){
            return this.item!=null?""+this.idCategory:"";
        }
    }

    public String getNameItem(){
        try {
            return ((TextView) this.view.findViewById(R.id.nameItem)).getText().toString();
        }catch (Exception e){
            return this.item.name!=""?this.item.name:"";
        }
    }

    public String getTime(){
        try {
            return ((TextView) this.view.findViewById(R.id.time)).getText().toString();
        }catch (Exception e){
            return this.item!=null?""+this.item.time:"";
        }
    }

    public String getUrl(){
        try {
            return ((TextView) this.view.findViewById(R.id.url)).getText().toString();
        }catch (Exception e){
            return this.item.url!=""?this.item.url:"";
        }
    }

}