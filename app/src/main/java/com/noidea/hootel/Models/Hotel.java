package com.noidea.hootel.Models;

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
