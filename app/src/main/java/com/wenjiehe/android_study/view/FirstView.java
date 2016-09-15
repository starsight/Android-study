package com.wenjiehe.android_study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.wenjiehe.android_study.R;

/**
 * Created by yiyuan on 2016/9/14.
 */
public class FirstView extends View {
    private Paint mypaint;

    public FirstView(Context context) {
        super(context);
        init();
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init (){
        mypaint = new Paint();
        mypaint.setAntiAlias(true);
        mypaint.setColor(getResources().getColor(android.R.color.darker_gray));
        mypaint.setStyle(Paint.Style.STROKE);
        mypaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
        Path path = new Path();
        path.moveTo(200,100);

        path.lineTo(100,210);
        path.lineTo(300,270);
        //path.rCubicTo(200,100,250,200,500,350);
        canvas.drawPath(path,mypaint);
    }
}
