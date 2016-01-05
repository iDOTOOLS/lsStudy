package com.yp.lockscreen.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchWallpaperVO implements Serializable{
    /**
     * http://wallpaper.tv163.com/service/searchRecommend.do?categoryId=10&pageSize=30
     */
    private static final long serialVersionUID = 1L;
    public int result;
    public ArrayList<WallpaperVO> data;
}
