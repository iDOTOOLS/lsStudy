package com.yp.enstudy.bean;

import java.io.Serializable;

public class TableName  implements Serializable,Comparable<TableName>{
    private static final long serialVersionUID = 1L;
    
    public int _id;
    /**
     * ciku_03
     */
    public String name;
    /**
     * 四级核心
     */
    public String ciku_name;
    /**
     * 单词个数
     */
    public int word_count;
    /**
     * 
     */
    public int remembered;
    /**
     * 音频url
     */
    public String audio_url;
    /**
     * 词库url
     * 
     */
    public String url;
    /**
     * 音频大小
     */
    public int audio_size;
    /**
     * 词库大小 kb
     */
    public int ciku_size;
    public String extrs1;
    public String extrs2;
    public int flag1;
    /**
     * 分类id
     */
    public int type_id;
    /**
     * 分类 
     */
    public String type;
    public int flag2;
    /**
     * 每类中的排序
     */
    public int sequence;
    /**
     * 是否下载，不在数据库中
     */
    public boolean isDownLoad;
    
	@Override
	public int compareTo(TableName another) {
		if (this.type_id == another.type_id) {
			return this.sequence - another.sequence;
		}else {
			return this.type_id - another.type_id;
		}
	}
}
