package com.example.han.compass.category;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
public class CategoryListActivity extends FragmentActivity implements MapView.MapViewEventListener {

    ListView listView;
    CategoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);

        listView = (ListView)findViewById(R.id.placelist);
        adapter = new CategoryAdapter(this);

        listView.setAdapter(adapter);
        listView.setDivider(null);

        final Intent intent = new Intent(this.getIntent());


        String query = intent.getStringExtra("query");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem categoryItem = (CategoryItem) adapter.getItem(position);
                String placeData = categoryItem.getData();
                Toast.makeText(getApplicationContext(),placeData,Toast.LENGTH_LONG).show();


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(placeData));
                startActivity(i);
            }

        });



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

                    adapter.addItem(new CategoryItem(item.title,item.address,item.imageUrl,item.placeUrl));

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
