package com.example.han.compass.login;

import com.example.han.compass.utils.Repo_UserList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by home on 2016-09-16.
 */
public interface ProfileInterface {
//    @GET("/users/getName")
//    public Call <Kakao> userGetName(@Query("name") String profile);

    @FormUrlEncoded
    @POST("/users/login")
    public Call <Kakao> userLogin(@Field("name") String name, @Field("profile") String profile, @Field("profile") String home, @Field("accessToken") String accessToken, @Field("latitude") double latitude, @Field("longitude") double longitude);

    @FormUrlEncoded
    @POST("/users/getAll")
    public Call <Repo_UserList> getAll(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("/rooms/add/getProfile/new")
    public Call <Repo_UserList> addGetProfileNew(@Field("userList") ArrayList<String> userList);
}

