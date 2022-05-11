package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.noidea.hootel.DAO.BranchDAO;
import com.noidea.hootel.DAO.HotelDAO;
import com.noidea.hootel.Database.AppDB;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;

import com.noidea.hootel.Fragments.LoginFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDB appDB = Room.databaseBuilder(getApplicationContext(),
                AppDB.class, "AppDB").build();

//        BranchDAO branchDAO = appDB.branchDAO();
//        List<Branch> branchList = branchDAO.getAll();
//        HotelDAO hotelDAO = appDB.hotelDAO();
//        List<Hotel> hotelList = hotelDAO.getAll();

        Bundle bundle = new Bundle();
        bundle.putString("hotels", Hotel.getHotelList(getApplicationContext().getString(R.string.api_hotel)));
        this.addFragment(R.id.main_container, LoginFragment.class, bundle);

    }

    protected void addFragment(int container, Class fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(container, fragment, bundle)
                .commit();
    }
}