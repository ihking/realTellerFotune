package com.example.han.compass.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class FavoriteAdapter extends BaseAdapter {


    private Context mContext;
    private List<SearchItem> mItems = new ArrayList<SearchItem>();

    private Button btn1;



    public FavoriteAdapter(Context context ){
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

    public void itemDelete(int position){
        mItems.remove(position);
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
       FavoriteView itemView;

        if (convertView == null) {
            itemView = new FavoriteView(mContext, mItems.get(position));
        } else {
            itemView = (FavoriteView) convertView;
        }

        return itemView;
    }
}
