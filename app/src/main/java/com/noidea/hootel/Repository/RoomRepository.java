package com.noidea.hootel.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.noidea.hootel.Database.AppDB;
import com.noidea.hootel.HootelApplication;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Room;

import java.util.List;

public class RoomRepository {
    private final AppDB appDB;
    public RoomRepository(Context context) {
        appDB = ((HootelApplication)context.getApplicationContext()).getDatabase();
    }
    public List<Room> getAllSavedBranches() {
        return appDB.roomDAO().getAll();
    }
    public List<Room> getByhotelId(String hotelId) {
        return appDB.roomDAO().getAllByhotelId(hotelId);
    }
    public boolean saveRoom(Room room) {
        new RoomAsyncTask(appDB).execute(room);
        return true;
    }
    public void updateAllSavedRoom(List<Room> rooms) {
        AsyncTask.execute(() -> appDB.roomDAO().updateRoom(rooms));
    }

    private static class RoomAsyncTask extends AsyncTask<Room, Void, Boolean> {

        private final AppDB appDB;

        private RoomAsyncTask(AppDB appDB) {
            this.appDB = appDB;
        }

        @Override
        protected Boolean doInBackground(Room... rooms) {
            Room room = rooms[0];
            try {
                appDB.roomDAO().insertRoom(room);
            }catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
