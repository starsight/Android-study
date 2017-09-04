package com.wenjiehe.android_study.restart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenjiehe.android_study.R;

/**
 * Created by Administrator on 2017/9/3.
 */

public class StretchableFloatingButton extends ViewGroup {

    private float width=0;
    private float height=0;
    private float center=0;
    private float x=1;//矩形右边
    private float y=20;//圆环宽度
    private float y_x;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final int SLIDE_DECREASE =1;
    private static final int SLIDE_INCREASE =2;

    private OnClickListener onClickListener;
    private StretchableFloatingButton stretchableFloatingButton;

    private String text="地铁/电影/景区/商铺……";
    private View child;
    private float tWidth;//文本宽度
    private float tHeight;
    private float tWidth_x;
    private float tX;

    private int attr_bacColor=Color.BLACK;
    private String atrr_textContent="地铁/电影/景区/商铺……";


    public StretchableFloatingButton(Context context) {
        this(context,null);
    }

    public StretchableFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        stretchableFloatingButton =this;
        y =20;

        TypedArray type =context.obtainStyledAttributes(attrs, R.styleable.StretchableFloatingButton);
        attr_bacColor = type.getColor(R.styleable.StretchableFloatingButton_bacColor,Color.YELLOW);
        atrr_textContent = type.getString(R.styleable.StretchableFloatingButton_textContent);

        TextView tv = new TextView(context);
        tv.setText(atrr_textContent);
        tv.setTextSize(16);
        addView(tv);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SLIDE_DECREASE:{
                    x-=30;
                    if(x>=center+30) {
                        y=y_x*x;
                        tX = tWidth_x*x;
                        handler.sendEmptyMessageDelayed(SLIDE_DECREASE, 1);
                    }
                    else{
                        x= center;
                        y=-1;
                        tX = center*2+5;
                        setEnabled(true);
                    }
                    break;
                }
                case SLIDE_INCREASE:{
                    x+=30;
                    if(x<=width-center-30) {
                        y=y_x*x;
                        tWidth = tWidth_x*x;
                        handler.sendEmptyMessageDelayed(SLIDE_INCREASE, 1);
                    }
                    else{
                        x=width-center;
                        y=20;
                        tX = tWidth+2*center+10;
                        setEnabled(true);
                    }
                    break;
                }
            }
            requestLayout();
            invalidate();
            //super.handleMessage(msg);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(width==0){
            width =MeasureSpec.getSize(widthMeasureSpec);
            height= MeasureSpec.getSize(heightMeasureSpec);

            center = height/2;
            x=width-center;
            y_x=y/x;

            child =getChildAt(0);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            tWidth=child.getMeasuredWidth();
            tHeight=child.getMeasuredHeight();

            tX=tWidth+2*center+10;
            tWidth_x = (tWidth+10)/(width-center*2);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        paint.setColor(attr_bacColor);
        canvas.drawCircle(center,center,center,paint);

        canvas.drawRect(center,0,x,height,paint);

        canvas.drawCircle(x,center,center,paint);

        //内环
        paint.setColor(Color.BLACK);
        canvas.drawCircle(center,center,center-y,paint);

        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        child.layout((int)(2*center+5),(int)(center-tHeight/2),(int)(tX),(int)(center+tHeight/2));
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(StretchableFloatingButton button);
    }

    private boolean canClick=true;
    private boolean increaseState= true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                canClick=judgeCanClick(event.getX(),event.getY());

                if(!canClick)
                    return super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                if(canClick&&isEnabled()&&onClickListener!=null)
                    onClickListener.onClick(stretchableFloatingButton);
                break;
        }

        return true;
    }

    private boolean judgeCanClick(float x, float y) {
        boolean canClick;
        if(increaseState){
            if(x<width&&y<height)
                canClick=true;
            else
                canClick=false;
        } else {
            if(x<2*center&&y<2*center)
                canClick=true;
            else
                canClick=false;
        }

        return canClick;
    }

    public void startScroll(){
        if(increaseState)
            startDecrease();
        else
            startIncrease();
    }

    private void startDecrease(){
        setEnabled(false);
        increaseState=false;
        handler.sendEmptyMessageDelayed(SLIDE_DECREASE,40);
    }

    private void startIncrease(){
        setEnabled(false);
        increaseState=true;
        handler.sendEmptyMessageDelayed(SLIDE_INCREASE,40);
    }
}
