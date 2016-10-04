package com.example.han.compass.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.han.compass.MyApplication;
import com.example.han.compass.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class LoginActivity extends AppCompatActivity {

    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen(); //

//        //디버그 키
//         try {
//         PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//         for (Signature signature : info.signatures) {
//         MessageDigest md = MessageDigest.getInstance("SHA");
//         md.update(signature.toByteArray());
//         String something = new String(Base64.encode(md.digest(),0));
//         Log.d("Hash key", something);
//         //Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//         }
//         } catch (PackageManager.NameNotFoundException e) {
//
//         } catch (NoSuchAlgorithmException e) {
//
//         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private void redirectSignUpActivity() {
        startActivity(new Intent(this, KaKaoSignUpActivity.class));
        finish();
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.d("LoginActivity 64 로그인 성공", "");
            if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "accessToken").equals("")){
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "accessToken", Session.getCurrentSession().getAccessToken());
                Toast.makeText(getApplicationContext(), MyApplication.accessToken + "", Toast.LENGTH_SHORT).show();
            }
            redirectSignUpActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }
}
