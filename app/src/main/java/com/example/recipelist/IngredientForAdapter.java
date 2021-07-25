package com.example.recipelist;

public class IngredientForAdapter extends Ingredient {

    public boolean selected;

    IngredientForAdapter(Ingredient ingredient){
        super(ingredient.id,ingredient.name,ingredient.quantity);
        this.selected=false;
    }

    @Override
    public  String toString(){
        return "id: "+this.id+" Название:"+this.name+" Количество: "+this.quantity+" selected: "+this.selected;
    }

}
