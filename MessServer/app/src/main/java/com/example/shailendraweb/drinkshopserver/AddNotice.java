package com.example.shailendraweb.drinkshopserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shailendraweb.drinkshopserver.Model.DataMessage;
import com.example.shailendraweb.drinkshopserver.Model.MyResponse;
import com.example.shailendraweb.drinkshopserver.Model.Token;
import com.example.shailendraweb.drinkshopserver.Retrofit.IDrinkShopAPI;
import com.example.shailendraweb.drinkshopserver.Services.IFCMServices;
import com.example.shailendraweb.drinkshopserver.Utils.Common;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNotice extends AppCompatActivity {
    EditText edt_title , edt_description;
    Button btn_send;

    IDrinkShopAPI mService;
    IFCMServices mFCMService;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        mService = Common.getAPI();
        mFCMService = Common.getFCMAPI();

        edt_title = (EditText)findViewById(R.id.edt_title);
        edt_description = (EditText)findViewById(R.id.edt_description);
        btn_send = (Button)findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNoti();
            }
        });
    }

    private void SendNoti() {
        compositeDisposable.add(mService.addNewNoti(edt_title.getText().toString(),edt_description.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                     sendNotification();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(AddNotice.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }));
    }

    private void sendNotification() {


        //Get Token of Admin
        mService.getToken("+917000123149","0")
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {

                        Token userToken = response.body();

                        DataMessage dataMessage = new DataMessage();

                        Map<String , String> dataSend = new HashMap<>();
                        dataSend.put("title","hloo");
                        dataSend.put("message","hhhhh");

                        dataMessage.to = userToken.getToken();
                        dataMessage.setData(dataSend);

                        mFCMService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.body().success == 1)
                                        {
                                            Toast.makeText(AddNotice.this, "Add Notice Success", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {

                                        Toast.makeText(AddNotice.this, "Add Notice Suceess"+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {

                        Toast.makeText(AddNotice.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
