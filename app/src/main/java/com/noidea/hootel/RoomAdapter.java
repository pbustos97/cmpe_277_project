package com.noidea.hootel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

import java.util.List;


public class RoomAdapter extends ArrayAdapter<Room> {
    private int resourceID;
    private List<Room> roomList;
    public RoomAdapter(@NonNull Context context, int textViewResourceID , @NonNull List<Room> objects) {
        super(context, textViewResourceID, objects);
        this.roomList = objects;
        resourceID = textViewResourceID;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Room room = getItem(position);

        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,null);
        }else{
            view = convertView;
        }
        TextView reservationInfo = view.findViewById(R.id.room_item);
        reservationInfo.setText(room.toString());
        return view;
    }
    public void setRoomList(List<Room> nroomList) {
        roomList.clear();
        roomList.addAll(nroomList);
        notifyDataSetChanged();
    }

}
