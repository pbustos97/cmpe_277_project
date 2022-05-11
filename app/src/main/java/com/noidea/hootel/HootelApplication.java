package com.noidea.hootel;

import android.app.Application;

import androidx.room.Room;

import com.noidea.hootel.Database.AppDB;

public class HootelApplication extends Application {
    private AppDB appDB;

    @Override
    public void onCreate() {
        super.onCreate();
        appDB = Room.databaseBuilder(this, AppDB.class, "appDB").build();
    }
    public AppDB getDatabase() {
        return appDB;
    }
}
