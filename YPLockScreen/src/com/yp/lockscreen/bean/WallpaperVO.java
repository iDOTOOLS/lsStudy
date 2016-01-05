package com.yp.lockscreen.bean;

import java.io.Serializable;

public class WallpaperVO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public int id;
    public int categoryId;
    public int wallpaperId;
    public String wallpaperUrl;
    public String createDate;
    public boolean isDownload;
    public String fileName;
    
    public String getWallpaperUrl() {
        return wallpaperUrl.replaceAll(".jpg", "_290x290.jpg");
    }
   
    public String getWallpaperUrl_480(){
        return wallpaperUrl.replaceAll(".jpg", "_480x.jpg");
    }
    
    public String getWallpaperUrl_640(){
        return wallpaperUrl.replaceAll(".jpg", "_640x640.jpg");
    }
}
