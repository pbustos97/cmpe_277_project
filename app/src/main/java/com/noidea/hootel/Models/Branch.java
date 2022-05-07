package com.noidea.hootel.Models;

import com.noidea.hootel.getJSONObj;
import com.noidea.hootel.Models.Helpers.Address;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Branch {
    private String branchId;
    private String hotelId;
    private String email;
    private String name;
    private Address address;
    private String ownerId;

    public Branch(String branchId, String hotelId, String email, String name, Address address) {
        this.branchId = branchId;
        this.hotelId = hotelId;
        this.email = email;
        this.name = name;
        this.address = address;
        this.ownerId = null;
    }

    public Branch(String branchId, String hotelId, String email, String name, Address address, String ownerId) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
}