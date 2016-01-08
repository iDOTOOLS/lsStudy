package com.yp.lockscreen.port;

import java.util.ArrayList;

import android.content.Context;

import com.yp.enstudy.ConfigManager;
import com.yp.enstudy.DownloadManager;
import com.yp.enstudy.WordData;
import com.yp.enstudy.bean.TableName;
import com.yp.enstudy.bean.Word;

public class Global {

	public static int screen_Height;
	public static int screen_width;
	

	public static int review_count = 10;
	public static int review_orderType = 0;
	
	public static String language;

	public static String AUDIO_PATH;

	public static String DOWNLOAD_PATH;

	public static String TEMP_PATH;

	public static String BASE_PATH;

	public static String IMAGE_PATH;

	public static ArrayList<Word> gUnknownWords;

	/**已掌握*/
	public static ArrayList<Word> gGraspWords;

	/**复习*/
	public static ArrayList<Word> gReViewWords;

	public static WordData gWordData;

	public static DownloadManager gDownLoad;

	public static Context gContext;
	
	public static TableName gCurTableName;
	
	public static String NET_ADDRESS = NetConfig.BASE_ADDRESS + NetConfig.categoryId + NetConfig.pageSize;

	public static ArrayList<Word> updataNoRememberList() {

		gUnknownWords = Global.gWordData.getNoRemember();
		return gUnknownWords;

	}

	public static ArrayList<Word> updataRemember() {

		gGraspWords = Global.gWordData.getRemember();
		return gGraspWords;

	}

	public static ArrayList<Word> updataReviewlsit() {
		gReViewWords = Global.gWordData.getReviewList(ConfigManager.getWordEveryDayNum(gContext),ConfigManager.getOrderType(gContext));
		return gReViewWords;

	}
	
	/**
	 * 更新当前TableName
	 * @return
	 */
	public static TableName updataCurTableName(){
		gCurTableName = Global.gWordData.getCurWordLibraryData();
		return gCurTableName;
		
	}
}
