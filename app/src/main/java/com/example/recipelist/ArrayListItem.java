package com.example.recipelist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ArrayListItem {
    public static ArrayList<Category> CATEGORIES = new ArrayList<Category>();
    public static ArrayList<ListItem> listItems = new ArrayList<ListItem>();

    public static int addCategory(String nameCategory) {
        int idCategory;
        if(CATEGORIES==null){
            idCategory=0;
            ArrayListItem.CATEGORIES=new ArrayList<Category>();
        }else{
            idCategory= CATEGORIES.size();
        }
        Category category = new Category(nameCategory, idCategory);
        CATEGORIES.add(category);
        return CATEGORIES.size();
    }

    public static String searchNameCategory(int idCategory) {
        for (Category category : CATEGORIES) {
            if (category.id == idCategory) {
                return category.name;
            }
        }
        return "";
    }

    public static int searchIdCategory(String nameCategory) {
        int idCategory = -1;
        for (Category category : CATEGORIES) {
            if (category.name == nameCategory) {
                idCategory = category.id;
                break;
            }
        }
        return idCategory;
    }

    public static int searchIndexListItemsByIdCategory(int idCategory) {
        if(ArrayListItem.listItems==null){
            ArrayListItem.listItems=new ArrayList<ListItem>();
            return -1;
        }
        for(int i=0;i<ArrayListItem.listItems.size();i++) {
            if(ArrayListItem.listItems.get(i).idCategory==idCategory){
                return i;
            }
        }
        return -1;
    }

    public static int searchIndexCategoryByIdCategory(int idCategory){
        for(int i=0;i<ArrayListItem.CATEGORIES.size();i++){
            if(ArrayListItem.CATEGORIES.get(i).id==idCategory){
                return i;
            }
        }
        return -1;
    }

    public static boolean updateItem(int idCategory, Item item,int idItem){
        int indexListItem=ArrayListItem.searchIndexListItemsByIdCategory(idCategory);

        if(indexListItem!=-1){
            return ArrayListItem.listItems.get(indexListItem).updateItem(item,idItem);
        }else{
            return false;
        }
    }

    public static void addListItem(int Category, Item item){
        int indexCategory=ArrayListItem.searchIndexListItemsByIdCategory(Category);
        if(indexCategory!=-1){
            ArrayListItem.listItems.get(indexCategory).addItem(item);
        }else {
            ArrayListItem.listItems.add(new ListItem(Category,item));
        }
    }

    public static List<String> categoriesToString(){
        List<String> listCategories=new ArrayList<String>();
        for(Category category: CATEGORIES){
            listCategories.add(category.name);
        }
        return listCategories;
    }

    public static String stringForCategories(){
        String str="";
        for(Category category: CATEGORIES){
            str+=" name: "+category.name+" id: "+category.id;
        }
        return str;
    }

    public static Item searchItem(int idItem,int idCategory){
        Item foundItem = null;
        int indexCategory=ArrayListItem.searchIndexListItemsByIdCategory(idCategory);
        if(indexCategory!=-1){
            foundItem = ArrayListItem.listItems.get(indexCategory).searchItem(idItem);
        }

        return foundItem;
    }

    public static boolean deleteItem(int idItem,int idCategory){
        try{
            int indexCategory=ArrayListItem.searchIndexListItemsByIdCategory(idCategory);
            if(indexCategory!=-1){
                return ArrayListItem.listItems.get(indexCategory).deleteItem(idItem);
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean deleteCategory(int idCategory){
        try{
            int indexCategory=ArrayListItem.searchIndexCategoryByIdCategory(idCategory);
            if(indexCategory!=-1){
                ArrayListItem.CATEGORIES.remove(indexCategory);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
