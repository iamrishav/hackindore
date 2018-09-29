package com.example.shailendra.drinkapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shailendra.drinkapp.Interface.ItemHoldClick;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.R;
import com.example.shailendra.drinkapp.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shailendra on 5/24/2018.
 */


//click on DrinkViewHolder & click on red bulb & click on imlements methods
public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder>  {

    Context context;
    List<Drink> drinkList;

    int row_index = -1;  //default no row choose

    // generate & click on constructor


    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }



    @Override
    public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.drink_item_layout,null);

        return new DrinkViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder( DrinkViewHolder holder,  int position) {

       // holder.txt_price.setText(new StringBuilder("₹").append(drinkList.get(position).Price).toString());
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(holder.img_product);


        for (int i=0 ;i<5;i++) {
            Drink drink = new Drink();
            if (i%2==drinkList.size())
                drink.setChecked(true);
            else
                drink.setChecked(false);

        }

        holder.setItemClickListener(new ItemHoldClick() {
    @Override
    public void onClick(View v, int position) {
        row_index = position; ///set row index to selected position
        Common.currentSelect = drinkList.get(position);  // set current item is item selection
        notifyDataSetChanged(); // made effect on Recycler View's Adapter
    }
});

//set highlight color
        if (row_index==position)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#F8F8FA"));
            holder.txt_drink_name.setTextColor(Color.parseColor("#c5c6c7"));

        }
        else
        {
           // holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.txt_drink_name.setTextColor(Color.parseColor("#000000"));
        }



    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
