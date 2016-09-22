package com.example.han.compass.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by han on 16. 8. 21..
 */
public interface ApiUtils {
    @GET("/users/getName")
    public Call<Repo_User> retroTest(@Query("name") String profile);
}
