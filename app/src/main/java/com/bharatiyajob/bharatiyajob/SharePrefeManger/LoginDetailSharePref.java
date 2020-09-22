package com.bharatiyajob.bharatiyajob.SharePrefeManger;

import android.content.Context;
import android.content.SharedPreferences;

import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;

public class LoginDetailSharePref {

    private  static LoginDetailSharePref sharePrefMamager;

    //to handle we need Context object
    private Context context;

    public LoginDetailSharePref(Context context) {
        this.context = context;
    }

    //we will create Syncronized Method as we only want a single instance
    public  static  synchronized  LoginDetailSharePref getInstance(Context context){
        if (sharePrefMamager == null){  //this mean the object is no yet created in this case we will make new SharedPrefrenceManager
            sharePrefMamager = new LoginDetailSharePref(context);
        }
        return  sharePrefMamager;
    }

    //now we will create method that will store User login Details

    public  void saveLoginDetails(LoginOtpResponse loginResponse){

        //here mode is private bcz we only want this application to access shared prefrence
        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", loginResponse.getId());
        editor.putString("name", loginResponse.getName());
        editor.putString("email", loginResponse.getEmail());
        editor.putString("mobile", loginResponse.getMobile());

        editor.apply();
    }

    //now we will create one more method to check if the user is already loggedI or not
    //if user detail is already presennt in share prefrence we will assume that user is alreadyu logged in

    public boolean UserAlreadyLoggedIn() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //it mean that value in id is saved bcz in database we cannot put id == -1
        return sharedPreferences.getString("id", "-1") != "-1"; //bcz user is already logged in
    }

    // this method will use to get back USer Driver Detail which is saved in any activty
    //now we need to get back the user and Driver
    public LoginOtpResponse getUserDetail() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //now we can read the value form sharePrefrences object
        LoginOtpResponse loginOtpResponse = new LoginOtpResponse(
                sharedPreferences.getString("error", null),
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("message", null)
        );

        return loginOtpResponse;
    }


    //Create a method to Logout
    public void UserLogout() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
