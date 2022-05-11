package com.noidea.hootel.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.noidea.hootel.Models.Room;

import java.util.List;

@Dao
public interface RoomDAO {
    @Query("SELECT * FROM roomTable")
    List<Room> getAll();

    @Query("SELECT * FROM roomTable WHERE roomId IN (:roomIds)")
    List<Room> getAllByIds(String[] roomIds);

    @Query("SELECT * FROM roomTable WHERE roomId LIKE :roomId")
    Room getById(String roomId);

    @Insert
    void insertAll(Room... rooms);

    @Insert
    void insertRoom(Room room);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateRoom(List<Room> rooms);

    @Delete
    void deleteRoom(Room room);
}
