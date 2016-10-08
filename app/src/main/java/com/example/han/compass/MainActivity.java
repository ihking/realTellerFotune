package com.example.han.compass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.han.compass.category.CategoryActivity;
import com.example.han.compass.login.LoginActivity;
import com.example.han.compass.login.SharedPreferenceUtil;
import com.example.han.compass.member.SelectMemberActivity;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView m_ListView;
    private CustomAdapter m_Adapter;
    //private ArrayAdapter<String> m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        setTitle("약속 리스트");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), SelectMemberActivity.class));

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //Intent
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

//        //setting button 관련 (일단은 로그아웃으로 구현)
        View navHeaderLayout = navigationView.getHeaderView(0);
        navHeaderLayout.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onNotSignedUp() {

                    }

                    @Override
                    public void onSuccess(Long result) {
                        redirectLogoutActivity();
                    }
                });
//                UserManagement.requestLogout(new LogoutResponseCallback() {
//                    @Override
//                    public void onCompleteLogout() {
//                        //로그아웃 성공 후 하고싶은 내용 코딩 ~
//                        redirectLogoutActivity();
//                    }
//                });
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter();
        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.mainListView);
        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView에 아이템 추가
        m_Adapter.add("2016.08.22");
        m_Adapter.add("2016.08.22");
        m_Adapter.add("2016.08.22");
        m_Adapter.add("2016.08.22");
        m_Adapter.add("2016.08.22");
        m_Adapter.add("2016.08.22");
        m_Adapter.add("2016.08.22");

        //Retrofit
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint("https://api.github.com")
//                .build();

    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            //Toast.makeText(getApplicationContext(), m_Adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(getApplication(), CategoryActivity.class));
        }
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void redirectLogoutActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);

        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), MyApplication._id, "");
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), MyApplication.name, "");
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), MyApplication.profile, "");
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), MyApplication.userId, "");

        startActivity(intent);
        finish();
    }
}
