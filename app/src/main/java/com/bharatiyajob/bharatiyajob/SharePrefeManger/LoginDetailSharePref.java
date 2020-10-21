package com.bharatiyajob.bharatiyajob.SharePrefeManger;

import android.content.Context;
import android.content.SharedPreferences;

import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;

import java.lang.ref.WeakReference;

public class LoginDetailSharePref {

    public LoginDetailSharePref sharePrefMamager;

    //to handle we need Context object
//    private final Context context;
    private WeakReference<Context> context;

    public LoginDetailSharePref(Context context) {
//        this.context = context;
        this.context = new WeakReference<>(context);
    }

    //we will create Syncronized Method as we only want a single instance
    public LoginDetailSharePref getInstance(Context context) {
        if (sharePrefMamager == null) {  //this mean the object is no yet created in this case we will make new SharedPrefrenceManager
            sharePrefMamager = new LoginDetailSharePref(context);
        }
        return sharePrefMamager;
    }

    //now we will create method that will store User login Details

    public void saveLoginDetails(LoginOtpResponse loginResponse) {
        //here mode is private bcz we only want this application to access shared prefrence
        SharedPreferences sharedPreferences = context.get().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status", loginResponse.getStatus());
        editor.putString("message", loginResponse.getMessage());
        editor.putString("id", loginResponse.getId());
        editor.putString("name", loginResponse.getName());
        editor.putString("email", loginResponse.getEmail());
        editor.putString("mobile", loginResponse.getMobile());
        editor.putString("reg_type", loginResponse.getReg_type());
        editor.putString("token", loginResponse.getToken());

        editor.apply();
    }

    //now we will create one more method to check if the user is already loggedI or not
    //if user detail is already presennt in share prefrence we will assume that user is alreadyu logged in

    public boolean UserAlreadyLoggedIn() {

        SharedPreferences sharedPreferences = context.get().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //it mean that value in id is saved bcz in database we cannot put id == -1
        return !sharedPreferences.getString("id", "-1").equals("-1"); //bcz user is already logged in
    }

    // this method will use to get back USer Driver Detail which is saved in any activty
    //now we need to get back the user and Driver
    public LoginOtpResponse getDetail() {

        SharedPreferences sharedPreferences = context.get().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //now we can read the value form sharePrefrences object

        return new LoginOtpResponse(
                sharedPreferences.getString("status", null),
                sharedPreferences.getString("message", null),
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("reg_type", null),
                sharedPreferences.getString("token", null)
        );
    }


    //Create a method to Logout
    public void Logout() {

        SharedPreferences sharedPreferences = context.get().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
