package com.example.han.compass.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.han.compass.R;

import java.util.List;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class FavoriteView extends LinearLayout {

    private TextView txt1;
    private Button btn1;



    public FavoriteView(Context context, SearchItem item){
        super(context);



        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.favorite_listitem,this,true);

        txt1 = (TextView)findViewById(R.id.searchItem);
        txt1.setText(item.Address);

        btn1 = (Button)findViewById(R.id.favoriteDelete);
        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "쨘", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
