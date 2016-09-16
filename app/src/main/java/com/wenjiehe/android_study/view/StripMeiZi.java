package com.wenjiehe.android_study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wenjiehe.android_study.MyUtils;
import com.wenjiehe.android_study.R;

/**
 * Created by yiyuan on 2016/9/16.
 */
public class StripMeiZi extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Canvas mCanvas;
    private Bitmap mBeforeBitmap;
    private Bitmap mBackBitmap;
    private int mLastX,mLastY;
    private int screenW, screenH; //屏幕宽高
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);


    public StripMeiZi(Context context) {
        this(context, null);
    }

    public StripMeiZi(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenW = MyUtils.getScreenW(context);
        screenH = MyUtils.getScreenH(context);
        init();
    }


    public StripMeiZi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //背后图片，这里让它全屏
        mBackBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.meizi_back);
        mBackBitmap = Bitmap.createScaledBitmap(mBackBitmap, screenW, screenH, false);
        //前面的图片，并绘制到Canvas上
        mBeforeBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBeforeBitmap);
        mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.meizi_before), null, new RectF(0, 0, screenW, screenH), null);
        //画笔相关的设置
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        mPaint.setStrokeWidth(80);    // 设置画笔宽
    }

    private void drawPath() {
        mPaint.setXfermode(mXfermode);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackBitmap, 0, 0, null);
        canvas.drawBitmap(mBeforeBitmap, 0, 0, null);
        drawPath();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);

                if (dx > 3 || dy > 3)
                    mPath.lineTo(x, y);

                mLastX = x;
                mLastY = y;
                break;
        }
        invalidate();
        return true;
    }
}
