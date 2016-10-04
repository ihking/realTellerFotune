package com.example.han.compass.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.han.compass.MyApplication;
import com.example.han.compass.R;
import com.example.han.compass.adapter.CustomMemberAdapter;
import com.example.han.compass.login.ProfileAdapter;
import com.example.han.compass.login.SharedPreferenceUtil;
import com.example.han.compass.request.RequestActivity;
import com.example.han.compass.utils.Repo_User;
import com.example.han.compass.utils.Repo_UserList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMemberActivity extends AppCompatActivity {
    private ListView m_ListView;
    private CustomMemberAdapter member_Adapter;
    Button memberComplete;

    Call<Repo_UserList> profileCall;
//    private Handler mHandler;
//    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("멤버선택");

        m_ListView = (ListView) findViewById(R.id.memberListView);
        memberComplete = (Button) findViewById(R.id.memberComplete);

//        mHandler = new Handler();

        // 커스텀 어댑터 생성
        member_Adapter = new CustomMemberAdapter();
        // Xml에서 추가한 ListView 연결
        // ListView에 어댑터 연결
        m_ListView.setAdapter(member_Adapter);

        profileCall = ProfileAdapter.getInstance().getAll(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "_id"));

        memberComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RequestActivity.class));
            }
        });

                // 더미 자료들
//        // ListView에 아이템 추가
//        for(int i=0 ; i<1 ;i++){
//            Repo_User temp = new Repo_User();
//            temp.setName("Eric" + i);
//            temp.setHome(new Repo_Home("서울시 성북구", 38+i, 38+i));
//
//            member_Adapter.add(temp);
//        }
//        member_Adapter.add("Eric");
//        member_Adapter.add("Danny");
//        member_Adapter.add("Garrett");
//        member_Adapter.add("Tillie");
//        member_Adapter.add("Edward");
//        member_Adapter.add("김밥");
//        member_Adapter.add("치킨");

//      Log.d("TedPark","로그 내용");
    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //Drawable dr = getResources().getDrawable(R.drawable.correct);
            Button button = (Button) findViewById(arg1.getId());
            button.setBackgroundResource(R.drawable.correct_select);
            //btn_check

            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            //Toast.makeText(getApplicationContext(), m_Adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
        }
    };

    public void networkDelayed(final Call<Repo_UserList> parameterCall){
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        mHandler.postDelayed(mRunnable, 10000);
        parameterCall.clone().enqueue(new Callback<Repo_UserList>() {
            @Override
            public void onResponse(Call<Repo_UserList> call, Response<Repo_UserList> response) {

                member_Adapter.clear();

                ArrayList<Repo_User> userList = response.body().getUserList();
                for (int i = 0; i < userList.size(); i++) {
                    Repo_User temp = userList.get(i);
                    member_Adapter.add(temp);
                    Log.d("정상적 출력 : ", temp.get_id());
                }
                member_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Repo_UserList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.network_fail1, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        networkDelayed(profileCall);

        MyApplication.invite_idList.clear();
        MyApplication.invite_idList.add(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "_id"));
    }

    @Override
    public void onPause() {
        Log.d(this.getClass().getSimpleName(), "onPause()");
        super.onPause();
//        if (mHandler != null)
//            mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mHandler != null)
//            mHandler.removeCallbacks(mRunnable);
    }

}

