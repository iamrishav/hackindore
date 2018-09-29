package com.example.shailendra.drinkapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shailendra.drinkapp.Model.Noti;
import com.example.shailendra.drinkapp.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotiViewHolder> {

    Context context;
    List<Noti> NotiList ;

    public NotificationAdapter(Context context, List<Noti> notiList) {
        this.context = context;
        NotiList = notiList;
    }

    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.notification_layout,null);

        return new NotiViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {

        holder.txt_title.setText(NotiList.get(position).Title);
        holder.txt_description.setText(NotiList.get(position).Description);

    }

    @Override
    public int getItemCount() {
        return NotiList.size();
    }
}
