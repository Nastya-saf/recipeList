package com.example.recipelist;


import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.example.recipelist.ItemPageActivity.ID_CATEGORY;

public class CreateListIngredientsFragment extends Fragment implements View.OnClickListener{
    private int sizeIngredients=0;
    FragmentTransaction ft;
    Item item;
    public ArrayList<Ingredient> listIngredients=new ArrayList<Ingredient>();
    View view;
    public ArrayList<IngredientForAdapter> listAdapter=new ArrayList<IngredientForAdapter>();

    public CreateListIngredientsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_list_ingredients, container, false);

        Button buttonAddFragment = (Button) this.view.findViewById(R.id.buttonAddFragment);
        buttonAddFragment.setOnClickListener(this);

        Button buttonDelete = (Button) this.view.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);

        this.item = getArguments().getParcelable(Item.class.getSimpleName());
        if(this.item != null) {
            this.settingFieldValues();
        }

        return this.view;
    }

    private void settingFieldValues(){
        this.listIngredients=this.item.ingredients;
        this.updateListIngredients();
    }

    public void deleteIngredient(){
        for(int i=0;i<this.listAdapter.size();i++){
            if(this.listAdapter.get(i).selected){
                this.item.deleteIngredient(this.listAdapter.get(i));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAddFragment:
                this.addItemIngredient();
                break;
            case R.id.buttonDelete:
                this.deleteIngredient();
                break;
        }
        this.updateListIngredients();
    }

    private void addItemIngredient(){
        //TODO: добавление ингридиента в список

        TextView nameTextView = (TextView) this.view.findViewById(R.id.name);
        TextView quantityTextView = (TextView) this.view.findViewById(R.id.quantity);

        if(nameTextView.getText().length()==0 || quantityTextView.getText().length()==0){
            Toast toast = Toast.makeText(getActivity(), "Не заполнены поля !!!!!",Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        Ingredient ingredient=new Ingredient(nameTextView.getText().toString(),quantityTextView.getText().toString());
        this.listIngredients.add(ingredient);

        nameTextView.setText("");
        quantityTextView.setText("");
    }

    private void updateListIngredients(){

        this.listAdapter.clear();
        for(Ingredient ingredient: this.listIngredients){
            this.listAdapter.add(new IngredientForAdapter(ingredient));
        }

        ListView productList = (ListView) this.view.findViewById(R.id.productList);
        IngredientsAdapter adapter = new IngredientsAdapter(getActivity(), R.layout.list_ingredients, this.listAdapter);
        productList.setAdapter(adapter);

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("","-------------START this.listIngredients: "+this.listIngredients);
        this.listIngredients.clear();
        for(Ingredient ingredient: this.listAdapter){
            this.listIngredients.add(ingredient);
        }
        Log.d("","------------- this.listAdapter: "+this.listAdapter);
        Log.d("","-------------END this.listIngredients: "+this.listIngredients);
        Log.d("","--------------- ON PAUSE ---------------");
    }

}