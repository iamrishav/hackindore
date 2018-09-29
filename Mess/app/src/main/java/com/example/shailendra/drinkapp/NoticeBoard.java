package com.example.shailendra.drinkapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shailendra.drinkapp.Adapter.DrinkAdapter;
import com.example.shailendra.drinkapp.Adapter.NotificationAdapter;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Model.Noti;
import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NoticeBoard extends AppCompatActivity {
    IDrinkShopAPI mService;

    RecyclerView lst_noti;


    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

    mService = Common.getAPI();

    swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refreshh);

    //drinks

    lst_noti = (RecyclerView)findViewById(R.id.recycler_noti);
        lst_noti.setLayoutManager(new GridLayoutManager(this , 1));
        lst_noti.setHasFixedSize(true);



        swipeRefreshLayout.post(new Runnable() {
        @Override
        public void run() {
            swipeRefreshLayout.setRefreshing(true);


            loadAllNoti();
        }
    });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            swipeRefreshLayout.setRefreshing(true);
            loadAllNoti();

        }
    });

}

    private void loadAllNoti() {
        compositeDisposable.add(mService.getAllNotifications().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).
                subscribe(new Consumer<List<Noti>>() {
                    @Override
                    public void accept(List<Noti> notis) throws Exception {

                        displayNoti(notis);
                    }
                }));

    }

    private void displayNoti(List<Noti> notis) {
      NotificationAdapter  adapter = new NotificationAdapter(this , notis);
        lst_noti.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);

    }


}
