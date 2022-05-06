package com.noidea.hootel.Models;

import com.noidea.hootel.HttpUtilSingle;
import com.noidea.hootel.Models.Helpers.Address;

import org.json.JSONObject;

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

    public static Branch getBranch(String branchId, String hotelId, String endpoint) {
        String url = endpoint.concat("branch-get?branchId="+branchId+"&hotelId="+hotelId);
        try {
            JSONObject branch = HttpUtilSingle.getJSON(url);
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
