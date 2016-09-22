package com.example.han.compass.search;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.han.compass.R;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class SearchView extends LinearLayout {

    private TextView txt1;

    public SearchView(Context context,SearchItem item){
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_listitem,this,true);

        txt1 = (TextView)findViewById(R.id.searchItem);
        txt1.setText(item.Address);
    }
}
