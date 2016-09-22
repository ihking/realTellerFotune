package com.example.han.compass.login;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.response.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by home on 2016-09-16.
 */
class MyLocationListener implements LocationListener {
    double latitude, longitude;
    // 위치정보는 아래 메서드를 통해서 전달된다.
    UserProfile userProfile;

    @Override
    public void onLocationChanged(Location location) {
        //appendText("onLocationChanged()가 호출되었습니다");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        System.out.println("lati" + String.valueOf(latitude));
        Log.d("latitude", "latitude" + String.valueOf(latitude));

        requestUpdateProfile();
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
                }

            }
        }, properties);

    }
}
