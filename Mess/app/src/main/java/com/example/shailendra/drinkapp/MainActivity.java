package com.example.shailendra.drinkapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Message;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shailendra.drinkapp.Model.CheckUserResponse;
import com.example.shailendra.drinkapp.Model.User;
import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Utils.Common;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.jar.Manifest;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
    Button btn_continue;

    IDrinkShopAPI mService ;

    //ctrl + o for external permisssion

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case REQUEST_PERMISSION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

            }
                break;
            default:
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //read external storage permission
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this , new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);




        mService = Common.getAPI();

        btn_continue = (Button)findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLoginPage(LoginType.PHONE);

            }
        });

        //check session
        if(AccountKit.getCurrentAccessToken() != null)
        {
            final AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
            alertDialog.show();
            alertDialog.setMessage("Please Waiting.....");
            //Auto Login
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {
                    mService.checkUserExists(account.getPhoneNumber().toString())
                            .enqueue(new Callback<CheckUserResponse>() {
                                @Override
                                public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                    CheckUserResponse userResponse = response.body();
                                    if(userResponse.isExists())
                                    {

                                        //fetch information

                                        mService.getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        //if user already exists , just start new activity
                                                        alertDialog.dismiss();

                                                        Common.currentUser = response.body(); // fetch user informatiion

                                                        //update Firebase token
                                                        updateTokenToFirebase();

                                                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                                        finish();  //close mainactivity

                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                    else
                                    {
                                        //else , need  register
                                        alertDialog.dismiss();

                                        showRegisterDialog(account.getPhoneNumber().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                                }
                            });
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.d("Error",accountKitError.getErrorType().getMessage());
                }
            });



            // just copy the below same login code for autologin
            // end Auto login
        }


   // pritnHashKey();
    }

    private void startLoginPage(LoginType loginType) {
        Intent intent = new Intent (this , AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType , AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION , builder.build());
        startActivityForResult(intent,REQUEST_CODE);


    }

    //ctrl+o

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
               AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (result.getError() != null)
            {
                  Toast.makeText(this , ""+result.getError().getErrorType().getMessage(),Toast.LENGTH_LONG).show();
            }
            else if (result.wasCancelled())
            {
                Toast.makeText(this , "Cancel ",Toast.LENGTH_LONG).show();
            }
            else {
                if(result.getAccessToken() != null)
                {
                    final AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
                    alertDialog.show();
                    alertDialog.setMessage("Please Waiting.....");


                    //get user phone & check exists on serrver

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            mService.checkUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<CheckUserResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            CheckUserResponse userResponse = response.body();
                                            if(userResponse.isExists())
                                            {

                                                //fetch information

                                                mService.getUserInformation(account.getPhoneNumber().toString())
                                                        .enqueue(new Callback<User>() {
                                                            @Override
                                                            public void onResponse(Call<User> call, Response<User> response) {
                                                                //if user already exists , just start new activity
                                                                alertDialog.dismiss();

                                                                Common.currentUser = response.body(); //fetch user infrmtn

                                                                //update Token
                                                                updateTokenToFirebase();

                                                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                                                finish();  //close mainactivity

                                                            }

                                                            @Override
                                                            public void onFailure(Call<User> call, Throwable t) {
                                                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            }
                                            else
                                            {
                                                //else , need  register
                                                alertDialog.dismiss();

                                                showRegisterDialog(account.getPhoneNumber().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                                        }
                                    });
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                        Log.d("Error",accountKitError.getErrorType().getMessage());
                        }
                    });
                }
            }
        }
    }

    private void showRegisterDialog(final String phone) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("REGISTER");


        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText edt_name = (MaterialEditText)register_layout.findViewById(R.id.edt_name);
        final MaterialEditText edt_address = (MaterialEditText)register_layout.findViewById(R.id.edt_addres);
        final MaterialEditText edt_birthdate = (MaterialEditText)register_layout.findViewById(R.id.edt_birthdate);

        Button btn_register = (Button)register_layout.findViewById(R.id.btn_register);

        edt_birthdate.addTextChangedListener(new PatternedTextWatcher("####-##-##"));


        builder.setView(register_layout);
      final AlertDialog  dialog = builder.create();

        //Event
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(TextUtils.isEmpty(edt_address.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Please Enter your Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(edt_birthdate.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Please Enter your Birthdate", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(edt_name.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                final AlertDialog watingDialog = new SpotsDialog(MainActivity.this);
                watingDialog.show();
                watingDialog.setMessage("Please Waiting.....");

               mService.registerNewUser(phone ,
                       edt_name.getText().toString(),
                       edt_address.getText().toString(),
                       edt_birthdate.getText().toString())
                       .enqueue(new Callback<User>() {
                           @Override
                           public void onResponse(Call<User> call, Response<User> response) {
                               watingDialog.dismiss();
                               User user = response.body();
                               if(TextUtils.isEmpty(user.getError_msg()))
                               {
                                   Toast.makeText(MainActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                                   Common.currentUser = response.body(); // just asssign json result to common.currentuser

                                   //update Token
                                   updateTokenToFirebase();

                                    //start new activity
                               startActivity(new Intent(MainActivity.this,HomeActivity.class));
                             finish();

                               }

                           }

                           @Override
                           public void onFailure(Call<User> call, Throwable t) {
                                  watingDialog.dismiss();
                               Toast.makeText(MainActivity.this, "Pta ni kya hua", Toast.LENGTH_SHORT).show();

                           }
                       });

            }

        });


               dialog.show();
    }


    private void pritnHashKey() {

        try{
            PackageInfo info = getPackageManager().getPackageInfo( "com.example.shailendra.drinkapp",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures)
            {
                MessageDigest md= MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH" , Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //Exit Application when click back button
    boolean isBackButtonClicked = false ;

    //ctrl + o


    @Override
    public void onBackPressed() {
       if (isBackButtonClicked) {
           super.onBackPressed();
       return;

       }
    this.isBackButtonClicked = true;
        Toast.makeText(this, "Please click Back Again to exit", Toast.LENGTH_SHORT).show();
    }
//ctrl + o


    @Override
    protected void onPostResume() {
        isBackButtonClicked = false;
        super.onPostResume();
    }

    private void updateTokenToFirebase() {
        IDrinkShopAPI mService = Common.getAPI();
        mService.updateToken(Common.currentUser.getPhone(), FirebaseInstanceId.getInstance().getToken(),"0")
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("FirebaseID" , response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("FirebaseID",t.getMessage());
                    }
                });
    }
}
