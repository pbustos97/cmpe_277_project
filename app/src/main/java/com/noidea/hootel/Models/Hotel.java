package com.noidea.hootel.Models;

public class Hotel {
    private String hotelId;
    private String address;
    private String country;
    private String email;
    private String name;
    private String ownerId;

    public Hotel(String hotelId, String address, String country, String email, String name, String ownerId) {
        this.hotelId = hotelId;
        this.address = address;
        this.country = country;
        this.email = email;
        this.name = name;
        this.ownerId = ownerId;
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
        String msg = this.address;
        msg.concat(", " + this.country);
        return msg;
    }

    public String getOwnerId() {
        return this.ownerId;
    }
}
