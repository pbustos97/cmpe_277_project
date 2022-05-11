package com.noidea.hootel.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Room;

import java.util.List;

@Dao
public interface HotelDAO {
    @Query("SELECT * FROM hotel")
    List<Hotel> getAll();

    @Query("SELECT * FROM hotel WHERE hotelId IN (:hotelIds)")
    List<Hotel> getAllByIds(String[] hotelIds);

    @Query("SELECT * FROM hotel WHERE hotelId LIKE :hotelId")
    Hotel getById(String hotelId);

    @Insert
    void insertAll(Hotel... hotels);

    @Insert
    void insertHotel(Hotel hotel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateHotel(List<Hotel> hotels);

    @Delete
    void deleteHotel(Hotel hotel);
}
