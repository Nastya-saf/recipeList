package com.example.recipelist;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    private ListView drawerList;
    NavigationView navigationView;
    private static String titleHome="HOME";

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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        this.navigationView.setNavigationItemSelectedListener(this);

        this.initCategoryList();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        Log.d("","--------------- Selected  item: "+item+" id: "+item.getItemId()+"   ---------------");
        switch (item.getItemId()) {

            case R.id.nav_home:
                Log.d("","--------- nav_home ---------");
                return true;
            case R.id.nav_gallery:
                Log.d("","--------- nav_gallery ---------");
                return true;
            case R.id.nav_slideshow:
                Log.d("","--------- nav_slideshow ---------");
                return true;
            default:
                return true;
        }

//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        this.drawer.closeDrawer(GravityCompat.START);
//        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.createAction:
//                if(ArrayListItem.CATEGORIES==null) {
//                    Toast.makeText(this, R.string.main_message_add_category, Toast.LENGTH_LONG).show();
//                }else{
//                    this.addItem();
//                }
                Log.d("","--------- createAction ---------");
                return true;
            case R.id.receiveData:
//                this.receiveData();
                Log.d("","--------- receiveData ---------");
                return true;
            case R.id.createCategory:
//                this.showDialogCreateCategory();
                Log.d("","--------- createCategory ---------");
                return true;
            case R.id.editCategoryList:
//                if(ArrayListItem.CATEGORIES==null) {
//                    Toast.makeText(this, R.string.main_message_add_category, Toast.LENGTH_LONG).show();
//                }else{
//                    this.showDialogEditCategoryList();
//                }
                Log.d("","--------- editCategoryList ---------");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initCategoryList(){
        Log.d("","--------- initCategoryList ---------");

        String[] menuList={this.titleHome,"category-1","category-2","category-3","category-4"};

        final Menu menu = this.navigationView.getMenu();
        Log.d("","--------- MENU 1---------");
        for (int i = 1; i<menuList.length; i++) {
//            menu.add("Runtime item "+ i);
            menu.add(Menu.NONE, i, Menu.NONE, menuList[i]);
        }
        Log.d("","--------- MENU 2---------");
        for (int i = 0, count = this.navigationView.getChildCount(); i < count; i++) {
            final View child = this.navigationView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }

        Log.d("","--------- MENU 3---------");

//        try{
//            List<String> menuList=ArrayListItem.categoriesToString();

//            String[] menuList={this.titleHome,"category-1","category-2","category-3","category-4"};
//
//            final Menu menu = this.navigationView.getMenu();
//            for (int i = 1; i <= 3; i++) {
//                menu.add("Runtime item "+ i);
//            }
//
//            Log.d("","--------- MENU ---------");

//            menuList.add(0,this.titleHome);

//            this.drawerList = (ListView)findViewById(R.id.listCategory);
//            this.drawerList.setAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_activated_1, menuList));
//            this.drawerList.setOnItemClickListener(new DrawerItemClickListener());

//        }catch (Exception e){}


    }

}
