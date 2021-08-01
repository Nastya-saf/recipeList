package com.example.recipelist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.recipelist.ItemPageActivity.ID_CATEGORY;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    List<ItemFragment> listItemFragments=new ArrayList<ItemFragment>();
    private static final int REQUEST_CODE_PERMISSION_READ_STORAGE= 563;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = findViewById(R.id.nav_view);

        this.navigationView.setNavigationItemSelectedListener(this);

        this.checkPermission();

        if (savedInstanceState == null) {
            this.receiveData();
        }

        this.initCategoryList();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        TextView messageText = (TextView) findViewById(R.id.message);
        int indexListItem = ArrayListItem.searchIndexListItemsByIdCategory(id);

        if (indexListItem != -1) {
            ListItem list = ArrayListItem.listItems.get(indexListItem);
            this.initFragment(list);
            getSupportActionBar().setTitle(ArrayListItem.CATEGORIES.get(id).name);
            messageText.setText("");
        } else {
            int size = ArrayListItem.CATEGORIES.size();
            if (id == -1) {
                this.initFragment(ArrayListItem.listItems);
                getSupportActionBar().setTitle(R.string.menu_home);
                messageText.setText("");
            } else {
                this.deleteStackFragment();
                messageText.setText(R.string.main_message_empty_filter);
            }
        }
        this.drawer.closeDrawer(Gravity.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.createAction:
                if(ArrayListItem.CATEGORIES==null) {
                    Toast.makeText(this, R.string.main_message_add_category, Toast.LENGTH_LONG).show();
                }else{
                    this.addItem();
                }
                return true;
            case R.id.receiveData:
                this.receiveData();
                return true;
            case R.id.createCategory:
                this.showDialogCreateCategory();
                return true;
            case R.id.editCategoryList:
                if(ArrayListItem.CATEGORIES==null) {
                    Toast.makeText(this, R.string.main_message_add_category, Toast.LENGTH_LONG).show();
                }else{
                    this.showDialogEditCategoryList();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.updateData();
                } else {
                }
                return;
        }
    }

    private void checkPermission(){
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            this.updateData();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }
    }

    private void initCategoryList(){

        final Menu menu = this.navigationView.getMenu();
        menu.clear();
        try{

            menu.add(Menu.NONE, -1, Menu.NONE, R.string.menu_home);

            for (int i = 0; i<ArrayListItem.CATEGORIES.size(); i++) {
                menu.add(Menu.NONE, ArrayListItem.CATEGORIES.get(i).id, Menu.NONE, ArrayListItem.CATEGORIES.get(i).name);
            }

            for (int i = 0, count = this.navigationView.getChildCount(); i < count; i++) {
                final View child = this.navigationView.getChildAt(i);
                if (child != null && child instanceof ListView) {
                    final ListView menuView = (ListView) child;
                    final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                    final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                    wrapped.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            menu.clear();
        }

    }

    public void onClick(int idItem,int idCategory) {
        Intent intent = new Intent(this, ItemPageActivity.class);
        intent.putExtra(ID_CATEGORY, idCategory);
        Item item=ArrayListItem.searchItem(idItem,idCategory);
        intent.putExtra(Item.class.getSimpleName(), item);
        startActivity(intent);
    }

    private void showDialogCreateCategory(){
        CreateCategoryFragment dialog = new CreateCategoryFragment();
        dialog.show(getSupportFragmentManager(), "");
    }

    private void showDialogEditCategoryList(){
        EditCategoryListFragment dialog = new EditCategoryListFragment();
        dialog.show(getSupportFragmentManager(), "");
    }

    public void clickCreateCategoryDialog(){
        this.initCategoryList();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.updateData();
    }

    private void initFragment(ListItem listItem){
        this.deleteStackFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(Item item : listItem.items){
            ItemFragment itemFragment=new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Item.class.getSimpleName(), item);
            bundle.putInt(ID_CATEGORY, listItem.idCategory);
            itemFragment.setArguments(bundle);
            this.listItemFragments.add(itemFragment);
            ft.add(R.id.container, itemFragment);
        }
        ft.commit();
    }

    private void initFragment(List<ListItem> listItems){

        this.deleteStackFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(ListItem listItem:listItems){
            for(Item item : listItem.items){
                ItemFragment itemFragment=new ItemFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Item.class.getSimpleName(), item);
                bundle.putInt(ID_CATEGORY, listItem.idCategory);
                itemFragment.setArguments(bundle);
                this.listItemFragments.add(itemFragment);
                ft.add(R.id.container, itemFragment);
            }
        }
        ft.commit();
    }

    private void deleteStackFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(ItemFragment item: this.listItemFragments){
            ft.remove(item);
        }
        ft.commit();
    }

    private void updateData(){
        if(ArrayListItem.listItems!=null){
            this.initFragment(ArrayListItem.listItems);
        }
    }

    private void clearData(){
        if(ArrayListItem.listItems!=null){
            ArrayListItem.listItems.clear();
        }
        if(ArrayListItem.CATEGORIES!=null){
            ArrayListItem.CATEGORIES.clear();
        }
    }

    private void receiveData(){
        this.clearData();
        ArrayListItem.listItems = JSON.importListItemFromJSON(this);
        ArrayListItem.CATEGORIES=JSON.importCategoriesFromJSON(this);

        try{
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
            this.initFragment(ArrayListItem.listItems);
        }catch (Exception e){
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }

    private void addItem(){
        Intent intent = new Intent(this, CreateItemActivity.class);
        startActivity(intent);
    }

}
