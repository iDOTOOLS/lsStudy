package com.dianxinos.lockscreen_sdk;

import java.lang.reflect.Method;
import android.content.Context;
import android.net.Uri;

public class ClassManager {

    public Class<?> LockPattern;
    public Method LockPattern_isSecure;
    
    public boolean LockPattern_IsSecure(){
        try {
            if(LockPattern!=null){
                LockPattern_isSecure = LockPattern.getMethod("isSecure",String.class);
            }
            return (Boolean)LockPattern_isSecure.invoke(LockPattern);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return false;
    }
    
    public Class<?> getLockPattern(){
        return LockPattern;
    }
    
    public void LockPatternNewInstance(Context context){
        try {
            LockPattern = Class.forName("com.android.internal.widget.LockPatternUtils");
            if(LockPattern==null){
                System.out.println("LockPattern is null");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public Uri getTelephonySmSUri(){
        return Uri.parse("content://sms");
    }
    
    public Uri getTelephonyMmsUri(){
        return Uri.parse("content://mms");
    }
    
    public Uri getTelePhonyMmsSmsUri(){
        return Uri.parse("content://mms-sms/");
    }
    
    public Uri getCallLogCallUri(){
        return Uri.parse("content://call_log/calls");
    }
}
