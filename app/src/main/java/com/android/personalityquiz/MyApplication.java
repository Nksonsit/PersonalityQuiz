package com.android.personalityquiz;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase();
    }
    private void initDataBase() {
        DatabaseManager.initialize(DBOpenHelper.getInstance((this)));
        DBOpenHelper.getInstance((this)).createDataBase(this);
    }

}
