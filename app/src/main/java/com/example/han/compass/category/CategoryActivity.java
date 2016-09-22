package com.example.han.compass.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.han.compass.R;

/**
 * Created by hsh86 on 2016-08-20.
 * page 6 : Category
 */
public class CategoryActivity extends AppCompatActivity {

    Button cat1,cat2,cat3,cat4;

   // String query; //문화, 회의실, 맛집, 쇼핑 분류
    double latitude; //위도 -> 객체로 받아올것
    double longtitude; //경도


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setTitle("카테고리");
        cat1 = (Button)findViewById(R.id.category1); //두근
        cat2 = (Button)findViewById(R.id.category2); //소근
        cat3 = (Button)findViewById(R.id.category3); //야미
        cat4 = (Button)findViewById(R.id.category4); //바리

        //문화
        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "문화시설";
                Intent intent = new Intent(CategoryActivity.this,CategoryListActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);



            }
        });

        //회의실
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="장소대여";
                Intent intent = new Intent(CategoryActivity.this,CategoryListActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);


            }
        });

        //맛집
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="맛집";
                Intent intent = new Intent(CategoryActivity.this,CategoryListActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);


            }
        });

        //쇼핑
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="여성의류";
                Intent intent = new Intent(CategoryActivity.this,CategoryListActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);


            }
        });




    }
}