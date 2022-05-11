package com.noidea.hootel.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.noidea.hootel.DAO.BranchDAO;
import com.noidea.hootel.DAO.HotelDAO;
import com.noidea.hootel.DAO.ReservationDAO;
import com.noidea.hootel.DAO.RoomDAO;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

@Database(entities = {Branch.class , Hotel.class, Room.class, Reservation.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract BranchDAO branchDAO();
    public abstract HotelDAO hotelDAO();
    public abstract RoomDAO roomDAO();
    public abstract ReservationDAO reservationDAO();
}

