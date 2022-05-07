package com.noidea.hootel.Models;

import java.util.List;

public class BookedRoom {
    private String roomId;
    private List<String> amenityIds;
    private Float price;

    public BookedRoom(String roomId, List<String> amenityIds) {
        this.roomId = roomId;
        this.amenityIds = amenityIds;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getAmenityIds() {
        return amenityIds;
    }

    public void setAmenityIds(List<String> amenityIds) {
        this.amenityIds = amenityIds;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
