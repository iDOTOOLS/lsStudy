package com.dotools.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class UiUtils {
    private static Integer mRealScreenHeight = 0;
    @SuppressLint("NewApi")
    public static int getScreenHeightPixelsTotal() {

         if (mRealScreenHeight == 0) {
             if (DevicesUtils_vk.hasVertualKey()) {
                 Display display = ((WindowManager) Utilities.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                         .getDefaultDisplay();
                 int orientation = display.getRotation();
                 boolean landscape = orientation == Surface.ROTATION_270 || orientation == Surface.ROTATION_90;
                 Point size = new Point();
                 if (Build.VERSION.SDK_INT >= 17) {
                     display.getRealSize(size);
                     mRealScreenHeight = landscape ? size.x : size.y;
                 } else {
                     try {
                         Method getRawH = landscape ? Display.class.getMethod("getRawWidth") : Display.class.getMethod("getRawHeight");
                         mRealScreenHeight = (Integer) getRawH.invoke(display);
                     } catch (Exception e) {
                         display.getSize(size);
                         mRealScreenHeight = landscape ? size.x : size.y;
                     }
                 }
             }
             if(mRealScreenHeight == 0) {
                 mRealScreenHeight = getScreenHeightPixels();
             }
         }
         return mRealScreenHeight;
    }
    
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;
    private static synchronized void initScreenHeightWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) Utilities.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        if(sScreenHeight < sScreenWidth) {
            int t = sScreenHeight;
            sScreenHeight = sScreenWidth;
            sScreenWidth = t;
        }
    }
    
    public static int getScreenHeightPixels() {
        if (sScreenHeight == 0) {
            initScreenHeightWidth();
        }
        return sScreenHeight;
    }
    
    public static int dipToPx(int dip) {
        return (int) (dip * getDensity() + 0.5f);
    }
    
    private static float sDensity = 0f;
    public static float getDensity() {
        if (sDensity == 0f) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) Utilities.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return sDensity;
    }
    
}
