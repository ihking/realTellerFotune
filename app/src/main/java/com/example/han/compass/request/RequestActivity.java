package com.example.han.compass.request;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.han.compass.R;
import com.example.han.compass.utils.Repo_User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RequestActivity extends AppCompatActivity {

//      예전 오픈소스 활용했을 떄 코드
//    private PieChart mChart;
//
//    protected String[] mParties = new String[] {
//            "가현", "상우", "진하", "소희", "가희", "영무", "기호",
//            "", "", "", "", "", "", "",
//            "", "", "", "", "", "", "",
//            "", "" };

    CircleDrawing circleDraw;
    ArrayList<Repo_User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        userList = getProfileList();

        circleDraw = (CircleDrawing) findViewById(R.id.chart1);
        circleDraw.start(RequestActivity.this, userList);
//      예전 오픈소스 활용했을 떄 코드
//        mChart = (PieChart) findViewById(R.id.chart1);
//        mChart.setExtraOffsets(5,5,5,5);
//
//        mChart.setDragDecelerationFrictionCoef(0.35f);
//
//        mChart.setHoleColor(Color.WHITE);
//        mChart.setTransparentCircleColor(Color.WHITE);
//
//        mChart.setHoleRadius(80f);
//        mChart.setTransparentCircleRadius(0f);
//
//        mChart.setDrawCenterText(true);
//
//        mChart.setRotationAngle(0);
//
//        setData(7, 100);
//        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//        // mChart.spin(2000, 0, 360);
//
//        Legend l = mChart.getLegend();
//        l.setPosition(LegendPosition.RIGHT_OF_CHART);
//        l.setXEntrySpace(3f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

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

//      예전 오픈소스 활용했을 떄 코드
//    private void setData(int count, float range) {
//
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < count ; i++) {
//            entries.add(new PieEntry((float) (100/count), mParties[i % mParties.length]));
//        }
//
//        PieDataSet dataSet = new PieDataSet(entries, "");
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//
//        PieData data = new PieData(dataSet);
//        mChart.setData(data);
//    }



    private ArrayList<Repo_User> getProfileList() {

        GetProfileAsyncTask asyncTask = new GetProfileAsyncTask();
        try {
//            userList = asyncTask.execute().get();
            return asyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
