package com.noidea.hootel.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

import java.util.List;

@Dao
public interface ReservationDAO {
    @Query("SELECT * FROM reservationTable")
    List<Reservation> getAll();

    @Query("SELECT * FROM reservationTable WHERE reservationId IN (:reservationIds)")
    List<Reservation> getAllByIds(String[] reservationIds);

    @Query("SELECT * FROM reservationTable WHERE reservationId IN (:reservationIds)")
    List<Reservation> getByreservationId(String[] reservationIds);

    @Insert
    void insertAll(Reservation... reservations);

    @Insert
    void insertReservation(Reservation reservation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateReservation(List<Reservation> reservations);

    @Delete
    void deleteReservation(Reservation reservation);
}
