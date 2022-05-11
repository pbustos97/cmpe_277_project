package com.noidea.hootel.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.noidea.hootel.getJSONArray;
import com.noidea.hootel.getJSONObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Entity(tableName = "reservationTable")
public class Reservation implements Serializable {
    @PrimaryKey
    @NonNull
    private String reservationId;

    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "checkIn")
    private boolean checkIn;
    @ColumnInfo(name = "checkOut")
    private boolean checkOut;
    @ColumnInfo(name = "startDate")
    private String startDate;
    @ColumnInfo(name = "endDate")
    private String endDate;
    @ColumnInfo(name = "season")
    private String season;
    @ColumnInfo(name = "days")
    private String days;
    @ColumnInfo(name = "reservationStatus")
    private String reservationStatus;
    @ColumnInfo(name = "custmoerFirstName")
    private String custmoerFirstName;
    @ColumnInfo(name = "custmoerLastName")
    private String custmoerLastName;
    @ColumnInfo(name = "branchId")
    private String branchId;
    @ColumnInfo(name = "hotelId")
    private String hotelId;

    @Ignore
    private List<String> roomIds;
    @Ignore
    private List<BookedRoom> bookedRoomList;
    @Ignore
    private User user;

    public Reservation(String reservationId, String price, User user, List<BookedRoom> bookedRoomList, boolean checkIn, boolean checkOut, String startDate, String endDate, String season, String days, String reservationStatus, String custmoerFirstName, String custmoerLastName, String branchId, String hotelId) {
        this.reservationId = reservationId;
        this.price = price;
        this.user = user;
        this.bookedRoomList = bookedRoomList;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.startDate = startDate;
        this.endDate = endDate;
        this.season = season;
        this.days = days;
        this.reservationStatus = reservationStatus;
        this.custmoerFirstName = custmoerFirstName;
        this.custmoerLastName = custmoerLastName;
        this.branchId = branchId;
        this.hotelId = hotelId;
        this.roomIds = new ArrayList<>();
    }

    public Reservation(String reservationId, String price, boolean checkIn, boolean checkOut, String startDate, String endDate, String season, String days, String reservationStatus, String custmoerFirstName, String custmoerLastName, String branchId, String hotelId) {
        this.reservationId = reservationId;
        this.price = price;
        this.user = null;
        this.bookedRoomList = null;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.startDate = startDate;
        this.endDate = endDate;
        this.season = season;
        this.days = days;
        this.reservationStatus = reservationStatus;
        this.custmoerFirstName = custmoerFirstName;
        this.custmoerLastName = custmoerLastName;
        this.branchId = branchId;
        this.hotelId = hotelId;
        this.roomIds = null;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookedRoom> getBookedRoomList() {
        return bookedRoomList;
    }

    public void setBookedRoomList(List<BookedRoom> bookedRoomList) {
        this.bookedRoomList = bookedRoomList;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public boolean isCheckOut() {
        return checkOut;
    }

    public void setCheckOut(boolean checkOut) {
        this.checkOut = checkOut;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getCustmoerFirstName() {
        return custmoerFirstName;
    }

    public void setCustmoerFirstName(String custmoerFirstName) {
        this.custmoerFirstName = custmoerFirstName;
    }

    public String getCustmoerLastName() {
        return custmoerLastName;
    }

    public void setCustmoerLastName(String custmoerLastName) {
        this.custmoerLastName = custmoerLastName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getHotelId() {
        return hotelId;
    }
    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
    public List<String> getRoomIds() throws JSONException, ExecutionException, InterruptedException {
        if (roomIds == null || roomIds.size() == 0) {
            retrieveRoomIds();
        }
        return roomIds;
    }

    @NonNull
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(bookedRoomList.size()).append(" room(s) between ").append(" Date: ").append(getStartDate()).append(" and ").append(getEndDate());
       return sb.toString();
    }

    public static Reservation getReservation(String reservationId, String endPoint) throws ExecutionException, InterruptedException {
        String url = endPoint.concat("reservationInfo?reservationId=" + reservationId);
        JSONObject reservation = new getJSONObj().execute(url).get();
        try {
            String custmoerFirstName = reservation.getString("custmoerFirstName");
            String custmoerLastName = reservation.getString("custmoerLastName");
            String price = reservation.getString("price");
            String userId = reservation.getString("userId");
            User user = User.getUser(userId, "https://2va96t2eh7.execute-api.us-west-2.amazonaws.com/dev/");
            boolean checkIn = reservation.getBoolean("checkIn");
            boolean checkOut = reservation.getBoolean("checkOut");
            String startDate = reservation.getString("startDate");
            String endDate = reservation.getString("endDate");
            String season = reservation.getString("season");
            String days = reservation.getString("days");
            String reservationStatus = reservation.getString("reservationStatus");
            String branchId = reservation.getString("branchId");
            String hotelId = reservation.getString("hotelId");
            List<BookedRoom> bookedRoomList = new ArrayList<>();
            JSONArray roomListArr = reservation.getJSONArray("room");
            for (int i = 0; i < roomListArr.length(); i++){
                JSONObject curRoom = roomListArr.getJSONObject(i);
                String roomId = curRoom.getString("roomId");
                JSONArray amenityIdsArr = curRoom.getJSONArray("amenityIds");
                List<String> amenityIds = new ArrayList<>();
                for(int j = 0; j < amenityIdsArr.length(); j++) {
                    amenityIds.add(amenityIdsArr.get(j).toString());
                }
                BookedRoom newRoom = new BookedRoom(roomId, amenityIds);
                bookedRoomList.add(newRoom);
            }
            return new Reservation(reservationId, price, user, bookedRoomList, checkIn, checkOut, startDate, endDate, season, days, reservationStatus,custmoerFirstName, custmoerLastName,branchId, hotelId);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void retrieveRoomIds() throws ExecutionException, InterruptedException, JSONException {
        String url = "https://5mbz63m677.execute-api.us-west-2.amazonaws.com/prod/".concat("reservationInfo?reservationId=" + reservationId);
        JSONObject reservation = new getJSONObj().execute(url).get();
        JSONArray roomListArr = reservation.getJSONArray("room");
        for (int i = 0; i < roomListArr.length(); i++) {
            JSONObject curRoom = roomListArr.getJSONObject(i);
            String roomId = curRoom.getString("roomId");
            roomIds.add(roomId);
        }
    }
    public static String getReservationByuserId(String userId) {
        JSONArray reservationArr = null;
        String[] res = new String[1];
        String param = "userId=".concat(userId);
        String url = "https://5mbz63m677.execute-api.us-west-2.amazonaws.com/prod/reservationInfo?".concat(param);
        try {
            reservationArr = new getJSONArray().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (reservationArr == null) {
            return null;
        }
        res[0] = reservationArr.toString();
        return res[0];
    }

}
