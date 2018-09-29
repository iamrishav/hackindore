package com.example.shailendra.drinkapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shailendra.drinkapp.Utils.Common;
import com.squareup.picasso.Picasso;

public class NewActivity extends AppCompatActivity {
    public ImageView imageView;
    public TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_item_layout);

        textView = (TextView)findViewById(R.id.txt_drink_name);
        imageView = (ImageView)findViewById(R.id.image_product);

        textView.setText(Common.currentSelect.getName());
        Picasso.with(this)
                .load(Common.currentSelect.Link)
                .into(imageView);
    }
}
