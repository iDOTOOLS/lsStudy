package com.yp.enstudy.bean;

import java.io.Serializable;

public class Word implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public int _id;
    public String word;
    public String interpretation;
    public String ps;
    public int random_id;
    public int reverse_id;
    /**0 正常   1 生词    2已掌握*/
    public int remember;
    public String cn;
    public String en;
    
    /**复习阶段 总共八个阶段*/
    public int stage;
    /**最后复习时间*/
    public long lastTime;
}
