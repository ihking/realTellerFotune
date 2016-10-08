package com.example.han.compass;

import android.app.Activity;
import android.app.Application;
import android.view.Display;

import com.example.han.compass.login.KakaoSDKAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.usermgmt.response.model.UserProfile;

import java.util.ArrayList;

/**
 * Created by home on 2016-08-21.
 */
public class MyApplication extends Application {
    public static String LOGIN = "http://211.249.50.198:4001";
    public static UserProfile kakao_user;
    public static String accessToken = "";
    public static ArrayList<String> invite_idList;

    // sharedPreference 변수 설정
    public static String _id = "_id";
    public static String name = "name";
    public static String profile = "profile";
    public static String userId = "userId";
    public static String home = "home";
    public static String latitude = "latitude";
    public static String longitude = "longitude";


    private static volatile MyApplication obj = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate(){
        super.onCreate();
        obj=this;
        invite_idList = new ArrayList<>();
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static MyApplication getMyApplicationContext(){
        return obj;
    }

    public static void setCurrentActivity(Activity currentActivity){
        MyApplication.currentActivity = currentActivity;
    }
    public static Activity getCurrentActivity(){
        return currentActivity;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        obj = null;
    }

    public static Display mDisplay;

    public static void setmDisplay(Display display){
        mDisplay = display;
    }

    public static int getDisplayWidth(){
        return mDisplay.getWidth();
    }

    public static int getDisplayHeight(){
        return mDisplay.getHeight();
    }

    public int resize_Height(int width, int height, int resize_width){
        return (this.getDisplayHeight()*resize_width)/getDisplayWidth();
    }
}
