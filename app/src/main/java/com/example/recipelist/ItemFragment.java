package com.example.recipelist;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.example.recipelist.ItemPageActivity.ID_CATEGORY;


public class ItemFragment extends Fragment implements View.OnClickListener {

    Item item;
    int idCategory;

    public ItemFragment(){
        super(R.layout.fragment_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_item,  container, false);
        view.setOnClickListener(this);

        if(getArguments() != null){
            this.item = getArguments().getParcelable(Item.class.getSimpleName());
            this.idCategory=getArguments().getInt(ID_CATEGORY);

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(this.item.name);

            Picasso.get()
                    .load(Uri.parse(this.item.img))
                    .placeholder (R.drawable.ic_baseline_local_dining_24)
                    .error (R.drawable.ic_baseline_local_dining_24)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into((ImageView) view.findViewById(R.id.img));
        }

        return view;
    }


    @Override
    public void onClick(View view){
        ((MainActivity)getActivity()).onClick(this.item.id,this.idCategory);
    }
}
