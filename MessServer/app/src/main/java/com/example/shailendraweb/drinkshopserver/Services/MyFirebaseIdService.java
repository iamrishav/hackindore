package com.example.shailendraweb.drinkshopserver.Services;

import android.util.Log;

import com.example.shailendraweb.drinkshopserver.Retrofit.IDrinkShopAPI;
import com.example.shailendraweb.drinkshopserver.Utils.Common;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    //ctrl o

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        updateTokenToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void updateTokenToServer(String token) {
        IDrinkShopAPI mService = Common.getAPI();
        mService.updateToken("server_app_01",token , "1")
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("FirebaseToken",response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Log.d("FirebaseToken",t.getMessage());
                    }
                });
    }
}
