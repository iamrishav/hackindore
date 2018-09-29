package com.example.shailendra.drinkapp.Retrofit;


import com.example.shailendra.drinkapp.Model.Banner;
import com.example.shailendra.drinkapp.Model.Category;
import com.example.shailendra.drinkapp.Model.CheckUserResponse;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Model.DrinkImage;
import com.example.shailendra.drinkapp.Model.Noti;
import com.example.shailendra.drinkapp.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by shailendra on 5/19/2018.
 */
public interface IDrinkShopAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone ,
                               @Field("name") String name,
                               @Field("address") String address,
                               @Field("birthdate") String birthdate);



    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);

    //for banners
    @GET("getbanner.php")
    io.reactivex.Observable<List<Banner>> getBanners();


    //for category or menu
    @GET("getmenu.php")
    io.reactivex.Observable<List<Category>> getMenu();

    //for drink load
    @FormUrlEncoded
    @POST("getdrink.php")
    io.reactivex.Observable<List<Drink>> getDrink(@Field("menuid") String menuID );


    //for drink Image load
    @FormUrlEncoded
    @POST("getDrinkImage.php")
    io.reactivex.Observable<List<DrinkImage>> getDrinkImage(@Field("drinkid") String drinkID );

   //upload Avatar
    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part MultipartBody.Part phone , @Part MultipartBody.Part file);


    //FCM Token
    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token ,
                             @Field("isServerToken") String isServerToken);



    //get all drinks
    @GET("getallnotifications.php")
    io.reactivex.Observable<List<Noti>> getAllNotifications();

}
