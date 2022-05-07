package com.noidea.hootel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HotelReservationFragment extends Fragment {
    private final String TAG = HotelReservationFragment.class.getSimpleName();

    private String hotelId;
    private static final String ARG_PARAM1 = "hotelId";
    private static final String ARG_PARAM2 = "param2";
    private JSONArray reservations;
    private TextView userBooking;
    private Button getRes;

    public static HotelReservationFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        HotelReservationFragment fragment = new HotelReservationFragment();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hotelId = getArguments().getString(ARG_PARAM1);
        }
        String param = "hotelId=".concat(hotelId);
        reservations = new JSONArray();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil util = new HttpUtil(R.string.api_booking, getContext());
                    reservations = util.getJSONArray("reservationInfo?".concat(param));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_reservation, container, false);
        userBooking = view.findViewById(R.id.hotelreservation);
        getRes = view.findViewById(R.id.refreshHoteles);
        getRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setReservation();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            setReservation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setReservation() throws JSONException {
        Log.d(TAG, "**** Start setReservation() ****");

        for (int i = 0; i < reservations.length(); i++) {
            JSONObject cur = reservations.getJSONObject(i);
            String reservationId = cur.getString("reservationId");
            Log.d(TAG, "**** reservationId ****" + reservationId);
            userBooking.setText(cur.toString());
        }
    }




}
