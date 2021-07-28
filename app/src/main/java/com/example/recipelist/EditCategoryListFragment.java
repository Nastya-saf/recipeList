package com.example.recipelist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Log.d("","---------- !!!!!! --------- list: "+this.listAdapter);

        this.updateList();

        Button buttonDelete = (Button) this.view.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","------------- !!!!! onClick DELETE !!!!! -------------");
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
        Log.d("","--------  UPDATE  this.listAdapter: "+this.listAdapter);
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), R.layout.edit_category_list, this.listAdapter);
        productList.setAdapter(adapter);
    }

    private void deleteCategory(){
        Log.d("","------------- !!!!! deleteCategory !!!!! -------------");
        for(int i=0;i<this.listAdapter.size();i++){
            if(this.listAdapter.get(i).selected){
                Log.d("","--- IF --- selected: "+this.listAdapter.get(i).selected+" id: "+this.listAdapter.get(i).id);
                ArrayListItem.deleteCategory(this.listAdapter.get(i).id);
                this.listAdapter.remove(i);
            }
        }
        this.updateList();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                ArrayListItem.CATEGORIES.clear();
                Log.d("","-------------------- !!! BUTTON_POSITIVE: "+this.listAdapter);
                for(CategoryForAdapter category: this.listAdapter){
                    ArrayListItem.CATEGORIES.add((Category) category);
                }
//                this.editCategories();
                ((MainActivity)getActivity()).clickCreateCategoryDialog();
                this.saveToJSON();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    }

//    public void editCategories(){
//        ListView listView = (ListView)getActivity().findViewById(R.id.productList);
//        ArrayList<String> arraylist = new ArrayList<String>();
//        for (int a = 0; a < listView.getCount(); a++) {
//            EditText et = (EditText) listView.getChildAt(a).findViewById(R.id.name);
////            string[a] = et.getText().toString();
//            arraylist.add(et.getText().toString());
//        }
//        Log.d("","--------- EDIT arraylist: "+arraylist);
//    }

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

//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//
//public class EditCategoryListFragment extends DialogFragment implements DialogInterface.OnClickListener {
//
//    View view;
//    public ArrayList<CategoryForAdapter> listAdapter=new ArrayList<CategoryForAdapter>();
//
//    @NonNull
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        this.view = inflater.inflate(R.layout.fragment_edit_category_list, null);
//
//        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//
//        for(Category category: ArrayListItem.CATEGORIES){
//            this.listAdapter.add(new CategoryForAdapter(category));
//        }
//        Log.d("","---------- !!!!!! --------- list: "+this.listAdapter);
//
//        this.updateList();
//
//        Button buttonDelete = (Button) this.view.findViewById(R.id.buttonDelete);
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("","------------- !!!!! onClick DELETE !!!!! -------------");
//                deleteCategory();
//            }
//        });
//
//        return builder
////                .setTitle("Диалоговое окно")
////                .setMessage("Для закрытия окна нажмите ОК")
//                .setView(view)
////                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        Log.d("","---------- setPositiveButton ---------------");
////                        saveCategory();
////
////                    }
////                })
////                .setNegativeButton("Отмена", null)
//                .setPositiveButton("OK", this)
//                .setNegativeButton("Отмена", this)
//                .create();
//    }
//
//    private void updateList(){
//        ListView productList = (ListView) this.view.findViewById(R.id.productList);
//        Log.d("","-------- this.listAdapter: "+this.listAdapter);
//        CategoryAdapter adapter = new CategoryAdapter(getActivity(), R.layout.edit_category_list, this.listAdapter);
//        productList.setAdapter(adapter);
//    }
//
//    private void deleteCategory(){
//        Log.d("","------------- !!!!! deleteCategory !!!!! -------------");
//        for(int i=0;i<this.listAdapter.size();i++){
//            if(this.listAdapter.get(i).selected){
//                Log.d("","--- IF --- selected: "+this.listAdapter.get(i).selected+" id: "+this.listAdapter.get(i).id);
//                ArrayListItem.deleteCategory(this.listAdapter.get(i).id);
////                ArrayListItem.CATEGORIES.remove(i);
//                this.listAdapter.remove(i);
//            }
//        }
//        this.updateList();
//    }
//
//    public void onClick(DialogInterface dialog, int which) {
//        switch (which) {
//            case Dialog.BUTTON_POSITIVE:
//                Log.d("","---------------- BUTTON_POSITIVE ----------------");
//                ArrayListItem.CATEGORIES.clear();
//                for(CategoryForAdapter category: this.listAdapter){
//                    ArrayListItem.CATEGORIES.add((Category) category);
//                }
//                Log.d("","----------- this.listAdapter: "+this.listAdapter);
//                Log.d("","----------- ArrayListItem.CATEGORIES: "+ArrayListItem.CATEGORIES);
//
////                ArrayListItem.CATEGORIES=(ArrayList<Category>) this.listAdapter;
//                ((MainActivity)getActivity()).clickCreateCategoryDialog();
//                this.saveToJSON();
//                break;
//            case Dialog.BUTTON_NEGATIVE:
//                Log.d("","---------------- BUTTON_NEGATIVE ----------------");
//                break;
//            case Dialog.BUTTON_NEUTRAL:
//                Log.d("","---------------- BUTTON_NEUTRAL ----------------");
//                break;
//        }
//    }
//
//    public void saveToJSON(){
//        boolean result = JSON.exportCategoriesToJSON((MainActivity)getActivity(), ArrayListItem.CATEGORIES);
//        if(result){
//            Toast.makeText((MainActivity)getActivity(), "Данные сохранены", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText((MainActivity)getActivity(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
//        }
//    }
//}