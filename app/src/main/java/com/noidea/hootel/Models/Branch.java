package com.noidea.hootel.Models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.noidea.hootel.HttpUtilSingle;
import com.noidea.hootel.getJSONObj;

import com.noidea.hootel.Models.Helpers.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
@Entity(tableName = "branch")
public class Branch implements Serializable {
    private static final String TAG = Branch.class.getSimpleName();

    @NonNull
    @PrimaryKey
    private String branchId;

    @ColumnInfo(name = "hotelId")
    private String hotelId;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "ownerId")
    private String ownerId;

    public Branch(String branchId, String hotelId, String email, String name, Address address) {
        this.branchId = branchId;
        this.hotelId = hotelId;
        this.email = email;
        this.name = name;
        this.address = address.getAddress();
        this.ownerId = null;
    }

    public Branch(String branchId, String hotelId, String email, String name, Address address, String ownerId) {
        this.branchId = branchId;
        this.hotelId = hotelId;
        this.email = email;
        this.name = name;
        this.address = address.getAddress();
        this.ownerId = ownerId;
    }
    public Branch(String branchId, String hotelId, String email, String name, String address, String ownerId) {
        this.branchId = branchId;
        this.hotelId = hotelId;
        this.email = email;
        this.name = name;
        this.address = address;
        this.ownerId = ownerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public static Branch getBranch(String branchId, String hotelId, String endpoint) throws ExecutionException, InterruptedException {
        String url = endpoint.concat("branch-get?branchId="+branchId+"&hotelId="+hotelId);
        JSONObject branch = null;
        branch = new getJSONObj().execute(url).get();
        try {

            branch = branch.getJSONObject("branch");
            String address = branch.getString("address");
            String country = branch.getString("country");
            Address address1 = new Address(address, country);
            String email = branch.getString("email");
            String name = branch.getString("branchName");
            return new Branch(branchId, hotelId, email, name, address1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBranchList(String endpoint) {
        final String[] msg = {null};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = endpoint.concat("branch-get?hotelId=-1&branchId=-1");
                try {
                    JSONObject obj = HttpUtilSingle.getJSON(url);
                    Log.d(TAG, obj.toString());
                    JSONObject arr = obj.getJSONObject("branches");
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
}

