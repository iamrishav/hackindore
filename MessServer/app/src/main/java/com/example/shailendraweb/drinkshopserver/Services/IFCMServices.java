package com.example.shailendraweb.drinkshopserver.Services;

import com.example.shailendraweb.drinkshopserver.Model.DataMessage;
import com.example.shailendraweb.drinkshopserver.Model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMServices {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAgXWrP7s:APA91bHpANO36LD7F_FI_Wlm2rrOCOEnGjoM2zO8yoIlLeCt2BnzbPTLw2gRTJsnykvWYDOgljNn-wCm94awout4T3nzVKZik0tfvsjtI4py1OYDIhBAHCWWklsH4FXjBp0Cm78u7-e1rp1qaiQgjm_Nj4azZmuWaA"

    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
