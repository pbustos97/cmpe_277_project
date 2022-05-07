package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.User;

public class HotelActivity extends AppCompatActivity {
    private String hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelEmail;
    private String userId;

    private TextView textViewHotelName;
    private TextView textViewHotelAddress;
    private Button bCreateBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            hotelId = intent.getStringExtra("hotelId");
            userId = intent.getStringExtra("userId");
            hotelName = intent.getStringExtra("hotelName");
            hotelAddress = intent.getStringExtra("hotelAddress");
            hotelEmail = intent.getStringExtra("hotelEmail");
        }

        textViewHotelName = findViewById(R.id.HotelActivityName);
        textViewHotelAddress = findViewById(R.id.HotelActivityAddress);
        bCreateBooking = findViewById(R.id.HotelActivityButtonBook);

        textViewHotelName.setText(hotelName);
        textViewHotelAddress.setText(hotelAddress);

        bCreateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookRoom();
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