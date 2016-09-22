package com.example.han.compass.search;

import android.content.Intent;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.han.compass.MapApiConst;
import com.example.han.compass.R;
import com.example.han.compass.map_search.Item;
import com.example.han.compass.map_search.OnFinishSearchListener;
import com.example.han.compass.map_search.Searcher;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class SearchAcitvity extends FragmentActivity implements MapView.MapViewEventListener {

    ListView listView;
    SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_search);

        listView = (ListView)findViewById(R.id.searchList);
        adapter = new SearchAdapter(this);
        listView.setAdapter(adapter);

        listView.setDivider(null);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchItem curItem = (SearchItem) adapter.getItem(position);
                String curData = curItem.getData();

                Intent intent2 = new Intent(SearchAcitvity.this,Search_MainActivity.class);
                intent2.putExtra("choice",curData);
                startActivity(intent2);


            }

        });




        final Intent intent = new Intent(this.getIntent());


                String query = intent.getStringExtra("query");

                double latitude = 37.537229; //위도
                double longtitude = 127.005515; //경도
                int radius = 10000; //반경거리 1~10000 meter 단위
                int page = 1;
                String apikey = MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY;

                Searcher searcher = new Searcher();
                searcher.searchKeyword(getApplicationContext(), query, latitude, longtitude, radius, page, apikey, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        for (int i = 0; i < itemList.size(); i++) {
                            Item item = itemList.get(i);

                            adapter.addItem(new SearchItem(item.title));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });


                        }

                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(getApplication(),"실패",Toast.LENGTH_LONG).show();

                    }
                });






    }






    public void onMapViewInitialized(MapView mapView) {


    }



    private void showResult(List<Item> itemList) {
        adapter = new SearchAdapter(this);
        listView.setAdapter(adapter);

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            adapter.addItem(new SearchItem(item.title));

        }


    }



    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {

    }


}
