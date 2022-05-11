package com.noidea.hootel.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.noidea.hootel.Database.AppDB;
import com.noidea.hootel.HootelApplication;
import com.noidea.hootel.Models.Hotel;

import java.util.List;

public class HotelRepository {
    private final AppDB appDB;
    public HotelRepository(Context context) {
        appDB = ((HootelApplication)context.getApplicationContext()).getDatabase();
    }
    public List<Hotel> getAllSavedHotels() {
        return appDB.hotelDAO().getAll();
    }
    public Hotel getSavedHotelById(String hotelId) {
        return appDB.hotelDAO().getById(hotelId);
    }
    public boolean saveHotel(Hotel hotel) {
        new HotelAsyncTask(appDB).execute(hotel);
        return true;
    }
    public void updateAllSavedHotel(List<Hotel> hotelList) {
        AsyncTask.execute(() -> appDB.hotelDAO().updateHotel(hotelList));
    }

    private static class HotelAsyncTask extends AsyncTask<Hotel, Void, Boolean> {

        private final AppDB appDB;

        private HotelAsyncTask(AppDB appDB) {
            this.appDB = appDB;
        }

        @Override
        protected Boolean doInBackground(Hotel... hotels) {
            Hotel hotel = hotels[0];
            try {
                appDB.hotelDAO().insertHotel(hotel);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
