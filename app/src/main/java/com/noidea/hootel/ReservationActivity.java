package com.noidea.hootel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReservationActivity extends AppCompatActivity {
    private final static String TAG = "ReservationActivity";
    TextView custmoerName;
    TextView checkInDate;
    TextView checkoutDate;
    TextView BookingInfo;
    TextView HotelInfo;
    TextView OrderPrice;
    TextView OrderStatus;
    Button cancelBtn;
    String reservationId;
    String userId;
    String accessToken;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        custmoerName = findViewById(R.id.reservation_name);
        checkInDate = findViewById(R.id.reservation_checkinDate);
        checkoutDate = findViewById(R.id.reservation_checkoutDate);
        BookingInfo = findViewById(R.id.reservation_bookingInfo);
        HotelInfo = findViewById(R.id.reservation_hotelInfo);
        OrderPrice = findViewById(R.id.reservation_orderprice);
        cancelBtn = findViewById(R.id.reservation_cancelbtn);
        OrderStatus = findViewById(R.id.reservation_orderStatus);
        Bundle b= getIntent().getExtras();
        reservationId = b.getString("reservationId");
        ArrayList<String> bookinginfo = b.getStringArrayList("bookingInfo");
        String bookingin = getBookingInfo(bookinginfo);
        String hotelin = getHotelInfo(b.getString("Address"), b.getString("email"));
        custmoerName.setText(b.getString("custmoerLastName"));
        checkInDate.setText(b.getString("startDate"));
        checkoutDate.setText(b.getString("endDate"));
        userId = b.getString("userId");
        accessToken = b.getString("accessToken");
        BookingInfo.setText(bookingin);
        HotelInfo.setText(hotelin);
        OrderStatus.setText(b.getString("status"));
        OrderPrice.setText("$" + b.getString("price"));
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] statusCode = {""};
                new Thread(new Runnable() {
                    String url = getApplicationContext().getString(R.string.api_booking) + "cancel";
                    String body = "{\n" +
                            "  \"body\": {\n" +
                            "    \"reservationId\": " + "\"" + reservationId + "\""+
                            "  }\n" +
                            "}";
                    @Override
                    public void run() {
                        try {
                            JSONObject res = HttpUtil.sendRequest(url, "PATCH", body, accessToken);
                            statusCode[0] = res.getString("statusCode");
                            Log.d(TAG, statusCode[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                Toast happytoast = Toast.makeText(getApplicationContext(),"Your reservation is canceled",Toast.LENGTH_SHORT);
                Toast failedtoast = Toast.makeText(getApplicationContext(),"Cancel failed please contact with Hotel",Toast.LENGTH_SHORT);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode[0].equals("200")) {
                            happytoast.show();
                        }else {
                            failedtoast.show();
                        }
                    }
                }, 1300);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public String getBookingInfo(ArrayList<String> bookinginfo) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String bkinfo : bookinginfo) {
            sb.append("Room " + i + " :").append("\r\n").append(bkinfo).append("\r\n").append("\r\n");
            i += 1;
        }
        return sb.toString();
    }
    public String getHotelInfo(String address, String email) {
        StringBuilder sb = new StringBuilder();
        sb.append("Address: ").append(address).append("\r\n");
        sb.append("Email: ").append(email);
        return sb.toString();
    }

}