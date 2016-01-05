package com.yp.lockscreen.view;

import com.yp.lockscreen.R;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
	public static LruCache<String, Bitmap> myCache = new LruCache<String, Bitmap>(8 * 1024 * 1024);
	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomImageView(Context context) {
		super(context);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(myCache.get("R.drawable.picture") == null){
			myCache.put("R.drawable.picture", BitmapFactory.decodeResource(getResources(), R.drawable.picture));
		}
	}
	
	private RectF desF;
	private RectF getDesRectF(){
		if(desF == null)
			desF = new RectF(0, 0, getWidth(), getHeight());
		return desF;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap bitmap = myCache.get("R.drawable.picture");
//		canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawBitmap(bitmap, null, getDesRectF(), null);
	}
}
