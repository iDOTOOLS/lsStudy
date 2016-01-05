package com.yp.lockscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HorizontalScrollGestureLy extends LinearLayout{

	private GestureDetector mGestureDetector;
	
	private OnHorizontalScrollListener mHorizontalScrollListener;
	
	private boolean bTouchIntercept = false;
	
	private OnInterceptListener mListener;
	
	
	private boolean isLockX;
	
	private int scrollFlag = 0;
	
	public HorizontalScrollGestureLy(Context context) {
		this(context,null);
	}

	public HorizontalScrollGestureLy(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(new HorizontalScrollGestureListener());
	}
	

	
	public void setOnInterceptListenr(OnInterceptListener listener){
		
		mListener = listener;
	}
	
	public void setonHorizontalScrollListener(OnHorizontalScrollListener l){
		this.mHorizontalScrollListener = l;
		
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return bTouchIntercept;
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		bTouchIntercept = mGestureDetector.onTouchEvent(ev);
		
		if(MotionEvent.ACTION_UP == ev.getAction()){
			if(mHorizontalScrollListener != null){
				mHorizontalScrollListener.doOnRelease();
			}
		}
		return super.dispatchTouchEvent(ev);
		
	}
	
	private class HorizontalScrollGestureListener extends SimpleOnGestureListener{
		int totalX = 0;
		int totalY = 0;
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			int flag = mListener.isIntercept();
			switch (scrollFlag) {
			case 0:
				totalX += Math.abs(distanceX);
				totalY += Math.abs(distanceY);
				break;
			case 1:
				isLockX = true;
				break;
			case 2:
				isLockX = false;
				break;
			}
			
//			System.out.println("=======>>>>>> totalX"+totalX+",--totalY"+totalY);
			
//			System.out.println("distanceX:"+distanceX+",distanceY:" + distanceY);
			
			if (totalY > 1) {
				scrollFlag = 1;
			}
			
			if (totalX > 1) {
				scrollFlag = 2;
			}
			
//			System.out.println("isLockX:"+isLockX);
			
			if ((flag == 0 || (flag == 1 && distanceX>0)) && !isLockX) {
				if(mHorizontalScrollListener != null) {
					mHorizontalScrollListener.doOnScroll(e1, e2, distanceX, distanceY);
				}
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean onDown(MotionEvent e) {
			isLockX = false;
			totalX = 0;
			totalY = 0;
			scrollFlag = 0;
			return super.onDown(e);
		}
	}

	public interface OnHorizontalScrollListener {
		
		public void doOnScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
		public void doOnRelease();

	}
	
	public interface OnInterceptListener{
		
		public int isIntercept();
		
	}
	
}
