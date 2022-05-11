package com.noidea.hootel.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.noidea.hootel.Database.AppDB;
import com.noidea.hootel.HootelApplication;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

import java.util.List;

public class ReservationRepository {
    private final AppDB appDB;
    public ReservationRepository(Context context) {
        appDB = ((HootelApplication)context.getApplicationContext()).getDatabase();
    }
    public List<Reservation> getAllSavedBranches() {
        return appDB.reservationDAO().getAll();
    }
    public Reservation getByreservationId(String reservationId) {
        return appDB.reservationDAO().getSavedReservationById(reservationId);
    }
    public boolean saveReservation(Reservation reservation) {
        new ReservationAsyncTask(appDB).execute(reservation);
        return true;
    }
    public void updateAllSavedReservation(List<Reservation> reservations) {
        AsyncTask.execute(() -> appDB.reservationDAO().updateReservation(reservations));
    }

    private static class ReservationAsyncTask extends AsyncTask<Reservation, Void, Boolean> {

        private final AppDB appDB;

        private ReservationAsyncTask(AppDB appDB) {
            this.appDB = appDB;
        }

        @Override
        protected Boolean doInBackground(Reservation... reservations) {
            Reservation reservation = reservations[0];
            try {
                appDB.reservationDAO().insertReservation(reservation);
            }catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
