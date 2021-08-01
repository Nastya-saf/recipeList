package com.example.recipelist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;


import java.util.ArrayList;

public class EditCategoryListFragment extends DialogFragment implements DialogInterface.OnClickListener {

    View view;
    public ArrayList<CategoryForAdapter> listAdapter=new ArrayList<CategoryForAdapter>();

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.fragment_edit_category_list, null);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        for(Category category: ArrayListItem.CATEGORIES){
            this.listAdapter.add(new CategoryForAdapter(category));
        }

        this.updateList();

        Button buttonDelete = (Button) this.view.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategory();
            }
        });

        return builder
                .setView(view)
                .setPositiveButton("OK", this)
                .setNegativeButton("Отмена", this)
                .create();
    }

    private void updateList(){
        ListView productList = (ListView) this.view.findViewById(R.id.productList);
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), R.layout.edit_category_list, this.listAdapter);
        productList.setAdapter(adapter);
    }

    private void deleteCategory(){
        for(int i=0;i<this.listAdapter.size();i++){
            if(this.listAdapter.get(i).selected){
                ArrayListItem.deleteCategory(this.listAdapter.get(i).id);
                this.listAdapter.remove(i);
            }
        }
        this.saveToJSON();
        this.updateList();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                ArrayListItem.CATEGORIES.clear();
                for(Category category: this.listAdapter){
                    ArrayListItem.CATEGORIES.add(category);
                }
                this.saveToJSON();
                ((MainActivity)getActivity()).clickCreateCategoryDialog();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    }

    public void saveToJSON(){
        boolean result = JSON.exportCategoriesToJSON((MainActivity)getActivity(), ArrayListItem.CATEGORIES);
        if(result){
            Toast.makeText((MainActivity)getActivity(), "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText((MainActivity)getActivity(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }
}