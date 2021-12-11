package com.example.hw1;

import android.app.Application;

import com.google.gson.Gson;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MSP.initHelper(this);
        DataManager dm = new Gson().fromJson(MSP.getMe().getString("DATA_MANAGER", ""), DataManager.class);

        if(dm == null){
            DataManager dataManager = new DataManager();
            MSP.getMe().putString("DATA_MANAGER", new Gson().toJson(dataManager));
        }
    }
}
