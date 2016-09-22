package com.example.han.compass.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<CategoryItem>mItems = new ArrayList<CategoryItem>();


    public CategoryAdapter(Context context){
        mContext = context;
    }

    public void addItem(CategoryItem it){
        mItems.add(it);
    }



    public void setListItems(List<CategoryItem>lit){
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
        CategoryView itemView;
        if (convertView == null) {
            itemView = new CategoryView(mContext, mItems.get(position));
        } else {
            itemView = (CategoryView) convertView;

        }

        return itemView;
    }



}
