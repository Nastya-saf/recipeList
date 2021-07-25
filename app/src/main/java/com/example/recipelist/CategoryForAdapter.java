package com.example.recipelist;

public class CategoryForAdapter extends Category{

    public boolean selected;

    CategoryForAdapter(Category category){
        super(category.name,category.id);
        this.selected=false;
    }

    @Override
    public  String toString(){
        return "{id: "+this.id+" Название:"+this.name+" selected: "+this.selected+" }";
    }


}