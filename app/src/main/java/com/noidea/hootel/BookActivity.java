package com.noidea.hootel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.User;
import com.noidea.hootel.Models.myRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class BookActivity extends AppCompatActivity {

    EditText checkIn;
    EditText checkOut;
    Button confirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        checkIn = findViewById(R.id.book_checkIndate);
        checkOut = findViewById(R.id.book_checkoutDate);
        confirmBtn = findViewById(R.id.book_confrimBtn);

        Bundle b= getIntent().getExtras();
        String roomId = b.getString("roomId");
        String userId = b.getString("userId");
        String hotelId = b.getString("hotelId");
        String branchId = b.getString("branchId");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final String[] statusCode = {""};
                String startdate = checkIn.getText().toString();
                String enddate = checkOut.getText().toString();
                String url = "https://5mbz63m677.execute-api.us-west-2.amazonaws.com/prod/booking";
                String body = getRequestBody(userId, roomId, branchId, startdate, enddate);
                myRequest myrequest = new myRequest(url, body);
                try {
                    JSONObject bookResponse = new sendRequest().execute(myrequest).get();
                    statusCode[0] = bookResponse.getString("statusCode");
                    Log.d("BOOK TEST", bookResponse.toString());
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }

                Toast happytoast = Toast.makeText(getApplicationContext(),"Booking successfully",Toast.LENGTH_LONG);
                Toast failedtoast = Toast.makeText(getApplicationContext(),"Can not book this room, please contact hotel",Toast.LENGTH_SHORT);
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
                }, 1500);

                Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                startActivity(intent);
            }
        });
    }
    private String getRequestBody(String userId, String roomId,String branchId, String startDate, String enddate) {
        Log.d("BookActivity", "UserId is ---> " + userId);
        String body = "{\n" +
                "  \"body\": {\n" +
                "    \"userId\":" + "\"" + userId + "\",\n" +
                "    \"branchId\":" + "\"" + branchId + "\",\n" +
                "    \"room\": [\n" +
                "      {\n" +
                "        \"roomId\": " + "\"" + roomId + "\", \n" +
                "        \"amenityIds\": [\n" +
                "          \"free\"\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"startDate\": " + "\"" + startDate + "\", \n" +
                "    \"endDate\": " + "\"" + enddate + "\", \n" +
                "    \"season\": \"Regular\",\n" +
                "    \"days\": \"Weekdays\"\n" +
                "  }\n" +
                "}\n";
        return body;

    }
}