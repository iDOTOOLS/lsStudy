package com.yp.lockscreen.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yp.lockscreen.bean.ReciteInfoVO;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * 
 * @Description 艾宾浩斯遗忘曲线的单例
 * @author LumiaJohn
 * @date 2015-1-3 上午12:37:16
 */
public class Ebbinghaus {
	private Ebbinghaus() {
	}

	private final static Ebbinghaus instance = new Ebbinghaus();

	/**
	 * 返回艾宾浩斯的单例
	 * 
	 * @return 艾宾浩斯对象
	 */
	public static Ebbinghaus getInstance() {
		return instance;
	}

	// 艾宾浩斯遗忘曲线的各个周期点
	private static final int[] CURVES = { 
			5, // 5分钟
			30, // 30分钟
			12 * 60, // 12小时
			1 * 24 * 60, // 1天
			2 * 24 * 60, // 2天
			4 * 24 * 60, // 4天
			7 * 24 * 60, // 7天
			15 * 24 * 60 // 15天
	};
	private Map<Integer, ReciteInfoVO> idInfoPair = new HashMap<Integer, ReciteInfoVO>();

	/**
	 * 用来内部测试使用 展示计划的相关信息
	 */
	public void showPlan() {
		if (hasPlan()) {
			for (Map.Entry<Integer, ReciteInfoVO> entry : idInfoPair.entrySet()) {
				int key = entry.getKey();
				ReciteInfoVO value = entry.getValue();
				Log.i("Ebbinghaus", "单词id:" + key + " --------- " + "计划阶段:"
						+ value.getCurveStage() + " --------- " + "上次复习的时间:"
						+ value.getLastTime());
			}
		} else {
			Log.i("Ebbinghaus", "还没有计划");
		}
	}

	/**
	 * 判断是否有单词进入到计划之中
	 * 
	 * @return true有单词进入到计划
	 * false 计划为空
	 *  注意:此方法会清除掉过期的单词的对应id
	 */
	public boolean hasPlan() {
		if (idInfoPair.isEmpty()) {
			return false;
		} else {
			removeAllOverdueId();
			if (idInfoPair.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 清除所有计划
	 */
	public void clearPlan(){
		if(idInfoPair.isEmpty()){
			return;
		}
		idInfoPair.clear();
	}
	/**
	 * 根据传入的id号查找到与之对应的内部数据，并更新其中的复习阶段和复习时间值
	 * 
	 * @param id
	 *            词语对应的id号
	 */
	public void updatePlanByIds(int... id) {
		if (id == null || id.length <= 0) {
			throw new IllegalArgumentException("词语的id为空");
		}
		for (int i = 0; i < id.length; i++) {
			ReciteInfoVO ReciteInfoVO = idInfoPair.get(id[i]);
			if (ReciteInfoVO == null) {
				throw new IllegalArgumentException("艾宾浩斯的map中没有以此为id的key");
			}
			ReciteInfoVO.setLastTime(System.currentTimeMillis());
			ReciteInfoVO.setCurveStage(ReciteInfoVO.getCurveStage() + 1);
		}
	}
	
	/**
	 * 查询单词对应的id是否已经完成任务
	 * @param id
	 * @return
	 * true 单词已经完成任务
	 * false 单词没有完成任务
	 */
	public boolean isPlanOverdue(int id){
		if(isWordInPlan(id)){
			int curveStage = idInfoPair.get(id).getCurveStage();
			return curveStage >= CURVES.length;
		}
		throw new IllegalArgumentException("id 不在计划中");
	}

	/**
	 * 移除计划中已经过期的单词id  
	 * 注:需要手动执行移除
	 */
	public void removeAllOverdueId() {
		List<Integer> toDeleteIdList = new ArrayList<Integer>();
		for (Map.Entry<Integer, ReciteInfoVO> entry : idInfoPair.entrySet()) {
			int id = entry.getKey();
			ReciteInfoVO info = entry.getValue();
			if (info.getCurveStage() >= CURVES.length) {
				toDeleteIdList.add(id);
			}
		}
		for (Integer id : toDeleteIdList) {
			idInfoPair.remove(id);
		}
	}
	/**
	 * 删除计划中单词对应的id的计划
	 * @param id
	 */
	public void removePlanById(int id){
		if(isWordInPlan(id)){
			idInfoPair.remove(id);
		}
	}

	/**
	 * 将单词对应的id加入内部维护的map中
	 * 
	 * @param id
	 *            单词对应的id
	 * 
	 */
	public void addWord2EbbinghausPlan(int... id) {
		if (id == null || id.length <= 0) {
			throw new IllegalArgumentException("词语的id为空");
		}
		for (int i = 0; i < id.length; i++) {
			if(isWordInPlan(id[i])){
				continue;
			}
			ReciteInfoVO info = new ReciteInfoVO();
			idInfoPair.put(id[i], info);
		}
	}
	
	public Map<Integer, ReciteInfoVO> getIdInfoPair() {
        return idInfoPair;
    }

    public void setIdInfoPair(Map<Integer, ReciteInfoVO> idInfoPair) {
        this.idInfoPair = idInfoPair;
    }

    /**
	 * 查询id对应的单词是否已经加入到计划之中
	 * @param id
	 * @return true 单词已经加入到计划之中
	 * 			false 单词没有加入到计划之中
	 */
	public boolean isWordInPlan(int id) {
		if (idInfoPair.get(id) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据单词的id判断是否该单词需要复习
	 * 
	 * @param id
	 * @return true需要复习
	 * false不需要复习
	 */
	public boolean needReview(int id) {
		if (idInfoPair.size() <= 0) {
			throw new IllegalArgumentException("艾宾浩斯的map中没有以此为id的key");
		}
		ReciteInfoVO record = idInfoPair.get(id);
		if (record == null) {
			throw new IllegalArgumentException("艾宾浩斯的map中没有以此为id的key");
		}
		if (record.getCurveStage() >= CURVES.length) {
			return false;
		}
		long currentTime = System.currentTimeMillis();
		int timeDiff = (int) ((currentTime - record.getLastTime()) / (1000 * 60));

		if (timeDiff > CURVES[record.getCurveStage()]) {
			return true;
		} else {
			return false;
		}
	}
}
