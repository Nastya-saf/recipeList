package com.example.recipelist;


import android.widget.TextView;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateItemActionsFragment extends Fragment {

    private View view;

    private Item item;

    private boolean isShow=false;

    public static CreateItemActionsFragment newInstance() {
        return new CreateItemActionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view=inflater.inflate(R.layout.fragment_create_item_actions, container, false);
        this.item = getArguments().getParcelable(Item.class.getSimpleName());
        this.isShow=true;
        if(this.item != null) {
            this.settingFieldValues();
        }

        return this.view;
    }

    private void settingFieldValues(){
        TextView actions=(TextView) this.view.findViewById(R.id.actions);
        actions.setText(""+this.item.actions);
    }

    public String getActions(){
        try {
            return ((TextView) this.view.findViewById(R.id.actions)).getText().toString();
        }catch (Exception e){
            return this.item!=null?this.item.actions:"";
        }
    }

}