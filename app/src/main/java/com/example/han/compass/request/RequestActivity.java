package com.example.han.compass.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.han.compass.R;
import com.example.han.compass.category.CategoryActivity;
import com.example.han.compass.utils.Repo_User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    CircleDrawing circleDraw;
    ArrayList<Repo_User> userList;
    Button select;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        userList = getProfileList();

        circleDraw = (CircleDrawing) findViewById(R.id.chart1);
        circleDraw.start(RequestActivity.this, userList);

        select = (Button) findViewById(R.id.select);
        select.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.select:
                goCategoryActivity();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }


    private ArrayList<Repo_User> getProfileList() {

        GetProfileAsyncTask asyncTask = new GetProfileAsyncTask();
        try {
            return asyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private void goCategoryActivity(){
        Intent intent = new Intent(ctx, CategoryActivity.class);
        startActivity(intent);
    }

}
