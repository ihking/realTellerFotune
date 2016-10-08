package com.example.han.compass.login;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.han.compass.MainActivity;
import com.example.han.compass.MapApiConst;
import com.example.han.compass.MyApplication;
import com.example.han.compass.R;
import com.example.han.compass.category.CategoryItem;
import com.example.han.compass.map_search.HomeItem;
import com.example.han.compass.map_search.Item;
import com.example.han.compass.map_search.OnFinishHomeListener;
import com.example.han.compass.map_search.OnFinishSearchListener;
import com.example.han.compass.map_search.Searcher;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by home on 2016-09-16.
 */
class MyLocationListener implements LocationListener {
    double latitude, longitude;
    // 위치정보는 아래 메서드를 통해서 전달된다.
    UserProfile userProfile;
    String apikey = MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY;

    Context context;
    String home;

    public MyLocationListener(Context context){
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        //appendText("onLocationChanged()가 호출되었습니다");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        String inputCoordSystem = "WGS84";

        System.out.println("lati" + String.valueOf(latitude));
        Log.d("latitude", "latitude" + String.valueOf(latitude));

        Searcher searcher = new Searcher();

        searcher.searchHomeFromPosition(context, apikey, latitude, longitude, inputCoordSystem, new OnFinishHomeListener() {
                    @Override
                    public void onSuccess(HomeItem itemList) {
                        home = itemList.fullName;
                        Log.d("집 정보 못 가져왔어d", "MyLoactionListener 62");
                        requestUpdateProfile();
                    }

                    @Override
                    public void onFail() {
                        Log.d("집 정보 못 가져왔어", "MyLoactionListener 62");
                    }
                }
        );




    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    private void requestUpdateProfile() {
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put("m_home", String.valueOf(home));
        properties.put("m_longitude", String.valueOf(longitude));
        properties.put("m_latitude", String.valueOf(latitude));

        UserManagement.requestUpdateProfile(new ApiResponseCallback<Long>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(Long userId) {
                //UserProfile.updateUserProfile(userProfile,properties);
                // userProfile.updateUserProfile(properties);
                if (userProfile != null) {
                    userProfile.saveUserToCache();
                    System.out.println("MyLocationListener 112");
                }
                requestMe();
            }
        }, properties);

    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Toast.makeText(context, "session closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.d("UserProfile : ", userProfile.toString());

//                Picasso.with(KaKaoSignUpActivity.this)
//                        .load(userProfile.getThumbnailImagePath())
//                        .fit()
//                        .into(profile);

                String nickname = userProfile.getNickname();
                String profilePath = userProfile.getThumbnailImagePath();
                double property_latitude = Double.parseDouble(userProfile.getProperty("m_latitude"));
                double property_longitude = Double.parseDouble(userProfile.getProperty("m_longitude"));
                String property_home = userProfile.getProperty("m_home");


                profileRequest(nickname, profilePath, property_home, property_latitude, property_longitude);
            }

            @Override
            public void onNotSignedUp() {
                //showSignup();
            }
        });
    }

    private void profileRequest(final String name, final String profile, final String home, final double latitude, final double longitude) {
        final ProfileInterface profileService = ProfileAdapter.getInstance();//create(ApiInterface.class);

        String accessToken = SharedPreferenceUtil.getSharedPreference(context, "accessToken");
        System.out.println("킬리만자로의 토큰 : " + accessToken);

        Call<Kakao> profileCall = profileService.userLogin(name, profile, home, accessToken, latitude, longitude);
        profileCall.clone().enqueue(new Callback<Kakao>() {
            @Override
            public void onResponse(Call<Kakao> call, Response<Kakao> response) {

                System.out.println("kk");
                Logger.d("LoginActivity 64 로그인 성공", "");
                //String name = response.body().getProfile(); //get_id();
                Kakao kakao = response.body();

                SharedPreferenceUtil.putSharedPreference(context, MyApplication._id, kakao.getUser().get_id());
                SharedPreferenceUtil.putSharedPreference(context, MyApplication.name, kakao.getUser().getName());
                SharedPreferenceUtil.putSharedPreference(context, MyApplication.home, kakao.getUser().getHome().getName());
                SharedPreferenceUtil.putSharedPreference(context, MyApplication.latitude, kakao.getUser().getHome().getLatitude() + "");
                SharedPreferenceUtil.putSharedPreference(context, MyApplication.longitude, kakao.getUser().getHome().getLongitude() + "");
                SharedPreferenceUtil.putSharedPreference(context, MyApplication.profile, kakao.getUser().getProfile());
                SharedPreferenceUtil.putSharedPreference(context, MyApplication.userId, kakao.getUser().get_id());

                if (kakao.getUser().getName().equals(R.string.network_assignExisted)) {   // 이미 있는 id인 경우
                    redirectMainActivity();
                } else {   // 처음 로그인 하는 경우
                    redirectMainActivity();
                }
            }

            @Override
            public void onFailure(Call<Kakao> call, Throwable t) {
                System.out.println(t.getMessage());
//                redirectLoginActivity();
                Toast.makeText(context, "서버 상태가 원활하지 않습니다. 5초 후 재 시도합니다", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        profileRequest(name, profile, home, latitude, longitude);
                    }
                }, 5000);

            }
        });
    }


    private void redirectMainActivity() {
        context.startActivity(new Intent(context, MainActivity.class));
        ((KaKaoSignUpActivity) context).finish();
    }

}
