package com.example.shailendra.drinkapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shailendra.drinkapp.R;

public class NotiViewHolder extends RecyclerView.ViewHolder {
    TextView txt_title , txt_description;

    public NotiViewHolder(View itemView) {
        super(itemView);

     txt_title = (TextView)itemView.findViewById(R.id.txt_title);
     txt_description = (TextView)itemView.findViewById(R.id.txt_description);
    }
}
