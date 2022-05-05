package com.noidea.hootel.Models;

import com.noidea.hootel.HttpUtilSingle;

import org.json.JSONObject;

public class Hotel {
    private String hotelId;
    private Address address;
    private String email;
    private String name;
    private String ownerId;

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
            JSONObject hotel = HttpUtilSingle.getJSON(url);
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
