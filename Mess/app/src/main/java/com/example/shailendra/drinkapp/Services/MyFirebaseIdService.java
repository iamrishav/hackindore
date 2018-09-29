package com.example.shailendra.drinkapp.Services;

import android.util.Log;

import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Utils.Common;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//for firebase cloud notification token
public class MyFirebaseIdService extends FirebaseInstanceIdService {
    //ctrl o

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        if (Common.currentUser != null)
        {
            updateTokenToFirebase();
        }
    }

    private void updateTokenToFirebase() {
        IDrinkShopAPI mService = Common.getAPI();
        mService.updateToken(Common.currentUser.getPhone(), FirebaseInstanceId.getInstance().getToken(),"0")
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("FirebaseID" , response.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("FirebaseID",t.getMessage());
                    }
                });
    }

}
