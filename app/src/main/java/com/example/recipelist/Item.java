package com.example.recipelist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Item implements Parcelable {
    public int id=-1;
    public String name;
    public String img;
    public ArrayList<Ingredient> ingredients=new ArrayList<Ingredient>();
    public String actions;
    public String time;
    public String url;

    Item(ArrayList<Ingredient> ingredients,String name, String img, String actions, String time,String url, int id){
        this.id=id;
        this.name=name;
        this.img=img;
        this.actions=actions;
        this.time=time;
        this.url=url;

        this.ingredients=ingredients;
    }

    Item(ArrayList<Ingredient> ingredients,String name, String img, String actions, String time,String url){
        this.id=-1;
        this.name=name;
        this.img=img;
        this.actions=actions;
        this.time=time;
        this.url=url;

        this.ingredients=ingredients;
    }

    Item(String name, String img, String actions, String time,String url, int id){
        this.id=id;
        this.name=name;
        this.img=img;
        this.actions=actions;
        this.time=time;
        this.url=url;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    public boolean update(Item item){
        try {
            this.name = item.name;
            this.img = item.img;
            this.actions = item.actions;
            this.time = item.time;
            this.url = item.url;
            this.ingredients = item.ingredients;
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public  String toString(){
        return "Название:"+this.name+" id: "+this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeString(this.img);
        dest.writeString(this.actions);
        dest.writeString(this.time);
        dest.writeString(this.url);
        if (this.ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(this.ingredients);
        }
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            String name=source.readString();
            int id=source.readInt();
            String img=source.readString();
            String actions=source.readString();
            String time=source.readString();
            String url= source.readString();

            ArrayList<Ingredient> ingredients;
            if (source.readByte() == 0x01) {
                ingredients = new ArrayList<Ingredient>();
                source.readList(ingredients, Ingredient.class.getClassLoader());
            } else {
                ingredients = null;
            }
            return new Item(ingredients,name, img, actions, time, url, id);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public boolean deleteIngredient(Ingredient ingredient){
        int position = searchPositionIngredientByItem(ingredient);
        if(position!=-1){
            this.ingredients.remove(position);
            return true;
        }else{
            return false;
        }
    }

    private int searchPositionIngredientByItem(Ingredient ingredient){
        if(this.ingredients==null){
            return -1;
        }
        for(int i=0;i<this.ingredients.size();i++){
            Ingredient currentIngredient = this.ingredients.get(i);
            if(currentIngredient.id==ingredient.id){
                return i;
            }
        }
        return -1;
    }

    public String sendItem(){

        String textSend=this.name+"\r\n Время выполнения: "+this.time+"\r\n";
        textSend+=this.url.length()!=0?"Ссылка: "+this.url+"\r\n":"";
        textSend+="Ингредиенты:\r\n"+this.sendIngredients();
        textSend+="Рецепт:\r\n"+this.actions;
        return textSend;
    }

    private String sendIngredients(){
        String text="";
        for(Ingredient ingredient: this.ingredients){
            text+=ingredient+"\r\n";
        }
        return text;
    }
}