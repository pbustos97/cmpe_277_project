package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.User;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HttpUtil.getJSON("allroomInfo");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil util = new HttpUtil(R.string.api_room, getApplicationContext());
                    util.sendRequest("amenity", "PATCH", "{\n" +
                            "    \"body\" : {\n" +
                            "        \"amenityId\" : \"6c9c1801-0acd-41ee-8274-f1a41c678a39\", \n" +
                            "            \"amenityInfo\" :\n" +
                            "            {\"amenityName\" : \"test change\"\n" +
                            "            }\n" +
                            "        \n" +
                            "    }\n" +
                            "}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Bundle bundle = new Bundle();
//        bundle.putString("userId", "user001");
//        bundle.putString("hotelId", "hotel001");
//        bundle.putString("hotels", Hotel.getHotelList(getApplicationContext().getString(R.string.api_hotel)));
        this.addFragment(R.id.main_container, LoginFragment.class, bundle);
    }

    protected void addFragment(int container, Class fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(container, fragment, bundle)
                .commit();
    }

    protected void removeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment).commit();
    }

    public void loggedIn() {
        if (User.isLoggedIn()) {
            this.removeFragment();
        }
    }
}