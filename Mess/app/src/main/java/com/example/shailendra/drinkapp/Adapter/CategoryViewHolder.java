package com.example.shailendra.drinkapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shailendra.drinkapp.Interface.IItemClickListener;
import com.example.shailendra.drinkapp.R;

/**
 * Created by shailendra on 5/23/2018.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img_product;
    TextView txt_menu_name;

    IItemClickListener itemClickListener;

    //click generate nd click on Setter & click itemclicklistener.IIte...


    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.image_product);
        txt_menu_name = (TextView) itemView.findViewById(R.id.txt_menu_name);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v);
    }
}
