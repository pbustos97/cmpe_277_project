package com.noidea.hootel.Models;

import com.noidea.hootel.HttpUtilSingle;
import com.noidea.hootel.R;

import org.json.JSONObject;

public class Branch {
    private String branchId;
    private String hotelId;
    private String email;
    private String name;
    private Address address;

    public Branch getBranch(String branchId, String hotelId, String endpoint) {
        String url = endpoint.concat("branch-get?branchId="+branchId+"&hotelId="+hotelId);
        try {
            JSONObject branch = HttpUtilSingle.getJSON(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
