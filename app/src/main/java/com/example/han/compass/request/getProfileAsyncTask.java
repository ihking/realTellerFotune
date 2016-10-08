package com.example.han.compass.request;

import android.os.AsyncTask;

import com.example.han.compass.MyApplication;
import com.example.han.compass.login.ProfileAdapter;
import com.example.han.compass.login.ProfileInterface;
import com.example.han.compass.utils.Repo_User;
import com.example.han.compass.utils.Repo_UserList;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by sjnam on 2016. 10. 3..
 */

class GetProfileAsyncTask extends AsyncTask<Void, Void, ArrayList<Repo_User>> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Repo_User> doInBackground(Void... voids) {
        final ProfileInterface profileService = ProfileAdapter.getInstance();//create(ApiInterface.class);
        Call<Repo_UserList> ProfileList = profileService.addGetProfileNew(MyApplication.invite_idList);

        ArrayList<Repo_User> userList = new ArrayList<>();

        try {
            userList = ProfileList.clone().execute().body().getUserList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    protected void onPostExecute(ArrayList<Repo_User> repo_users) {
        super.onPostExecute(repo_users);
    }
}
