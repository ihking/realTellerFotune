package com.example.han.compass.category;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.han.compass.R;
import com.example.han.compass.category.CategoryView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hsh86 on 2016-08-20.
 */
public class CategoryView extends FrameLayout{

    private TextView placeTitle;
    private TextView placeAdd;
    private ImageView placeImg;

    public ImageView heart;

    private boolean h_check=false;



    public CategoryView(Context context, CategoryItem item){
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.category_listitem,this,true);

        placeTitle = (TextView)findViewById(R.id.placeTitle);
        placeAdd = (TextView)findViewById(R.id.placeAdd);
        placeImg = (ImageView)findViewById(R.id.placeImage);


        placeTitle.setText(item.name);
        placeAdd.setText(item.add);

            heart = (ImageView)findViewById(R.id.heart);
            heart.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(h_check==false){
                        heart.setImageResource(R.mipmap.heart_blue);
                        h_check=true;

                    }

                    else{
                        heart.setImageResource(R.mipmap.heart);
                        h_check=false;
                    }

                }
            });


            if(item.imageUrl.isEmpty()){
                placeImg.setImageResource(R.mipmap.default_north);
            }

            else{

                Glide.with(context).load(item.imageUrl).override(360,100).into(placeImg);


            }






    }




}
