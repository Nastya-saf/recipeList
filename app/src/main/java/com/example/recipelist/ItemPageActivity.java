package com.example.recipelist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ItemPageActivity extends AppCompatActivity {

    public static final String ID_CATEGORY="idCategory";
    public Item item;
    public int idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        this.idCategory = intent.getIntExtra(ID_CATEGORY,0);
        this.item=(Item)intent.getParcelableExtra(Item.class.getSimpleName());
        this.initText();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editItem:
                this.editItem();
                return true;
            case R.id.deleteItem:
                this.deleteItem();
                return true;
            case R.id.sendItem:
                this.send();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void send(){
        String textSend=ArrayListItem.searchItem(this.item.id,this.idCategory).sendItem();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textSend);
        Intent chosenIntent = Intent.createChooser(intent, textSend);
        startActivity(chosenIntent);
    }

    private void initText(){
        Picasso.get()
                .load(Uri.parse(this.item.img))
                .placeholder (R.drawable.ic_baseline_local_dining_24)
                .error (R.drawable.ic_baseline_local_dining_24)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into((ImageView) findViewById(R.id.img));

        TextView categoryName = (TextView) findViewById(R.id.category);
        categoryName.setText(""+ArrayListItem.searchNameCategory(idCategory));

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(""+item.name);

        TextView time = (TextView) findViewById(R.id.time);
        time.setText(""+item.time);

        TextView url = (TextView) findViewById(R.id.url);
        url.setText(""+item.url);

        TextView actions = (TextView) findViewById(R.id.actions);
        actions.setText(""+item.actions);

        this.initLayoutIngredients();
    }

    private void initLayoutIngredients(){
        GridLayout layoutIngredients = (GridLayout) findViewById(R.id.layoutIngredients);
        layoutIngredients.setColumnCount(3);

        int rowIndex=0;
        GridLayout.Spec row = GridLayout.spec(rowIndex, 1);
        GridLayout.Spec column = GridLayout.spec(0, 1);
        for (Ingredient ingredient:this.item.ingredients){
            TextView textIngredient= new TextView(this);
            textIngredient.setBackgroundColor(0xffe8eaf6);
            textIngredient.setText(ingredient.name);
            row = GridLayout.spec(rowIndex, 1);
            column = GridLayout.spec(0, 1);
            GridLayout.LayoutParams gridLayoutParam1 = new GridLayout.LayoutParams(row,column);
            layoutIngredients.addView(textIngredient,gridLayoutParam1);

            TextView textIngredient2= new TextView(this);
            textIngredient2.setBackgroundColor(0xffe8eaf6);
            textIngredient2.setText("...............................................");
            row = GridLayout.spec(rowIndex, 1);
            column = GridLayout.spec(1, 1);
            GridLayout.LayoutParams gridLayoutParam2 = new GridLayout.LayoutParams(row,column);
            layoutIngredients.addView(textIngredient2,gridLayoutParam2);

            TextView textIngredient3= new TextView(this);
            textIngredient3.setBackgroundColor(0xffe8eaf6);
            textIngredient3.setText(ingredient.quantity);
            row = GridLayout.spec(rowIndex, 1);
            column = GridLayout.spec(2, 1);
            GridLayout.LayoutParams gridLayoutParam3 = new GridLayout.LayoutParams(row,column);
            layoutIngredients.addView(textIngredient3,gridLayoutParam3);

            rowIndex++;

        }
    }

    private void editItem(){
        Log.d("","--------------- editItem ---------------");
//        Intent intent = new Intent(this, CreateItemActivity.class);
//        intent.putExtra(ID_CATEGORY, this.idCategory);
//        intent.putExtra(Item.class.getSimpleName(), this.item);
//        startActivity(intent);
    }

    private void deleteItem(){
        String resultMessage="";
        boolean resultDelete = ArrayListItem.deleteItem(this.item.id,this.idCategory);
        if (resultDelete){
            boolean resultExport = JSON.exportListItemsToJSON(this, ArrayListItem.listItems);
            if(resultExport){
                resultMessage="Данные успешно удалены!";
            }
            else{
                ArrayListItem.addListItem(this.idCategory,this.item);
                resultMessage="Ошибка удаления!";
            }
        }else{
            resultMessage="Ошибка удаления!";
        }
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        this.back();
    }

    private void back(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }



}