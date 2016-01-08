
package com.dotools.views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class StatusBarAboveView extends ViewGroup {

    public StatusBarAboveView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }
}
