
package com.dotools.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.ViewConfiguration;
import android.view.Window;

public class DevicesUtils_vk {
    

    private static Boolean hasVertualKey = null;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasVertualKey(){
        if(hasVertualKey == null)
            hasVertualKey = !ViewConfiguration.get(Utilities.getApplicationContext()).hasPermanentMenuKey();
        return hasVertualKey;
    }
    private static Integer statusHeight = null;

    public static int getStatusHeight(Activity activity) {
        if (statusHeight == null && hasVertualKey()) {
            Rect localRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
            statusHeight = localRect.top;
            if (0 == statusHeight) {
                Class<?> localClass;
                try {
                    localClass = Class.forName("com.android.internal.R$dimen");
                    Object localObject = localClass.newInstance();
                    int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                    statusHeight = activity.getResources().getDimensionPixelSize(i5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return statusHeight;
    }
}
