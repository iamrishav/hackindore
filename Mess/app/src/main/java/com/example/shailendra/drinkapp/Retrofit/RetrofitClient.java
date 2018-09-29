package com.example.shailendra.drinkapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shailendra on 5/19/2018.
 */
public class RetrofitClient {
    private static Retrofit retrofit= null;


   public static Retrofit getclient (String baseUrl)

   {
       if(retrofit == null)
       {
           retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // in slider partt 4 for banner
                   .build();

       }
       return retrofit;

   }


}
