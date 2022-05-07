package com.noidea.hootel.Models;

import android.util.Log;
import com.noidea.hootel.HttpUtilSingle;
import com.noidea.hootel.getJSONObj;
import com.noidea.hootel.Models.Helpers.Address;

import org.json.JSONArray;
import org.json.JSONObject;

public class Hotel {
    private static final String TAG = Hotel.class.getSimpleName();

    private String hotelId;
    private Address address;
    private String email;
    private String name;
    private String ownerId;

    public Hotel(String hotelId, Address address, String email, String name) {
        this.hotelId = hotelId;
        this.address = address;
        this.email = email;
        this.name = name;
        this.ownerId = null;
    }

    public Hotel(String hotelId, Address address, String email, String name, String ownerId) {
        this.hotelId = hotelId;
        this.address = address;
        this.email = email;
        this.name = name;
        this.ownerId = ownerId;
    }

    public static Hotel getHotel(String hotelId, String endpoint) {
        String url = endpoint.concat("hotel-get?hotelId="+hotelId);
        try {
            JSONObject hotel = new getJSONObj().execute(url).get();
            hotelId = hotel.getString("hotelId");
            String address = hotel.getString("address");
            String country = hotel.getString("country");
            Address address1 = new Address(address, country);
            String email = hotel.getString("email");
            String name = hotel.getString("name");
            String ownerId = hotel.getString("ownerId");
            return new Hotel(hotelId, address1, email, name, ownerId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getHotelList(String endpoint) {
        final String[] msg = {null};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = endpoint.concat("hotel-get?hotelId=-1");
                try {
                    JSONObject obj = HttpUtilSingle.getJSON(url);
                    JSONArray arr = obj.getJSONArray("hotels");
                    Log.d(TAG, arr.toString());
                    msg[0] = arr.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg[0];
    }



    public String getName() {
        return this.name;
    }

    public String getHotelId() {
        return this.hotelId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address.getFullAddress();
    }

    public String getOwnerId() {
        return this.ownerId;
    }
}