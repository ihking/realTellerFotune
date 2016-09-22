package com.example.han.compass.search;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hsh86 on 2016-08-20.
 */
public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<SearchItem> mItems = new ArrayList<SearchItem>();



    public SearchAdapter(Context context){
        mContext = context;
    }

    public void addItem(SearchItem it){
        mItems.add(it);
    }



    public void setListItems(List<SearchItem>lit){
        mItems = lit;
    }

    public void allDelete(){
        mItems.clear();
    }


    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SearchView itemView;
        if (convertView == null) {
            itemView = new SearchView(mContext, mItems.get(position));
        } else {
            itemView = (SearchView) convertView;

        }

        return itemView;
    }


}
