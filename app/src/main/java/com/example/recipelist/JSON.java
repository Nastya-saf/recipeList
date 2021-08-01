package com.example.recipelist;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSON {
    private static final String FILE_NAME_LIST_ITEMS = "listItems.json";
    private static final String FILE_NAME_LIST_CATEGORIES = "category.json";

    static boolean exportListItemsToJSON(Context context, ArrayList<ListItem> dataList){
        Gson gson = new Gson();
        DataListItems dataItems = new DataListItems();
        dataItems.setItems(dataList);
        String jsonString = gson.toJson(dataItems);
        return exportData(context, jsonString,FILE_NAME_LIST_ITEMS);
    }

    static boolean exportCategoriesToJSON(Context context, ArrayList<Category> dataList){
        Gson gson = new Gson();
        DataCategories dataItems = new DataCategories();
        dataItems.setItems(dataList);
        String jsonString = gson.toJson(dataItems);
        return exportData(context, jsonString,FILE_NAME_LIST_CATEGORIES);
    }

    static boolean exportData(Context context, String jsonString,String fileName){

        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    static ArrayList<ListItem> importListItemFromJSON(Context context){
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME_LIST_ITEMS);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataListItems dataItems = gson.fromJson(streamReader, DataListItems.class);
            return  dataItems.getItems();
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static ArrayList<Category> importCategoriesFromJSON(Context context){
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME_LIST_CATEGORIES);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataCategories dataItems = gson.fromJson(streamReader, DataCategories.class);
            return  dataItems.getItems();
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static class DataListItems extends DataItems<ListItem>{};
    private static class DataCategories extends DataItems<Category>{};

    private static class DataItems<T> {
        private ArrayList<T> listItem;

        ArrayList<T>getItems() {
            return listItem;
        }
        void setItems(ArrayList<T> listItem) {
            this.listItem = listItem;
        }
    }
}


