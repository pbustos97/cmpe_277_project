package com.noidea.hootel.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.noidea.hootel.BranchListAdapter;
import com.noidea.hootel.HotelActivity;
import com.noidea.hootel.HotelListAdapter;
import com.noidea.hootel.Models.Helpers.Address;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.User;
import com.noidea.hootel.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelFragment extends Fragment {
    private final String TAG = HotelFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "hotels";
    private static final String ARG_PARAM2 = "branches";
    private static final String ARG_PARAM3 = "userId";
    private static final String ARG_PARAM4 = "accessToken";

    private String hotelStr;
    private String branchStr;

    private ArrayList<Hotel> hotels;
    private ArrayList<Branch> branches;

    private ListView hotelList;

    public HotelFragment() {
    }

    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotels = new ArrayList<Hotel>();
        branches = new ArrayList<Branch>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        hotelStr = Hotel.getHotelList(getString(R.string.api_hotel));
                        branchStr = Branch.getBranchList(getString(R.string.api_hotel));
                        Log.d(TAG, "hotelStr: ".concat(hotelStr));
                        Log.d(TAG, "branchStr: ".concat(branchStr));
                        JSONArray arr = new JSONArray(hotelStr);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            String hotelId = obj.getString("hotelId");
                            Address address = new Address(obj.getString("Address"),
                                    obj.getString("Country"));
                            String email = obj.getString("email");
                            String name = obj.getString("HotelName");
                            String ownerId = obj.getString("ownerId");
                            hotels.add(new Hotel(hotelId, address, email, name, ownerId));
                        }
                        JSONObject hotelsObj = new JSONObject(branchStr);
                        for (int i = 0; i < hotels.size(); i++) {
                            String hotelId = hotels.get(i).getHotelId();
                            Log.d(TAG, "branch hotelId: ".concat(hotelId));
                            Log.e(TAG, "hotelId obj: ".concat(hotelsObj.getString(hotelId)));
                            if (!hotelsObj.getString(hotelId).equals("null")) {
                                arr = new JSONArray(hotelsObj.getString(hotelId));
                                Log.d(TAG, "branchArr: ".concat(arr.toString()));
                                for (int j = 0; j < arr.length(); j++) {
                                    JSONObject obj = arr.getJSONObject(j);
                                    Address address = new Address(obj.getString("Address"),
                                            obj.getString("Country"));
                                    String branchId = obj.getString("branchId");
                                    String branchName = obj.getString("BranchName");
                                    String branchEmail = obj.getString("email");
                                    String ownerId = obj.getString("ownerId");
                                    branches.add(new Branch(branchId, hotelId, branchEmail, branchName, address, ownerId));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }





                    View view = getView();
                    BranchListAdapter listAdapter = new BranchListAdapter(getContext(), branches);
                    for (int i = 0; i < branches.size(); i++) {
                        Log.d(TAG, "branchId: ".concat(branches.get(i).getBranchId()));
                    }
                    hotelList = view.findViewById(R.id.HotelListView);
                    hotelList.setAdapter(listAdapter);
                    hotelList.setClickable(true);
                }
            });
            t.start();
            t.join();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                            Log.d(TAG, "select item: " + pos);
                            Intent intent = new Intent(getActivity(), HotelActivity.class);
                            Branch branch = branches.get(pos);
                            Log.d(TAG, "branch: " + branch.getHotelId());
                            intent.putExtra("hotelId", branch.getHotelId());
                            intent.putExtra("branchId", branch.getBranchId());
                            intent.putExtra("hotelName", branch.getName());
                            intent.putExtra("hotelAddress", branch.getAddress());
                            intent.putExtra("hotelEmail", branch.getEmail());
                            intent.putExtra("userId", User.getUserId());
                            intent.putExtra("accessToken", User.getAccessToken());
                            startActivity(intent);
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error getting hotel list");
            e.printStackTrace();
        }
    }
}