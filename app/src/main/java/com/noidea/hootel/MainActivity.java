package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;

import com.noidea.hootel.Database.AppDB;
import com.noidea.hootel.Models.Hotel;

import com.noidea.hootel.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Hotel.getHotelList(getApplicationContext().getString(R.string.api_hotel));
        this.addFragment(R.id.main_container, LoginFragment.class, null);
    }
    protected void addFragment(int container, Class fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(container, fragment, bundle)
                .commit();
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