
package com.dotools.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.WindowManager;

import com.dotools.views.StatusBarAboveView;

public class DTLockScreenUtils {
    public static final String ACTION_SHORTCUT_NOQUITSPLASH_STRING = "com.dotools.exitsplash_hide";
    public static StatusBarAboveView mStatusBarAboveView;
    private static WindowManager mWindowManager;
    private static WindowManager.LayoutParams mLocalLayoutParams;

    public static int mStatusBarHeight;
    private static boolean hasInit;

    public static void addStatusBarAboveView(Context ct) {
        if (mWindowManager == null) {
            mWindowManager = ((WindowManager) ct.getSystemService(Context.WINDOW_SERVICE));
            mLocalLayoutParams = new WindowManager.LayoutParams();
            mLocalLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            mLocalLayoutParams.gravity = Gravity.TOP;
            mLocalLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    // this is to enable the notification to recieve touch
                    // events
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    // Draws over status bar
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            mLocalLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mLocalLayoutParams.format = PixelFormat.TRANSLUCENT;
        }
        if (mStatusBarHeight == 0) {
            mLocalLayoutParams.height = UiUtils.dipToPx(35);
        } else {
            mLocalLayoutParams.height = mStatusBarHeight;
        }
        if (mStatusBarAboveView == null) {
            mStatusBarAboveView = new StatusBarAboveView(ct);
        }
        if (mStatusBarAboveView.getParent() == null)
            mWindowManager.addView(mStatusBarAboveView, mLocalLayoutParams);
    }

    public static void removeStatusBarAboveView() {
        if (mStatusBarAboveView != null)
            mWindowManager.removeView(mStatusBarAboveView);
        mStatusBarAboveView = null;
    }

    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public static void disableKeyguard(Activity acv) {
        acv.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        acv.getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }
}
