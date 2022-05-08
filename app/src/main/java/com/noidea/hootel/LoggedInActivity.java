package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;

import com.noidea.hootel.Fragments.HotelFragment;
import com.noidea.hootel.Fragments.SelectionFragment;
import com.noidea.hootel.Fragments.UserFragment;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.User;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("userId", User.getUserId());
        bundle.putString("accessToken", User.getToken());
        this.addFragment(R.id.main_container, SelectionFragment.class, bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loggedIn();
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
        if (!User.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void changeFragment(String fragment) {
        removeFragment();
        Bundle bundle = new Bundle();
        if (fragment.equals("Profile")) {
            bundle.putString("userId", User.getUserId());
            addFragment(R.id.main_container, UserFragment.class, bundle);
        } else if (fragment.equals("Selection")) {
            addFragment(R.id.main_container, SelectionFragment.class, bundle);
        } else if (fragment.equals("Hotels")) {
            bundle.putString("hotels", Hotel.getHotelList(getString(R.string.api_hotel)));
            addFragment(R.id.main_container, HotelFragment.class, bundle);
        } else if (fragment.equals("Reservations")) {
            // Choose Reservations Fragment
        } else {
            loggedIn();
        }
    }
}