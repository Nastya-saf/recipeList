package com.example.recipelist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class CreateItemImageFragment extends Fragment implements View.OnClickListener{

    static final int GALLERY_REQUEST = 1;

    private View view;
    private String img="";
    private ImageView imageView;
    private Item item;

    public CreateItemImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view= inflater.inflate(R.layout.fragment_create_item_image, container, false);

        Button buttonAddImage = (Button) this.view.findViewById(R.id.buttonAddImage);
        buttonAddImage.setOnClickListener(this);

        this.imageView=(ImageView) this.view.findViewById(R.id.imageItem);
        this.item = getArguments().getParcelable(Item.class.getSimpleName());

        this.settingFieldImage();

        return this.view;
    }

    private void settingFieldImage(){
        RequestCreator picasso;

        if(this.item != null){
            picasso=Picasso.get()
                    .load(Uri.parse(this.item.img));
        }else{
            picasso=Picasso.get()
                    .load(R.drawable.ic_baseline_local_dining_24);
        }

        picasso.placeholder (R.drawable.ic_baseline_local_dining_24)
                .error (R.drawable.ic_baseline_local_dining_24)
                .into(this.imageView);
    }

    @Override
    public void onClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }


    public String getImg(){
        return this.img!=""?this.img:this.item.img!=""?this.item.img:"";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImage = null;
        try{
            selectedImage = imageReturnedIntent.getData();
            this.img=selectedImage.toString();
        }catch (Exception e){

        }finally {
            Picasso.get()
                    .load(selectedImage)
                    .placeholder (R.drawable.ic_launcher_foreground)
                    .error (R.drawable.ic_launcher_background)
                    .into(this.imageView);
        }

    }
}