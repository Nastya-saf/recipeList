package com.example.recipelist;


import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable{
    public static int SIZE=0;
    public int id=-1;
    public String name="";
    public String quantity="";

    public Ingredient(String name,String quantity){
        this.name=name;
        this.quantity=quantity;
        Ingredient.SIZE++;
        this.id=Ingredient.SIZE;
    }

    public Ingredient(int id,String name,String quantity){
        this.name=name;
        this.quantity=quantity;
        this.id=id;
    }

    public Ingredient(Ingredient ingredient) {
        this.name=ingredient.name;
        this.quantity=ingredient.quantity;
        this.id=ingredient.id;
    }

    @Override
    public  String toString(){
        return ""+this.name+" - "+this.quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.quantity);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            int id=source.readInt();
            String name=source.readString();
            String quantity=source.readString();
            return new Ingredient(id,name, quantity);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
