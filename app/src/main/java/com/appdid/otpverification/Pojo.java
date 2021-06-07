package com.appdid.otpverification;

import android.content.Context;
import android.content.SharedPreferences;

public class Pojo{

    String pojoValue;

    private Context context;

    private SharedPreferences sharedPreferences;

    public Pojo(Context context){
        this.context = context;
    }

    public Pojo() {

    }

    public String getPojo(String key) {
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        pojoValue = sharedPreferences.getString(key, "Not Entered");
        return pojoValue;

    }

    void setPojo(String key,String pojoName) {
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,pojoName);
        editor.apply();
    }

    void clearData()
    {
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
