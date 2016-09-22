package com.example.han.compass.request;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.han.compass.R;

/**
 * Created by home on 2016-09-19.
 */
public class CircleDrawing extends View implements Runnable{

    int cur_angle = 0;
    Thread animator = null;
    Context context;

    private Paint pnt;
    private final int width = 10;
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
        super(context, attrs, defStyle);;
        this.context = context;
    }

    void start(){
        animator = new Thread(this);
        animator.start();

    }

    @Override
    public void run() {
        while(true){
            cur_angle += (360/16);

            postInvalidate();

            try {
                Thread.sleep(900, 0);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        int radius = 100;
        int mini_radius = 10;


        radius = deviceWidth/4;

        pnt = getPrint(R.color.requestCircleWait);
        canvas.drawCircle(deviceWidth/2, deviceHeight/5*1, radius, pnt);

        mini_radius = 15;

        for (double angle = 0; angle < 365; angle += ((double)365/16)){
            int x_base = deviceWidth/2;
            int y_base = deviceHeight/5*1;
            int diff = mini_radius;
            int x, y;
            double rad;

            rad = angle*3.14/180;

            x = (int) ((radius + diff) * Math.sin(rad));
            y = (int) ((radius + diff) * Math.cos(rad));

            canvas.drawCircle(x_base + x, y_base + y, mini_radius, pnt);

        }
    }

    public Paint getPrint(int id){
        Paint paint;

        paint = new Paint();
        paint.setAntiAlias(true);             //그림이 화면에 표현될 때 더욱 자연스러워 보이게 한다. 왜냐하면 그려지는 그림 주변에 옅은 색상을 더 뿌려서 화면에 표현되게 하기 때문이다.
        paint.setColor(context.getResources().getColor(id));   //
        paint.setStrokeWidth(width);
        paint.setStyle(Paint.Style.STROKE);   // 인수에 따라 그려지는 그림이 달라진다.

        return paint;
    }

}
