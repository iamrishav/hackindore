package com.example.shailendraweb.drinkshopserver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shailendraweb.drinkshopserver.Adapter.ViewHolder.DrinkListViewHolder;
import com.example.shailendraweb.drinkshopserver.Interface.IItemClickListener;
import com.example.shailendraweb.drinkshopserver.Model.Drink;
import com.example.shailendraweb.drinkshopserver.R;
import com.example.shailendraweb.drinkshopserver.UpdateProductActivity;
import com.example.shailendraweb.drinkshopserver.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkListAdapter extends RecyclerView.Adapter<DrinkListViewHolder> {

  Context context;
  List<Drink> drinkList;


    public DrinkListAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_layout , parent ,false);
        return new DrinkListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkListViewHolder holder, final int position) {

        Picasso.with(context).load(drinkList.get(position).Link).into(holder.img_product);
        holder.txt_price.setText(new StringBuilder("₹").append(drinkList.get(position).Price).toString());
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        //Event on Drink Click
        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, boolean isLongClick) {

                //assign current drink
                Common.currentDrink = drinkList.get(position);

                //start Update product Activity
                context.startActivity(new Intent(context , UpdateProductActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
