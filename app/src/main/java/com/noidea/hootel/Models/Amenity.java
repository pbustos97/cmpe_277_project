package com.noidea.hootel.Models;

import com.noidea.hootel.HttpUtilSingle;

import org.json.JSONException;
import org.json.JSONObject;

public class Amenity {
    private String amenityId;
    private String hotelId;
    private String amenityName;
    private String amenityPrice;

    public Amenity(String amenityId, String hotelId, String amenityName, String amenityPrice) {
        this.amenityId = amenityId;
        this.hotelId = hotelId;
        this.amenityName = amenityName;
        this.amenityPrice = amenityPrice;
    }

    public String getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(String amenityId) {
        this.amenityId = amenityId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public void setAmenityName(String amenityName) {
        this.amenityName = amenityName;
    }

    public String getAmenityPrice() {
        return amenityPrice;
    }

    public void setAmenityPrice(String amenityPrice) {
        this.amenityPrice = amenityPrice;
    }

    public Amenity getAmenity(String amenityId, String endpoint) {
        String url = endpoint.concat("amenityInfo?amenityId=" + amenityId);
        JSONObject amenity = HttpUtilSingle.getJSON(url);
        try {
            String hotelId = amenity.getString("hotelId");
            String amenityName = amenity.getString("amenityName");
            String amenityPrice = amenity.getString("amenityPrice");
            return new Amenity(amenityId, hotelId, amenityName, amenityPrice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
