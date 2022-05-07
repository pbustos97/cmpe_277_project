package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSetMetaData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("userId", "user005");
        bundle.putString("hotelId", "hotel001");
        bundle.putString("hotels", Hotel.getHotelList(getApplicationContext().getString(R.string.api_hotel)));
        this.addFragment(R.id.main_container, UserReservationFragment.class, bundle);

    }

    protected void addFragment(int container, Class fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(container, fragment, bundle)
                .commit();
    }
}