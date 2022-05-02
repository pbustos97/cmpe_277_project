package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}