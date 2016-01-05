package com.yp.lockscreen.bean;

import java.io.Serializable;

/**
 * 
 * @Description TODO 存储单词所在的遗忘曲线的阶段的bean
 * @author LumiaJohn
 * @date 2015-1-3 上午12:44:21
 */
public class ReciteInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
	 * 遗忘曲线的周期阶段
	 */
	private int curveStage = 0;
	/**
	 * 上次复习时的时间
	 */
	private long lastTime;
	
	/**
	 * 初始化遗忘曲线的周期阶段为0 ，上次复习时的时间为当前时间
	 */
	public ReciteInfoVO(){
		this.curveStage = 0;
		this.lastTime = System.currentTimeMillis();
	}
	/**
	 * @param curveStage 遗忘曲线的周期阶段
	 * @param lastTime 上次复习时的时间
	 */
	public ReciteInfoVO(int curveStage, long lastTime){
		this.curveStage = curveStage;
		this.lastTime = lastTime;
	}
	public int getCurveStage() {
		return curveStage;
	}
	public void setCurveStage(int curveStage) {
		this.curveStage = curveStage;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	
	
}
