package com.example.shailendraweb.drinkshopserver.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shailendraweb.drinkshopserver.Interface.IItemClickListener;
import com.example.shailendraweb.drinkshopserver.R;

public class DrinkListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public ImageView img_product;
    public TextView txt_drink_name , txt_price;

    IItemClickListener iItemClickListener;

    //setter for itemclicklistner

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public DrinkListViewHolder(View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.img_product);
        txt_drink_name = (TextView)itemView.findViewById(R.id.txt_drink_name);
        txt_price = (TextView)itemView.findViewById(R.id.txt_price);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        iItemClickListener.onClick(view , false);
    }
}
