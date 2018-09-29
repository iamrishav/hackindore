package com.example.shailendra.drinkapp.Utils;


import com.example.shailendra.drinkapp.Model.Category;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Model.User;
import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Retrofit.RetrofitClient;

/**
 * Created by shailendra on 5/19/2018.
 */
public class Common {

    public static final String BASE_URL = "http://shikhaelectricals.com/Mess/";

    public static User currentUser = null;

    public static Drink currentSelect = null;   //for multiple

    public static Category currentCategory = null ;  //create for store menu/category position or data for new actiivity
    public static Drink currentDrink = null ;   //create for store drink position or id ............by mee


    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getclient(BASE_URL).create(IDrinkShopAPI.class);

    }





}
