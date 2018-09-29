package com.example.shailendraweb.drinkshopserver.Retrofit;

import com.example.shailendraweb.drinkshopserver.Model.Category;
import com.example.shailendraweb.drinkshopserver.Model.Drink;
import com.example.shailendraweb.drinkshopserver.Model.Token;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IDrinkShopAPI {


    //load Menu list
    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    //Add new Category
    @FormUrlEncoded
    @POST("server/category/add_category.php")
    Observable<String> addNewCategory(@Field("name") String name, @Field("imgPath") String imgPath);

    //upload new Menu / Category image
    @Multipart
    @POST("server/category/upload_category_img.php")
    Call<String> uploadCategoryFile(@Part MultipartBody.Part file);

    //update category
    @FormUrlEncoded
    @POST("server/category/update_category.php/")
    Observable<String> updateCategory(@Field("id") String id,
                                      @Field("name") String name,
                                      @Field("imgPath") String imgPath);

    //Delete Category
    @FormUrlEncoded
    @POST("server/category/delete_category.php/")
    Observable<String> deleteCategory(@Field("id") String id);

    //load Drink list
    @FormUrlEncoded
    @POST("getdrink.php")
    Observable<List<Drink>> getDrink(@Field("menuid") String menuID);

    //Add new Product/Drink
    @FormUrlEncoded
    @POST("server/product/add_product.php")
    Observable<String> addNewProduct(@Field("name") String name,
                                     @Field("imgPath") String imgPath,
                                     @Field("price") String price,
                                     @Field("menuId") String menuId);

    //upload new product / Drink image
    @Multipart
    @POST("server/product/upload_product_img.php")
    Call<String> uploadProductFile(@Part MultipartBody.Part file);

    //update Product
    @FormUrlEncoded
    @POST("server/product/update_product.php/")
    Observable<String> updateProduct(@Field("id") String id,
                                      @Field("name") String name,
                                      @Field("imgPath") String imgPath,
                                      @Field("price") String price,
                                     @Field("menuId") String menuId);

    //Delete Product
    @FormUrlEncoded
    @POST("server/product/delete_product.php/")
    Observable<String> deleteProduct(@Field("id") String id);


    //Firebase Token
    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token,
                             @Field("isServerToken") String isServerToken);

    //Emails snd
    @FormUrlEncoded
    @POST("mail/Email/index.php")
    Observable<String> sendEmails(@Field("img") String img,
                             @Field("title") String title);

    //Add new Noti
    @FormUrlEncoded
    @POST("server/product/insertnewnoti.php")
    Observable<String> addNewNoti(@Field("title") String title,
                                     @Field("description") String description);

    //Get Token
    @FormUrlEncoded
    @POST("gettoken.php")
    Call<Token> getToken(@Field("phone") String phone,
                         @Field("isServerToken") String isServerToken);
}
