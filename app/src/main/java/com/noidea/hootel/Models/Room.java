package com.noidea.hootel.Models;

import androidx.annotation.NonNull;

import com.noidea.hootel.getJSONObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Room {
    private String roomId;
    private String hotelId;
    private String roomType;
    private String roomName;
    private String price;

    public Room(String roomId, String hotelId, String roomType, String roomName, String price) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.roomName = roomName;
        this.price = price;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("Room Name: ").append(getRoomName()).append(", ").append("Room Type: ").append(getRoomType()).append("Room price: ").append(getPrice());
       return sb.toString();
    }

    public static Room getRoom(String roomId, String endpoint) throws ExecutionException, InterruptedException {
        String url = endpoint.concat("roomInfo?roomId=" + roomId);
        JSONObject room = new getJSONObj().execute(url).get();
        try{
            String hotelId = room.getString("hotelId");
            String roomType = room.getString("roomType");
            String roomName = room.getString("roomName");
            String price = room.getString("roomPrice");
            return new Room(roomId, hotelId, roomType, roomName, price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
