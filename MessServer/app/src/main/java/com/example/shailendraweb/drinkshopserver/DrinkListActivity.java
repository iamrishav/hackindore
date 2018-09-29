package com.example.shailendraweb.drinkshopserver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shailendraweb.drinkshopserver.Adapter.DrinkListAdapter;
import com.example.shailendraweb.drinkshopserver.Model.Drink;
import com.example.shailendraweb.drinkshopserver.Retrofit.IDrinkShopAPI;
import com.example.shailendraweb.drinkshopserver.Utils.Common;
import com.example.shailendraweb.drinkshopserver.Utils.ProgressRequestBody;
import com.example.shailendraweb.drinkshopserver.Utils.UploadCallBack;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkListActivity extends AppCompatActivity implements UploadCallBack {

    private static final int PICK_FILE_REQUEST = 1111;
    IDrinkShopAPI mService;
    RecyclerView recycler_drinks;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    FloatingActionButton btn_add;

    ImageView img_browser;
    EditText edt_drink_name , edt_drink_price;


    Uri selected_uri ;
    String uploaded_img_path="";


    //ctrl o file upload

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PICK_FILE_REQUEST)
            {
                if (data != null)
                {
                    selected_uri = data.getData();
                    if (selected_uri != null && !selected_uri.getPath().isEmpty())
                    {
                        img_browser.setImageURI(selected_uri);
                        uploadFileToServer();
                    }
                    else
                        Toast.makeText(this, "Cannot Upload file to Server", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void uploadFileToServer() {
        //Product image upload
        if (selected_uri != null)
        {
            File file = FileUtils.getFile(this,selected_uri);

            String fileName = new StringBuilder(UUID.randomUUID().toString())
                    .append(FileUtils.getExtension(file.toString())).toString();

            ProgressRequestBody requestFile = new ProgressRequestBody(file , this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file" , fileName,requestFile);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.uploadProductFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    //After Upload , get file name & return String contains link of image
                                    uploaded_img_path = new StringBuilder(Common.BASE_URL)
                                            .append("server/product/product_img/")
                                            .append(response.body().toString())
                                            .toString();

                                    Log.d("IMGPath" , uploaded_img_path);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                    Toast.makeText(DrinkListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            }).start();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_list);

        mService = Common.getAPI();

        btn_add = (FloatingActionButton)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDrinkDialog();
            }
        });

        recycler_drinks = (RecyclerView)findViewById(R.id.recycler_drinks);
        recycler_drinks.setLayoutManager( new GridLayoutManager(this , 2));
        recycler_drinks.setHasFixedSize(true);

        loadListDrink(Common.currentCategory.getID());

    }

    private void showAddDrinkDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Product");

        View view = LayoutInflater.from(this).inflate(R.layout.add_new_product_layout , null);

        edt_drink_name = (EditText)view.findViewById(R.id.edt_drink_name);
        edt_drink_price = (EditText)view.findViewById(R.id.edt_drink_price);
        img_browser = (ImageView)view.findViewById(R.id.img_browser);

        //Event
        img_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(), "Select a File")
                        ,PICK_FILE_REQUEST);
            }
        });

        //set View
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                uploaded_img_path = "";
                selected_uri = null;
            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (edt_drink_name.getText().toString().isEmpty()) {
                    Toast.makeText(DrinkListActivity.this, "Please enter name of Product", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edt_drink_price.getText().toString().isEmpty()) {
                    Toast.makeText(DrinkListActivity.this, "Please enter price of product", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (uploaded_img_path.isEmpty()) {
                    Toast.makeText(DrinkListActivity.this, "Please select image of Product", Toast.LENGTH_SHORT).show();
                    return;
                }


                compositeDisposable.add(mService.sendEmails(uploaded_img_path , edt_drink_name.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(DrinkListActivity.this, "Email Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(DrinkListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));





                compositeDisposable.add(mService.addNewProduct(edt_drink_name.getText().toString(),
                        uploaded_img_path ,
                        edt_drink_price.getText().toString(),
                        Common.currentCategory.getID())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                                Toast.makeText(DrinkListActivity.this, s, Toast.LENGTH_SHORT).show();
                                loadListDrink(Common.currentCategory.getID());

                                uploaded_img_path="";
                                selected_uri = null;


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(DrinkListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })


                );


            }

        }).show();

    }



    private void loadListDrink(String id) {
        compositeDisposable.add(mService.getDrink(id).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Drink>>() {
            @Override
            public void accept(List<Drink> drinks) throws Exception {
                displayDrinkList(drinks);
            }
        }));
    }

    private void displayDrinkList(List<Drink> drinks) {
        DrinkListAdapter adapter = new DrinkListAdapter(this,drinks);
        recycler_drinks.setAdapter(adapter);
    }

    //ctrl o


    @Override
    protected void onResume() {
        loadListDrink(Common.currentCategory.getID());

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onProgressUpdate(int percentage) {

    }
}
