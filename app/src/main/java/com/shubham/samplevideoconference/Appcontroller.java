package com.shubham.samplevideoconference;

import android.app.Application;
import android.content.SharedPreferences;

public class Appcontroller extends Application {
    User_Model user_model;
    static Appcontroller mInstance;

    public static synchronized Appcontroller getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public User_Model getUser_model() {
        SharedPreferences user_pref;
        User_Model user=new User_Model();
        user_pref=getSharedPreferences("user_info",MODE_PRIVATE);

        if(user_pref.contains("user_name"))
        {
            user.setUser_name(user_pref.getString("user_name",""));
            user.setUser_email(user_pref.getString("user_email",""));
            user.setUser_image(user_pref.getString("user_image",""));
            return user;
        }
        else {
            return null;
        }

    }

    public void setUser_model(User_Model user_model) {
        this.user_model = user_model;
        SharedPreferences user_pref;
        user_pref=getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor=user_pref.edit();
        editor.putString("user_name",user_model.getUser_name());
        editor.putString("user_email",user_model.getUser_email());
        editor.putString("user_image",user_model.getUser_image());
        editor.apply();
    }
}
