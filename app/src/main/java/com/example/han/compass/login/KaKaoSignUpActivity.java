package com.example.han.compass.login;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.han.compass.MainActivity;
import com.example.han.compass.R;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaKaoSignUpActivity extends AppCompatActivity {
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ka_kao_sign_up);

        getMyLocation();
        requestMe();
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                Toast.makeText(KaKaoSignUpActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Toast.makeText(KaKaoSignUpActivity.this, "session closed", Toast.LENGTH_SHORT).show();
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


                profileRequest(nickname, profilePath, Double.parseDouble(userProfile.getProperty("m_latitude")), Double.parseDouble(userProfile.getProperty("m_longitude")));
            }

            @Override
            public void onNotSignedUp() {
                //showSignup();
            }
        });
    }


    //gps 값 가져오기
    private void getMyLocation() {
        if (locationManager == null) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
        // provider 기지국||GPS 를 통해서 받을건지 알려주는 Stirng 변수
        // minTime 최소한 얼마만의 시간이 흐른후 위치정보를 받을건지 시간간격을 설정 설정하는 변수
        // minDistance 얼마만의 거리가 떨어지면 위치정보를 받을건지 설정하는 변수
        // manager.requestLocationUpdates(provider, minTime, minDistance, listener);

        // 10초
        long minTime = 10000;

        // 거리는 0으로 설정
        // 그래서 시간과 거리 변수만 보면 움직이지않고 10초뒤에 다시 위치정보를 받는다
        float minDistance = 0;

        MyLocationListener listener = new MyLocationListener();

//        // 허가 확인
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
        System.out.println("도착");
    }


    private void profileRequest(final String name, final String profile, final double latitude, final double longitude ) {
        final ProfileInterface profileService = ProfileAdapter.getInstance();//create(ApiInterface.class);

        String accessToken = SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "accessToken");
        System.out.println("킬리만자로의 토큰 : " + accessToken);

        Call<Kakao> profileCall = profileService.userLogin(name, profile, accessToken, latitude, longitude);
        profileCall.enqueue(new Callback<Kakao>() {
            @Override
            public void onResponse(Call<Kakao> call, Response<Kakao> response) {

                System.out.println("kk");
                Logger.d("LoginActivity 64 로그인 성공", "");
                //String name = response.body().getProfile(); //get_id();
                Kakao kakao = response.body();

                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "_id", kakao.getUser().get_id());
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "name", kakao.getUser().getName());
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "profile", kakao.getUser().getProfile());
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "userId", kakao.getUser().get_id());

                if(kakao.getUser().getName().equals(R.string.network_assignExisted)){   // 이미 있는 id인 경우
                    redirectMainActivity();
                }
                else{   // 처음 로그인 하는 경우
                    redirectMainActivity();
                }
            }

            @Override
            public void onFailure(Call<Kakao> call, Throwable t) {
                System.out.println(t.getMessage());
//                redirectLoginActivity();
                Toast.makeText(getApplicationContext(), "서버 상태가 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void redirectLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}