package com.noidea.hootel.Models;

import com.noidea.hootel.HttpUtilSingle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer{
    // Convert bookings to Booking class
    private ArrayList<String> bookings;
    private String currentBooking;
    private String loyaltyId;
    private User user;

    private Customer(User user, ArrayList<String> bookings, String currentBooking, String loyaltyId) {
        this.user = user;
        this.bookings = bookings;
        this.currentBooking = currentBooking;
        this.loyaltyId = loyaltyId;
    }

    public Customer getCustomer(String userId, String endpoint) {
        String url = endpoint.concat("get-role?userId=" + userId + "&role=Customer");
        JSONObject customer = HttpUtilSingle.getJSON(url);
        try {
            customer = customer.getJSONObject("user");
            User user = User.getUser(userId, endpoint);
            String loyaltyId = customer.getString("loyaltyId");
            String currentBooking = customer.getString("currentBooking");
            ArrayList<String> bookings = new ArrayList<String>();
            JSONArray booking = customer.getJSONArray("bookings");
            for (int i = 0; i < booking.length(); i++) {
                bookings.add(booking.getString(i));
            }
            return new Customer(user, bookings, currentBooking, loyaltyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
