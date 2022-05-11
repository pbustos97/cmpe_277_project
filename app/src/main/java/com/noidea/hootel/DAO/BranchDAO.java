package com.noidea.hootel.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Room;

import java.util.List;

@Dao
public interface BranchDAO {
    @Query("SELECT * FROM branch")
    List<Branch> getAll();

    @Query("SELECT * FROM branch WHERE branchId IN (:branchIds)")
    List<Branch> getAllByIds(String[] branchIds);

    @Query("SELECT * FROM branch WHERE hotelId IN (:hotelIds)")
    List<Branch> getByhotelId(String[] hotelIds);

    @Insert
    void insertAll(Branch... branches);

    @Insert
    void insertBranch(Branch room);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateBranch(List<Branch> branches);

    @Delete
    void deleteBranch(Branch branch);

}

