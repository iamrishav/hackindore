package com.example.shailendraweb.drinkshopserver.Interface;

import android.view.View;

public interface IItemClickListener {

    //for item click
    //on single click open products
    //on long click open update/delete dialog

    void onClick(View view , boolean isLongClick );
}
