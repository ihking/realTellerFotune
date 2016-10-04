package com.example.han.compass.request;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.han.compass.R;
import com.example.han.compass.utils.Repo_User;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by home on 2016-09-19.
 */
public class CircleDrawing extends View {

    int cur_angle = 0;
    Thread animator = null;
    static Context context;
    int peopleNum = 0;

    Bitmap temp_Bitmap;
    ArrayList<Repo_User> userList;

    private int count = -1;
    private Paint pnt;
    private final int width = 10;
    int deviceWidth;
    int deviceHeight;

//    private Path path;
//    private int x, y;
//    private boolean isInitialize = false;
//    private final float CircleSize = 50.0f;

    // View에는 필요에 따른 3종류의 생성자가 View에 구현되어 있다.

    // 첫번째 생성자는 다음과 같다.
    public CircleDrawing(Context context) {
        super(context);
        this.context = context;
    }

    // 이 생성자는 XML상에서 CanvasView를 구현하기 위해서 사용된다.
    // 즉, XML상에서 Custom View를 사용하기 위해서는 반드시 구현해줘야 한다.
    // 이 생성자는 Parameter로 Context와 더불어 AttributeSet를 요구한다.
    // 여기서 AttributeSet 이란 Attribute들의 집합체로 Resource File (XML파일)에서 사용되는 속성들을 의미한다.
    public CircleDrawing(Context context, AttributeSet attribs) {
        super(context, attribs);
        this.context = context;
    }

    //  두번째와 다른 점은 Parameter로 int defStyle을 요구한다는 점인데
//  만약 defStyle이 0일 경우에는 현재 어떤 Theme도 적용시키지 않게 된다.
//  즉, defStyle에 적용되는 Value에 따라 theme를 변경시킬 수 있게 된다.
    public CircleDrawing(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    void start(Context context, ArrayList<Repo_User> user_List) {
        this.context = context;
        this.peopleNum = user_List.size();
        userList = user_List;

//        animator = new Thread(this);
//        animator.start();
    }

//    @Override
//    public void run() {
//        while (true) {
//            cur_angle += (360 / peopleNum);
//
//            postInvalidate();
//
//            try {
//                Thread.sleep(900, 0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        System.out.println("onDraw");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;
        final int radius, mini_radius;

        radius = deviceWidth / 4;
        mini_radius = 15;

        pnt = getPrint(R.color.requestCircleWait);
        canvas.drawCircle(deviceWidth / 2, deviceHeight / 5 * 1, radius, pnt);
//        System.out.println("onDraw");

        for (double angle = 0; angle < 365; angle += ((double) 365 / peopleNum)) {
            count++;
            final int x_base = deviceWidth / 2;
            final int y_base = deviceHeight / 5 * 1;

            final int x, y;
            double rad;

            rad = angle * 3.14 / 180;

            x = (int) ((radius) * Math.sin(rad));
            y = (int) ((radius) * Math.cos(rad));

            GlideAsyncTask asyncTask = new GlideAsyncTask();
            try {
                temp_Bitmap = asyncTask.execute().get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            canvas.drawBitmap(temp_Bitmap, x_base + x - temp_Bitmap.getWidth()/2, y_base + y - temp_Bitmap.getHeight()/2, pnt);
//                canvas.drawBitmap(temp_Bitmap, x_base + x, y_base + y, pnt);
//            canvas.drawCircle(x_base + x, y_base + y, 20, pnt);
        }
    }

    // color 타입에 따른 색 리턴
    public Paint getPrint(int id) {
        Paint paint;

        paint = new Paint();
        paint.setAntiAlias(true);             //그림이 화면에 표현될 때 더욱 자연스러워 보이게 한다. 왜냐하면 그려지는 그림 주변에 옅은 색상을 더 뿌려서 화면에 표현되게 하기 때문이다.
        paint.setColor(context.getResources().getColor(id));   //
        paint.setStrokeWidth(width);
        paint.setStyle(Paint.Style.STROKE);   // 인수에 따라 그려지는 그림이 달라진다.

        return paint;
    }


    // 프로필 이미지 가져오는 asyncTask
    public class GlideAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        Bitmap bitmap = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = getImageFromURL(userList.get(count).getProfile());

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }


    // onBackground에서 실행되는 메소드
    public static Bitmap getImageFromURL(String imageURL){
        Bitmap imgBitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;

        try
        {
            URL url = new URL(imageURL);
            conn = (HttpURLConnection)url.openConnection();
            conn.connect();

            int nSize = conn.getContentLength();
            bis = new BufferedInputStream(conn.getInputStream(), nSize);
            imgBitmap = BitmapFactory.decodeStream(bis);
            imgBitmap = transform(imgBitmap);
        }
        catch (Exception e){
            e.printStackTrace();
        } finally{
            if(bis != null) {
                try {bis.close();} catch (IOException e) {}
            }
            if(conn != null ) {
                conn.disconnect();
            }
        }

        return imgBitmap;
    }

    // centerCrop
    public static Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
//                //Check if the x and y position of the touch is inside the bitmap
//                if( x > bitmapXPosition && x < bitmapXPosition + bitmapWidth && y > bitmapYPosition && y < bitmapYPosition + bitmapHeight )
//                {
//                    //Bitmap touched
//                }
                return true;
        }
        return false;
    }
}
