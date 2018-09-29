package com.example.shailendra.drinkapp.Model;

/**
 * Created by shailendra on 5/19/2018.
 */
public class CheckUserResponse {

    private boolean exists;
    private String error_msg;

    public CheckUserResponse() { //using generate constructor
    }


   //using generate getter & setter

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
