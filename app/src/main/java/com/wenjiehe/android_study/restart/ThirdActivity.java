package com.wenjiehe.android_study.restart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wenjiehe.android_study.R;

public class ThirdActivity extends Activity implements OnTouchListener  {
	
	//��ָ���һ���ʱ����С�ٶ�
	private static final int XSPEED_MIN = 200;
	
	//��ָ���һ���ʱ����С����
	private static final int XDISTANCE_MIN = 150;
	
	//��¼��ָ����ʱ�ĺ����ꡣ
	private float xDown;
	
	//��¼��ָ�ƶ�ʱ�ĺ����ꡣ
	private float xMove;
	
	//���ڼ�����ָ�������ٶȡ�
	private VelocityTracker mVelocityTracker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll_third);
		ll.setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getRawX();
			//��ľ���
			int distanceX = (int) (xMove - xDown);
			//��ȡ˳ʱ�ٶ�
			int xSpeed = getScrollVelocity();
			//�������ľ�����������趨����С�����һ�����˲���ٶȴ��������趨���ٶ�ʱ�����ص���һ��activity
			if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
				finish();
				//�����л����������ұ߽��룬����˳�
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			}
			break;
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		default:
			break;
		}
		return true;
	}
	
	/**
	 * ����VelocityTracker���󣬲�������content����Ļ����¼����뵽VelocityTracker���С�
	 * 
	 * @param event
	 *        
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}
	
	/**
	 * ����VelocityTracker����
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}
	
	/**
	 * ��ȡ��ָ��content���滬�����ٶȡ�
	 * 
	 * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ��
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}
}
