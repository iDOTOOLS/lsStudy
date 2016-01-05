package com.yp.lockscreen.port;

public class NetConfig {

	public static final String BASE_ADDRESS = "http://wallpaper.tv163.com/service/searchRecommend.do";
	
	/**
	 * 值的样式：?categoryId=10
	 */
	public static String categoryId = "?categoryId=10";
	
	/**
	 * 值的样式：&pageSize=30
	 */
	public static String pageSize = "&pageSize=30";
	
	
	/**
	 * @param category 值的样式："?categoryId=10"
	 * @param page  值的样式："&pageSize=30"
	 */
	public static void setNetAddress(String category,String page){
		categoryId = category;
		pageSize = page;
	}
	
}
