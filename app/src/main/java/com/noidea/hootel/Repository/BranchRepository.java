package com.noidea.hootel.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.noidea.hootel.Database.AppDB;
import com.noidea.hootel.HootelApplication;
import com.noidea.hootel.Models.Branch;

import java.util.List;

public class BranchRepository {
    private final AppDB appDB;
    public BranchRepository(Context context) {
        appDB = ((HootelApplication)context.getApplicationContext()).getDatabase();
    }
    public List<Branch> getAllSavedBranches() {
        return appDB.branchDAO().getAll();
    }
    public List<Branch> getByhotelId(String[] branchIds) {
        return appDB.branchDAO().getByhotelId(branchIds);
    }
    public boolean saveBranch(Branch branch) {
        new BranchAsyncTask(appDB).execute(branch);
        return true;
    }
    public void updateAllSavedHotel(List<Branch> branchList) {
        AsyncTask.execute(() -> appDB.branchDAO().updateBranch(branchList));
    }

    private static class BranchAsyncTask extends AsyncTask<Branch, Void, Boolean> {

        private final AppDB appDB;

        private BranchAsyncTask(AppDB appDB) {
            this.appDB = appDB;
        }

        @Override
        protected Boolean doInBackground(Branch... branches) {
            Branch branch = branches[0];
            try {
                appDB.branchDAO().insertBranch(branch);
            }catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
