package com.example.han.compass.search;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;


import com.example.han.compass.R;
import com.example.han.compass.map_search.Item;

import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Search_MainActivity extends FragmentActivity{

    MapView mMapView;

    //EditText searchEdit;
    Button deleteBtn;
   // HashMap<Integer,Item> TagItem = new HashMap<Integer, Item>();

    ListView listView;
    FavoriteAdapter adapter;
    SearchView search;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_setting);


        search = (SearchView) findViewById(R.id.search);
        int searchTextViewId = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchTextView = (TextView) search.findViewById(searchTextViewId);
        searchTextView.setHintTextColor(getResources().getColor(R.color.pinksh_gray));
        searchTextView.setTextSize(13);

        search.setQueryHint("자주 출발하는 장소를 저장해두세요!");


        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(Search_MainActivity.this, SearchAcitvity.class);
                intent.putExtra("query",query);

                startActivity(intent);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub


                return false;
            }
        });




        listView = (ListView)findViewById(R.id.favoriteList);
        adapter = new FavoriteAdapter(this);
        listView.setAdapter(adapter);

        listView.setDivider(null);



        Intent newIntent = new Intent(getIntent());
        String choice = newIntent.getStringExtra("choice");

        if(choice!=null){
            adapter.addItem(new SearchItem(choice));

        }





    }
}
