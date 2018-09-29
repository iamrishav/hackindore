package com.example.shailendra.drinkapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shailendra.drinkapp.Interface.IItemClickListener;
import com.example.shailendra.drinkapp.Interface.ItemHoldClick;
import com.example.shailendra.drinkapp.R;

import org.w3c.dom.Text;

/**
 * Created by shailendra on 5/24/2018.
 */

public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img_product;
    TextView txt_drink_name , txt_price;

    ItemHoldClick itemClickListener;

    ImageView btn_add_to_cart , btn_favorites;

    //setter last

    public void setItemClickListener(ItemHoldClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public DrinkViewHolder(View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.image_product);
        txt_drink_name = (TextView)itemView.findViewById(R.id.txt_drink_name);

      //  btn_add_to_cart = (ImageView) itemView.findViewById(R.id.btn_add_cart);
       // btn_favorites = (ImageView)itemView.findViewById(R.id.btn_favorite);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v , getAdapterPosition() );
    }
}
