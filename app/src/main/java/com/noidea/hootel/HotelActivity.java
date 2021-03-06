package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.noidea.hootel.Fragments.LoginFragment;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.User;

public class HotelActivity extends AppCompatActivity {
    private String hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelEmail;
    private String branchId;
    private String userId;
    private String accessToken;
    private TextView textViewHotelName;
    private TextView textViewHotelAddress;
    private Button bCreateBooking;
    private Button bContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            hotelId = intent.getStringExtra("hotelId");
            userId = intent.getStringExtra("userId");
            Log.d("HotelActivity", "UserId : " + userId);
            hotelName = intent.getStringExtra("hotelName");
            hotelAddress = intent.getStringExtra("hotelAddress");
            hotelEmail = intent.getStringExtra("hotelEmail");
            accessToken = intent.getStringExtra("accessToken");
            branchId = intent.getStringExtra("branchId");
        }

        textViewHotelName = findViewById(R.id.HotelActivityName);
        textViewHotelAddress = findViewById(R.id.HotelActivityAddress);
        bCreateBooking = findViewById(R.id.HotelActivityButtonBook);
        bContact = findViewById(R.id.HotelActivityButtonTwo);

        textViewHotelName.setText(hotelName);
        textViewHotelAddress.setText(hotelAddress);

        bCreateBooking.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                bundle.putString("accessToken", accessToken);
                bundle.putString("hotelId", hotelId);
                bundle.putString("branchId", branchId);
                RoomFragment roomFragment = new RoomFragment();
                roomFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.hotel_container, roomFragment).commit();
            }
        });

        bContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "contact@hootel.com", Toast.LENGTH_LONG).show();
            }
        });
    }

    // MODIFY THIS, CALL BOOKING ENDPOINT
    private void BookRoom() {
        // USE THIS DURING INTEGRATION
        //String userId = User.getUserId();
        String url = getResources().getString(R.string.api_booking);
    }


}