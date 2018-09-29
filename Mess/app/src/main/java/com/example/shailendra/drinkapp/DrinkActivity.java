package com.example.shailendra.drinkapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shailendra.drinkapp.Adapter.DrinkAdapter;
import com.example.shailendra.drinkapp.Adapter.DrinkViewHolder;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.shailendra.drinkapp.R.id.lst_menu;

public class DrinkActivity extends AppCompatActivity {

    IDrinkShopAPI mService;

    RecyclerView lst_drink;

    TextView txt_banner_name;

    FloatingActionButton fab;

    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);


    mService = Common.getAPI();

    swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refreshh);
    fab = (FloatingActionButton)findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(DrinkActivity.this , NewActivity.class));
        }
    });



        //drinks

        lst_drink = (RecyclerView)findViewById(R.id.recycler_drinks);
        lst_drink.setLayoutManager(new GridLayoutManager(this , 1));
        lst_drink.setHasFixedSize(true);

       txt_banner_name = (TextView)findViewById(R.id.txt_menu_name);
        txt_banner_name.setText(Common.currentCategory.Name);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                loadListDrink(Common.currentCategory.ID);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);

                loadListDrink(Common.currentCategory.ID);

            }
        });

    }

    private void loadListDrink(String menuId) {
    compositeDisposable.add(mService.getDrink(menuId)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Consumer<List<Drink>>() {
        @Override
        public void accept(List<Drink> drinks) throws Exception {

            displayDrinkList(drinks);
        }
    }));



    }

    private void displayDrinkList(List<Drink> drinks) {

        DrinkAdapter adapter = new DrinkAdapter(this,drinks);
        lst_drink.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    //Exit Application when click back button
    boolean isBackButtonClicked = false ;

    //ctrl + o


    @Override
    public void onBackPressed() {
        if (isBackButtonClicked) {
            super.onBackPressed();
            return;

        }
        this.isBackButtonClicked = true;
        Toast.makeText(this, "Please click Back Again to exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isBackButtonClicked = false ;
    }


}
