package com.example.han.compass.category;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class CategoryItem {
    public String name;
    public String add;
    public String imageUrl;
    public String placeUrl;

    public CategoryItem(String n, String a, String i,String p){
        this.name = n;
        this.add = a;
        this.imageUrl = i;
        this.placeUrl = p;

    }

    public String getData(){
        return placeUrl;
    }

}
