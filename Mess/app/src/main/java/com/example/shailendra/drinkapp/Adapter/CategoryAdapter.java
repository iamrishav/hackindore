package com.example.shailendra.drinkapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shailendra.drinkapp.DrinkActivity;
import com.example.shailendra.drinkapp.Interface.IItemClickListener;
import com.example.shailendra.drinkapp.Model.Category;
import com.example.shailendra.drinkapp.R;
import com.example.shailendra.drinkapp.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shailendra on 5/23/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    //implement categoryviewHolder methods by click on red bulb
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,null);
        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
             //Load Image
        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        //Event on item click
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory = categories.get(position);


                //start new ACtivity
              context.startActivity(new Intent(context, DrinkActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
