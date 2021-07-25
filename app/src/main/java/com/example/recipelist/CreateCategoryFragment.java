package com.example.recipelist;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateCategoryFragment extends DialogFragment implements DialogInterface.OnClickListener {

    View view;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.fragment_create_category, null);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());


        return builder
                .setView(view)
                .setPositiveButton("OK", this)
                .setNegativeButton("Отмена", this)
                .create();
    }

    public void saveCategory(){
        try{
            String nameCategory=((TextView) this.view.findViewById(R.id.nameCategory)).getText().toString();
            ArrayListItem.addCategory(nameCategory);
            this.saveToJSON();
        }catch (Exception e) {
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

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                this.saveCategory();
                ((MainActivity)getActivity()).clickCreateCategoryDialog();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    }
}