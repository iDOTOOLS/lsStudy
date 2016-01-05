package com.yp.lockscreen.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class MarketUtils {
    public static void appRank(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (Exception e) {
            Toast.makeText(context, "Couldn't launch the market !",Toast.LENGTH_SHORT).show();
        }
    }
    
    public static void appRank(Context context,String pkg) {
        Uri uri = Uri.parse("market://details?id=" + pkg);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (Exception e) {
            Toast.makeText(context, "Couldn't launch the market !",Toast.LENGTH_SHORT).show();
        }
    }
    
    public static boolean jumpMarket(String marketPkg,String searchViewName,String searchPkg,Context context){
        try {
            Intent it = context.getPackageManager().getLaunchIntentForPackage(marketPkg);
            it.setComponent(new ComponentName(marketPkg, searchViewName));
            it.setData(Uri.parse("market://details?id=" + searchPkg));
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static Intent getMarket(String marketPkg,String searchViewName,String searchPkg,Context context){
        Intent it  = null;
        try {
            it = context.getPackageManager().getLaunchIntentForPackage(marketPkg);
            it.setComponent(new ComponentName(marketPkg, searchViewName));
            it.setData(Uri.parse("market://details?id=" + searchPkg));
        } catch (Exception e) {
            return null;
        }
        return it;
    }
}
