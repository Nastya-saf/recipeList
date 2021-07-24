package com.example.recipelist;


import java.util.*;

class Category {

    public String name="";
    public int id=0;

    public Category(String name,int id){
        this.name=name;
        this.id=id;
    }

    public String toString(){
        return "{id: "+id+" name: "+name+"} ";
    }
}

public class ListItem {

    public int idCategory;
    public ArrayList<Item> items=new ArrayList<Item>();
    public static int lengthItem=0;


    ListItem(int idCategory){
        this.idCategory=idCategory;
    }

    ListItem(int idCategory,Item item){
        this.idCategory=idCategory;
        this.addItem(item);
    }

    public int addItem(Item item){
        item.id=ListItem.lengthItem++;
        this.items.add(item);
        return item.id;
    }

    public boolean updateItem(Item item, int id){
        int index=this.searchIndexItemByIdItem(id);
        if(index!=-1){
            return this.items.get(index).update(item);
        }else{
            return false;
        }
    }

    @Override
    public  String toString(){
        String strItems="";
        try {
            for( Item item: this.items){
                strItems+="\n\r"+item.toString();
            }

        }catch (Exception e){
            strItems+=" ------- ERROR ------- ";
        }

        return "Категория: " + this.idCategory + " Рецепты\n\r" + strItems;
    }

    public int searchIndexItemByIdItem(int idItem) {
        for(int i=0;i< this.items.size();i++) {
            if(this.items.get(i).id==idItem){
                return i;
            }
        }
        return -1;
    }

    public boolean deleteItem(int idItem){
        int indexItem=this.searchIndexItemByIdItem(idItem);
        if(indexItem!=-1){
            this.items.remove(indexItem);
            return true;
        }else{
            return false;
        }
    }

    public Item searchItem(int idItem){
        Item foundItem = null;
        for(Item item : this.items) {
            if (item.id == idItem) {
                foundItem = item;
            }
        }
        return foundItem;
    }
}


