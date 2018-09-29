package com.example.shailendraweb.drinkshopserver.Utils;

import com.example.shailendraweb.drinkshopserver.Model.Category;
import com.example.shailendraweb.drinkshopserver.Model.Drink;
import com.example.shailendraweb.drinkshopserver.Retrofit.FCMRetrofitClient;
import com.example.shailendraweb.drinkshopserver.Retrofit.IDrinkShopAPI;
import com.example.shailendraweb.drinkshopserver.Retrofit.RetrofitClient;
import com.example.shailendraweb.drinkshopserver.Services.IFCMServices;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static final String BASE_URL = "http://shikhaelectricals.com/Mess/";
    public static final String FCM_URL = "https://fcm.googleapis.com/";

    public static Category currentCategory ;
    public static Drink currentDrink;


    public static List<Category> menuList = new ArrayList<>();   //for spinner menu

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }

    public static IFCMServices getFCMAPI()
    {
        return FCMRetrofitClient.getClient(FCM_URL).create(IFCMServices.class);
    }



}
