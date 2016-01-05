package com.yp.lockscreen.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ixintui.pushsdk.SdkConstants;
import com.yp.lockscreen.utils.LogHelper;
//此类基本上没用,因为没用到推送功能
public class IxinPushReceiver extends BroadcastReceiver{
	private static final String TAG = "PushReceiver";

	/*** 推送消息接收器 ***/
	@Override
	public void onReceive(Context context, Intent intent) 
	{
	    String action = intent.getAction();
		// 透传消息
		if (action.equals(SdkConstants.MESSAGE_ACTION)) 
	    {
			String msg = intent.getStringExtra(SdkConstants.MESSAGE);
			String extra = intent.getStringExtra(SdkConstants.ADDITION);
			LogHelper.d(TAG, "message received, msg is: " + msg + "extra: " + extra);
			// 处理透传内容
	        // ...
		} 
	    // SDK API的异步返回结果
		else if (action.equals(SdkConstants.RESULT_ACTION)) 
	    {
	         // API 名称
		     String cmd = intent.getStringExtra(SdkConstants.COMMAND);
	         // 返回值，0为成功，否则失败
		     int code = intent.getIntExtra(SdkConstants.CODE, 0);
		     if (code != 0) 
	         {
	             // 错误信息
				 String error = intent.getStringExtra(SdkConstants.ERROR);
				 LogHelper.d(TAG, "command is: " + cmd + " result error: " + error);
			} 
	        else 
	        {
	        	LogHelper.d(TAG, "command is: " + cmd + "result OK");
			}
			// 附加结果，比如添加成功的tag， 比如推送是否暂停等
			String extra = intent.getStringExtra(SdkConstants.ADDITION);
			if (extra != null) 
	        {
				LogHelper.d(TAG, "result extra: " + extra);
			}		
		}
	    // 通知点击事件
		else if (action.equals(SdkConstants.NOTIFICATION_CLICK_ACTION)) 
	    {
			String msg = intent.getStringExtra(SdkConstants.MESSAGE);
			LogHelper.d(TAG, "notification click received, msg is: " + msg);
		}            
	}
}

