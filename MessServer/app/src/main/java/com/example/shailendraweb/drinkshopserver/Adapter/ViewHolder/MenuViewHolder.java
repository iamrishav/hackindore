package com.example.shailendraweb.drinkshopserver.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shailendraweb.drinkshopserver.Interface.IItemClickListener;
import com.example.shailendraweb.drinkshopserver.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    public ImageView img_product;
    public TextView txt_product;

    IItemClickListener iItemClickListener;

    //generate setter
    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.img_product);
        txt_product = (TextView)itemView.findViewById(R.id.txt_menu_name);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        iItemClickListener.onClick(view , false);
    }

    @Override
    public boolean onLongClick(View view) {
      iItemClickListener.onClick(view , true);
        return true;
    }
}
