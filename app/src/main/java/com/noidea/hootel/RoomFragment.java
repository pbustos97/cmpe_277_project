package com.noidea.hootel;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RoomFragment extends Fragment {

    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "hotelId";
    private static final String ARG_PARAM3 = "branchId";

    private final String TAG = RoomFragment.class.getSimpleName();

    private String userId;
    private Button reshRoom;
    private String hotelId;
    private String branchId;
    private JSONArray rooms;
    private List<Room> roomList;
    public RoomFragment() {
        // Required empty public constructor
    }

    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
            hotelId =getArguments().getString(ARG_PARAM2);
            branchId = getArguments().getString(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url = getString(R.string.api_room).concat("rommInfoByhotel?hotelId=").concat(hotelId);
        Log.d(TAG, "get rooms url ->" + url);
        try {
            rooms = new getJSONArray().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        return view;
    }

    public List<Room> getListRoom() throws JSONException, ExecutionException, InterruptedException {
        List<Room> res = new ArrayList<>();
        if (rooms == null || rooms.length() == 0) {
            return res;
        }
        for (int index = 0; index < rooms.length(); index++) {
            JSONObject room = rooms.getJSONObject(index);
            String roomId = room.getString("roomId");
            Room room1 = Room.getRoom(roomId, "https://nua5fhfin1.execute-api.us-west-2.amazonaws.com/prod/");
            res.add(room1);
        }
        return res;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            View view = getView();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    roomList = new ArrayList<>();
                    RoomAdapter roomAdapter = new RoomAdapter(getActivity(), R.layout.room_list, roomList);
                    ListView listView = view.findViewById(R.id.room_list_view);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(roomAdapter);
                            listView.setClickable(true);
                            reshRoom = view.findViewById(R.id.room_fresh_button);
                            reshRoom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        Thread t = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    roomList = getListRoom();
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            roomAdapter.setRoomList(roomList);
                                                        }
                                                    });
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        t.start();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            try {
                                roomList = getListRoom();
                            } catch (JSONException | ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.d(TAG, "test in lsitview : clicked " + i + "th item");
                                    Room room = roomList.get(i);
                                    String roomId = room.getRoomId();
                                    String roomPrice = room.getPrice();
                                    String roomType = room.getRoomType();
                                    String roomName = room.getRoomName();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("userId", userId);
                                    Log.d(TAG, "userId -->" + userId);
                                    bundle.putString("roomId", roomId);
                                    bundle.putString("roomPrice", roomPrice);
                                    bundle.putString("roomType", roomType);
                                    bundle.putString("roomName", roomName);
                                    bundle.putString("hotelId", hotelId);
                                    bundle.putString("branchId", branchId);
                                    Intent intent = new Intent(getActivity(), RoomActivity.class);
                                    intent.putExtras(bundle);
                                    onPause();
                                    startActivity(intent, bundle);
                                }
                            });
                        }
                    });


                }
            });
            t.start();
        } catch (Exception e) {
            Log.e(TAG, "Error populating ListView");
            e.printStackTrace();
        }

    }
}