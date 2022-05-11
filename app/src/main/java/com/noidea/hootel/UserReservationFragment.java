package com.noidea.hootel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.noidea.hootel.Models.BookedRoom;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;
import com.noidea.hootel.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class UserReservationFragment extends Fragment{
    private final String TAG = UserReservationFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "reservationList";
    private static final String ARG_PARAM3 = "accessToken";

    private String userId;
    private JSONArray reservations;
    private List<Reservation> reservationList;
    private Button getRes;
    ListView listView;
    ReservationAdaper reservationAdaper;
    public UserReservationFragment() {
    }

    public static UserReservationFragment newInstance(String param1, String param2) {

        UserReservationFragment fragment = new UserReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reservationList = new ArrayList<>();
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
            Log.d(TAG, "userId here is " + userId);
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reservation, container, false);

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
                        reservations = new JSONArray( Reservation.getReservationByuserId(userId) );
                        for (int index = 0; index < reservations.length(); index++) {
                            JSONObject reservation = reservations.getJSONObject(index);
                            String reservationId = reservation.getString("reservationId");
                            Reservation reservation1 = Reservation.getReservation(reservationId, "https://5mbz63m677.execute-api.us-west-2.amazonaws.com/prod/");
                            reservationList.add(reservation1);
                        }
                    } catch (JSONException | ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View view = getView();
                            reservationAdaper = new ReservationAdaper(getActivity(), R.layout.reservaion_list, reservationList);
                            listView = view.findViewById(R.id.list_view);
                            listView.setAdapter(reservationAdaper);
                            listView.setClickable(true);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.d(TAG, "test in lsitview : clicked " + i + "th item");
                                    Reservation reservation = reservationList.get(i);
                                    String branchId = reservation.getBranchId();
                                    String hotelId = reservation.getHotelId();
                                    List<String> roomIds = null;
                                    try {
                                        roomIds = reservation.getRoomIds();
                                    } catch (JSONException | ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    Branch branch = null;
                                    ArrayList<String> roomInfo = new ArrayList<>();
                                    try {
                                        branch = Branch.getBranch(branchId, hotelId, "https://2pai97g6d5.execute-api.us-west-2.amazonaws.com/dev/");
                                        for (String roomId : roomIds) {
                                            Room room = Room.getRoom(roomId, "https://nua5fhfin1.execute-api.us-west-2.amazonaws.com/prod/");
                                            roomInfo.add(room.toString());
                                        }
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    Bundle bundle = new Bundle();
                                    bundle.putString("custmoerLastName", reservation.getCustmoerFirstName() + " " + reservation.getCustmoerLastName());
                                    bundle.putString("startDate", reservation.getStartDate());
                                    bundle.putString("endDate", reservation.getEndDate());
                                    bundle.putString("Address", branch.getAddress());
                                    bundle.putString("price", reservation.getPrice());
                                    bundle.putString("email", branch.getEmail());
                                    bundle.putString("reservationId", reservation.getReservationId());
                                    bundle.putStringArrayList("bookingInfo", roomInfo);
                                    bundle.putString("status", reservation.getReservationStatus());
                                    bundle.putString("userId", getArguments().getString(ARG_PARAM1));
                                    bundle.putString("accessToken", getArguments().getString(ARG_PARAM3));
                                    Intent intent = new Intent(getActivity(),ReservationActivity.class);
                                    intent.putExtras(bundle);
                                    onPause();
                                    startActivity(intent);
                                }
                            });
                            getRes = view.findViewById(R.id.freshRes);
                            getRes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        if (reservationList == null || reservationList.size() == 0) {
                                            reservationList = getListReservation();
                                        }
                                    } catch (JSONException | ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    reservationAdaper.setReservations(reservationList);
                                }
                            });
                        }
                    });
                }
            });
            t.start();
        } catch (Exception e) {
            Log.e(TAG, "Error population reservations");
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public List<Reservation> getListReservation() throws JSONException, ExecutionException, InterruptedException {
        List<Reservation> res = new ArrayList<>();
        for (int index = 0; index < reservations.length(); index++) {
            JSONObject reservation = reservations.getJSONObject(index);
            String reservationId = reservation.getString("reservationId");
            Reservation reservation1 = Reservation.getReservation(reservationId, "https://5mbz63m677.execute-api.us-west-2.amazonaws.com/prod/");
            res.add(reservation1);
        }
        return res;
    }

}
