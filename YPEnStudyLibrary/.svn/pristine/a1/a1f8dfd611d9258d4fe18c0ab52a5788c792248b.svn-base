package com.yp.enstudy.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import android.text.TextUtils;

public class TimeUtil {

	public static String converTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 30 * 24 * 60 * 60) {
			timeStr = timeGap / (30 * 24 * 60 * 60) + "个月前";
		} else if (timeGap >= 7 * 24 * 60 * 60) {
			timeStr = timeGap / (7 * 24 * 60 * 60) + "周前";
		} else if (timeGap > 24 * 60 * 60 && timeGap < 7 * 24 * 60 * 60) {// 1天以上
			long day = Long.parseLong(new SimpleDateFormat("dd").format(System
					.currentTimeMillis()))
					- Long.parseLong(new SimpleDateFormat("dd")
							.format(timestamp * 1000));
			if (day <= 0) {
				Date d1 = new Date(System.currentTimeMillis());
				Date d2 = new Date(timestamp * 1000);
				SimpleDateFormat s1 = new SimpleDateFormat("MM-dd");
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				s1.format(d1);
				s1.format(d2);
				Date date = new Date(timestamp * 1000);
				if (d1.compareTo(d2) >= 1) {
					timeStr = timeGap / (24 * 60 * 60) + 1 + "天前"
							+ sdf.format(date);
				} else {
					timeStr = sdf.format(date);
				}
			} else if (day == 1) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date(timestamp * 1000);
				timeStr = "昨天 " + sdf.format(date);
			} else {
				timeStr = timeGap / (24 * 60 * 60) + "天前";
			}
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			long day = Long.parseLong(new SimpleDateFormat("dd").format(System
					.currentTimeMillis()))
					- Long.parseLong(new SimpleDateFormat("dd")
							.format(timestamp * 1000));
			if (day <= 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date(timestamp * 1000);
				timeStr = sdf.format(date);
			} else if (day == 1) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Date date = new Date(timestamp * 1000);
				timeStr = "昨天 " + sdf.format(date);
			} else {
				timeStr = timeGap / (24 * 60 * 60) + "天前";
			}
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else if (timeGap < 59 && timeGap > 10) {// 10秒钟-59秒钟
			timeStr = timeGap + "秒前";
		} else if (timeGap < 10) {
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/**
	 * 通过时间戳获取天数
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayNumber(int time) {
		return (time / (24 * 60 * 60)) - 1;
	}

	public static long getSubDays(long old, long now) {

		long nowDays = now % (60 * 60 * 24) == 0 ? (now / (60 * 60 * 24))
				: (now / (60 * 60 * 24) + 1);
		long oldDays = old % (60 * 60 * 24) == 0 ? (old / (60 * 60 * 24))
				: (old / (60 * 60 * 24) + 1);
		long avg = nowDays - oldDays + 1;

		return avg;
	}

	/**
	 * 两个日期相差的天数
	 * 
	 * @param nextDate
	 * @param preDate
	 * @return
	 */
	public static int getDayNumber(Date nextDate, Date preDate) {
		return (int) ((nextDate.getTime() - preDate.getTime()) / (1000l * 60l * 60l * 24l));
	}

	public static String getDate(String sTime) {
		long timeLong = Long.parseLong(sTime);
		Date date = new Date(timeLong * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

		String time[] = sdf.format(date).split("/");
		String month = time[0];
		String day = time[1];

		return StringUtil.reMoveLeft(month) + "/" + StringUtil.reMoveLeft(day);
	}

	// public static String converTime(long timestamp) {
	// if (timestamp == 0)
	// return "";
	// String sTime = "";
	// String[] array = {"年前", "月前", "天前", "小时前", "分钟前", "秒前"};
	// String pattern = "HH:mm";
	// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	// Date oldDate = new Date(timestamp * 1000);
	// // try {
	// // String news = simpleDateFormat.format(oldDate);
	// // oldDate = simpleDateFormat.parse(timestamp + "");
	// // long daya = (new Date(System.currentTimeMillis()).getTime() -
	// // oldDate.getTime()) / (24 * 60 * 60 * 1000);
	//
	// // } catch (ParseException e) {
	// // e.printStackTrace();
	// // }
	// // oldDate = simpleDateFormat.parse(String.valueOf(timestamp));
	// long nowTime = new Date().getTime();
	// long oldTime = oldDate.getTime();
	// long gmillion = (nowTime - oldTime) / 1000L;
	//
	// long year = Long.parseLong(new
	// SimpleDateFormat("yyyy").format(System.currentTimeMillis())) -
	// Long.parseLong(new SimpleDateFormat("yyyy").format(timestamp * 1000));
	// // long month = Long.parseLong(new
	// // SimpleDateFormat("MM").format(System.currentTimeMillis())) -
	// // Long.parseLong(new SimpleDateFormat("MM").format(timestamp * 1000));
	//
	// long day = Long.parseLong(new
	// SimpleDateFormat("dd").format(System.currentTimeMillis())) -
	// Long.parseLong(new SimpleDateFormat("dd").format(timestamp * 1000));
	// // long hour = Long.parseLong(new
	// // SimpleDateFormat("HH").format(System.currentTimeMillis())) -
	// // Long.parseLong(new SimpleDateFormat("HH").format(timestamp * 1000));
	// // long minute = Long.parseLong(new
	// // SimpleDateFormat("mm").format(System.currentTimeMillis())) -
	// // Long.parseLong(new SimpleDateFormat("mm").format(timestamp * 1000));
	// // long second = Long.parseLong(new
	// // SimpleDateFormat("ss").format(System.currentTimeMillis())) -
	// // Long.parseLong(new SimpleDateFormat("ss").format(timestamp * 1000));
	// // long hour = gmillion % (24 * 3600) / 3600;
	// // long minute = gmillion % 3600 / 60;
	// // long second = gmillion % 60;
	//
	// // long day = gmillion / (24 * 3600);
	// long hour = gmillion % (24 * 3600) / 3600;
	// long minute = gmillion % 3600 / 60;
	// long second = gmillion % 60;
	//
	// if (day == 0 && year <= 0) {
	// if (hour > 0) {
	// // sTime = hour + array[3];
	// // System.out.println("今天，小时大于0，显示原时间");
	// sTime = simpleDateFormat.format(timestamp * 1000);
	// } else if (minute > 0) {
	// sTime = minute + array[4];
	// } else if (second >= 0 && second <= 10) {
	// sTime = "刚刚";
	// } else {
	// sTime = second + array[5];
	// }
	// } else if (day == 1 && year <= 0) {
	// // System.out.println("昨天，小时大于0，显示原时间");
	// sTime = "昨天" + simpleDateFormat.format(timestamp * 1000);
	// } else if (day > 1 && day < 7 && year <= 0) {
	// sTime = day + array[2];
	// } else if (day >= 7 && year <= 0) {
	// long cMonth = day / 7;
	// if (cMonth <= 4) {
	// sTime = cMonth + "周前";
	// } else if (cMonth > 48) {
	// sTime = cMonth / 48 + "年前";
	// }
	// } else {
	// day = gmillion / (24 * 3600) + 1;
	// long cMonth = day / 7;
	// if (cMonth <= 4) {
	// sTime = cMonth + "周前";
	// } else if (cMonth > 48) {
	// sTime = cMonth / 48 + "年前";
	// }
	// // sTime = "";
	// // System.out.println("不应该打印");
	// // long cYear = day / 365;
	// // long cMonth = day / 30;
	// // sTime = year > 0 ? year + array[0] : month > 0 ? month + array[1]
	// // : day + array[2];
	// }
	// return sTime;
	// }

	public static String NearFriendPageConverTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = "刚刚";
		if (timeGap > 30 * 24 * 60 * 60) {
			timeStr = timeGap / (30 * 24 * 60 * 60) + "月前";
		} else if (timeGap > 7 * 24 * 60 * 60) {
			timeStr = timeGap / (7 * 24 * 60 * 60) + "周前";
		} else if (timeGap > 24 * 60 * 60) {// 1天以上
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else if (timeGap < 59 && timeGap > 10) {// 10秒钟-59秒钟
			timeStr = timeGap + "秒前";
		} else if (timeGap < 10) {
			timeStr = "刚刚";
		}
		return timeStr;
	}

	public static String getStandardTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		Date date = new Date(timestamp * 1000);
		sdf.format(date);
		return sdf.format(date);
	}

	public static String getHHmm(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = new Date(timestamp * 1000);
		sdf.format(date);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得星期
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getWeekTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 EEEE");
		Date date = new Date(timestamp * 1000);
		String week = sdf.format(date);
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 通过时间戳获得月日
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getDateTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得年月日
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getYearTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得年
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getYearByTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得月
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getMonthTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得日
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getDayTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得星期
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getWeekTimeByList(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 通过时间戳获得年月日及时间 格式：*年*月*日，**：**
	 * 
	 * @param date
	 * @return
	 */
	public static String getYMDHM(long timestamap) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date(timestamap * 1000);
		String year = sdf.format(date);
		sdf = new SimpleDateFormat("MM");
		date = new Date(timestamap * 1000);
		String month = sdf.format(date);
		sdf = new SimpleDateFormat("dd");
		date = new Date(timestamap * 1000);
		String day = sdf.format(date);
		sdf = new SimpleDateFormat("HH:mm");
		date = new Date(timestamap * 1000);
		String time = sdf.format(date);

		String times = year + "年" + month + "月" + day + "日" + "," + time;

		return times;
	}

	public static String getyyyyMMddHHmmss(Date date) {
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
			return sdf.format(date);
		}
		return "";
	}

	public static String getyyyyMMddHHmmss2(Timestamp date) {
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
			return sdf.format(date);
		}
		return "";
	}

	/** 是否是昨天 */
	public static boolean isYestoday(long time) {
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		String today = getyyyyMMdd(getDateByTime(time));
		return flag;
	}

	public static String getyyyyMMdd(Date date) {
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
			return sdf.format(date);
		}
		return "";
	}

	/** 时间戳转DATE */
	public static Date getDateByTime(long unixDate) {
		SimpleDateFormat fm1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long unixLong = 0;
		String date = "";
		try {
			unixLong = unixDate * 1000;
		} catch (Exception ex) {
			System.out.println("String转换Long错误，请确认数据可以转换！");
		}
		try {
			date = fm2.format(unixLong);
			// date = fm2.format(new Date(date));
		} catch (Exception ex) {
			System.out.println("String转换Date错误，请确认数据可以转换！");
		}
		return new Date(date);
	}

	public static String getyyyyMMddHHmmssSSS(Date date) {
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
			return sdf.format(date);
		}
		return "";
	}

	public static String getyyyyMMddHHmmssSSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
		return sdf.format(getChinaDate());
	}

	public static Date getChinaDate() {
		// 用中国时区、中国本地语言环境创建时间
		Calendar cal = Calendar.getInstance(
				TimeZone.getTimeZone("Asia/Hong_Kong"), Locale.CHINESE);
		return cal.getTime();
	}

	public static Date getChinaDate(int year, int month, int date, int hour,
			int minute, int second, int millisecond) {
		// 用中国时区、中国本地语言环境创建时间
		Calendar cal = Calendar.getInstance(
				TimeZone.getTimeZone("Asia/Hong_Kong"), Locale.CHINA);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal.getTime();
	}

	/** 时间戳转日期 */
	public static String formattertime(String now) {
		Long timestamp = Long.parseLong(now) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	/** 验证两个时间戳是否是同一天 */
	public static boolean checkSameDay(long time1, long time2) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (sf.format(new Date(time1 * 1000)).equals(
				sf.format(new Date(time2 * 1000)))) {
			return true;
		} else {
			return false;
		}
	}

	/** 验证两个时间戳是否是同一年 */
	public static boolean checkSameYear(long oldtime, long newtime) {
		if ((Math.abs(Integer.valueOf(getYearByTime(newtime))
				- Integer.valueOf(getYearByTime(oldtime)))) > 0) {
			return false;
		} else {
			return true;
		}
	}

	/** 验证两个时间戳是否是同一月 */
	public static boolean checkSameMonth(long oldtime, long newtime) {
		if ((Math.abs(Integer.valueOf(getMonthTime(newtime))
				- Integer.valueOf(getMonthTime(oldtime)))) > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 日期转时间戳 格式:yyyy::MM:dd HH:mm:ss
	 * */
	public static long getTimestamp(String date) {
		long l = 0;
		if (!TextUtils.isEmpty(date)) {
			try {
				Date d1 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
						.parse(date);
				l = d1.getTime() / 1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return l;

	}

	/**
	 * 计算星座
	 * 
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getConstellation(Integer month, Integer day) {
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		Integer[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		Integer num = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(num, num + 2);
	}

	/**
	 * 取得时间戳 精确到毫秒
	 * 
	 * @return time
	 */
	public static long getdatetime() {
		Date date = new Date();
		long time = date.getTime();
		return time;
	}

	/**
	 * 获得唯一标识UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/***
	 * 当天24点时间
	 * 
	 * @return
	 */
	public static int getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/***
	 * 当天0点时间
	 * 
	 * @return
	 */
	public static int getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 获取下一天0点时间
	 * 
	 * @return
	 */
	public static long getNextDate(long time) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String temp = df.format(new Date(time * 1000));
		long res = date2long(temp, "yyyy/MM/dd");
		res = res / 1000;
		res += (24 * 60 * 60);
		return res;
	}

	public static long date2long(String datestr, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(datestr);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getGroupTime(long timestamp) {
		if (TimeUtil.checkSameDay(System.currentTimeMillis() / 1000, timestamp)) {
			return "今天";
		} else if (TimeUtil.converTime(timestamp).indexOf("昨天") > -1) {
			return "昨天";
		} else {
			return TimeUtil.getMonthTime(timestamp) + "月"
					+ TimeUtil.getDayTime(timestamp) + "日";
		}
	}

	/**
	 * 将HH:mm类型的格式转换为millis
	 * 
	 * @param str
	 * @return
	 */
	public static long twentyfour2Millis(String str) {

		String[] times = str.split(":");

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		switch (times.length) {
		case 0:

			break;
		case 1:
			calendar.set(Calendar.HOUR_OF_DAY, NumberUtil.toInt(times[0]));
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			break;
		case 2:
			calendar.set(Calendar.HOUR_OF_DAY, NumberUtil.toInt(times[0]));
			calendar.set(Calendar.MINUTE, NumberUtil.toInt(times[1]));
			calendar.set(Calendar.SECOND, 0);
			break;
		case 3:
			calendar.set(Calendar.HOUR_OF_DAY, NumberUtil.toInt(times[0]));
			calendar.set(Calendar.MINUTE, NumberUtil.toInt(times[1]));
			calendar.set(Calendar.SECOND, NumberUtil.toInt(times[2]));
			break;
		}
		calendar.set(Calendar.MILLISECOND, 0);
		
		
		return calendar.getTimeInMillis();
	}

}