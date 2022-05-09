package com.noidea.hootel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.noidea.hootel.Fragments.HotelFragment;
import com.noidea.hootel.Fragments.ReservationFragment;
import com.noidea.hootel.Fragments.UserFragment;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.User;

public class UIActivity extends AppCompatActivity {
    private static final String TAG = UIActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("userId", User.getUserId());
        bundle.putString("accessToken", User.getToken());
        String fragment = getIntent().getStringExtra("fragment");
        if (fragment == null) {
            Log.e(TAG, "Input fragment string was not passed into the activity");
            return;
        }
        if (fragment.equals("profile")) {
            addFragment(R.id.main_container, UserFragment.class, bundle);
        } else if (fragment.equals("hotels")) {
            bundle.putString("hotels", Hotel.getHotelList(getString(R.string.api_hotel)));
            bundle.putString("branches", Branch.getBranchList(getString(R.string.api_hotel)));
            addFragment(R.id.main_container, HotelFragment.class, bundle);
        } else if (fragment.equals("reservations")) {
            bundle.putString("reservationList", getIntent().getStringExtra("reservationList"));
            addFragment(R.id.main_container, UserReservationFragment.class, bundle);
        } else {
            Log.e(TAG, "invalid fragment input: ".concat(fragment));
        }
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
}