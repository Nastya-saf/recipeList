package com.example.recipelist;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import static com.example.recipelist.ItemPageActivity.ID_CATEGORY;

public class CreateItemActivity extends AppCompatActivity {

    public Item item=null;
    public int idCategory;
    private boolean isEdit=false;

    CreateItemMainFragment mainFragment;
    CreateListIngredientsFragment listIngredientsFragment;
    CreateItemActionsFragment actionsFragment;
    CreateItemImageFragment imageFragment;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        this.mainFragment = new CreateItemMainFragment();
        this.listIngredientsFragment = new CreateListIngredientsFragment();
        this.actionsFragment = new CreateItemActionsFragment();
        this.imageFragment= new CreateItemImageFragment();

        Intent intent = getIntent();
        if(intent!=null){

            this.idCategory = intent.getIntExtra(ID_CATEGORY,0);
            this.item=(Item)intent.getParcelableExtra(Item.class.getSimpleName());
            this.isEdit=this.item!=null?true:false;

            this.settingBundleForFragments();
        }

        this.installationFragment();
    }

    private void settingBundleForFragments(){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Item.class.getSimpleName(), this.item);
        bundle.putInt(ID_CATEGORY, this.idCategory);

        this.mainFragment.setArguments(bundle);
        this.listIngredientsFragment.setArguments(bundle);
        this.actionsFragment.setArguments(bundle);
        this.imageFragment.setArguments(bundle);

    }

    public void onClick(View view) {
        this.installationFragment(view.getId());
    }

    private void installationFragment(int id){

        ft = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.CreateIngredients:
                ft.replace(R.id.FrameContainer, listIngredientsFragment);
                break;
            case R.id.CreateActions:
                ft.replace(R.id.FrameContainer, actionsFragment);
                break;
            case R.id.CreateMain:
                ft.replace(R.id.FrameContainer, mainFragment);
                break;
            case R.id.CreateImage:
                ft.replace(R.id.FrameContainer, imageFragment);
            default:
                break;
        }
        ft.commit();
    }

    private void installationFragment(){
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameContainer, mainFragment);
        ft.commit();
    }

    public void save(View view){

        String nameItemTextView = this.mainFragment.getNameItem();
        String timeTextView =this.mainFragment.getTime();
        String urlTextView = this.mainFragment.getUrl();
        String category = this.mainFragment.getCategory();
        String actionsTextView = "";

        try{
            actionsTextView = this.actionsFragment.getActions();
        }catch (Exception e){
            if(this.isEdit)
                actionsTextView=this.isEdit?this.item.actions:"";
        }

        String imgUrl = "";
        try{
            imgUrl = this.imageFragment.getImg();
        }catch (Exception e){
            if(this.isEdit)
                imgUrl=this.isEdit?this.item.img:"";
        }

        ArrayList<Ingredient> listIngredients=null;
        if(this.listIngredientsFragment.listIngredients.size()==0 && this.isEdit){
            listIngredients=this.item.ingredients;
        }else{
            listIngredients=this.listIngredientsFragment.listIngredients;
        }

        if(
                nameItemTextView.length()==0 ||
                        timeTextView.length()==0 ||
                        category.length()==0 ||
                        actionsTextView.length()==0 ||
                        listIngredients==null){
            Toast toast = Toast.makeText(this, "Не заполнены поля !!!!!",Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        int idCategory=ArrayListItem.searchIdCategory(category);
        if(idCategory==-1){
            idCategory=ArrayListItem.addCategory(category);
        }

        Item item = new Item(this.listIngredientsFragment.listIngredients,
                nameItemTextView,
                imgUrl,
                actionsTextView,
                timeTextView,
                urlTextView);

        if(this.isEdit){
            if(this.idCategory==idCategory){
                ArrayListItem.updateItem(this.idCategory,item,this.item.id);
            }else{
                ArrayListItem.deleteItem(this.item.id,this.idCategory);
                ArrayListItem.addListItem(idCategory,item);
            }

        }else{
            ArrayListItem.addListItem(idCategory,item);
        }

        this.item=item;
        this.idCategory=idCategory;

        this.saveToJSON();
        this.back();
    }

    public void saveToJSON(){
        boolean result = JSON.exportListItemsToJSON(this, ArrayListItem.listItems);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }

    public void cancel(View view){
        this.back();
    }

    private void back(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}