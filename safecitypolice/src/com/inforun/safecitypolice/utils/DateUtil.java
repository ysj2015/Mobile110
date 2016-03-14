package com.inforun.safecitypolice.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * 日期操作类
 * @author daniel
 *
 */
public class DateUtil {

    /**
     * 获得年份
     *
     * @param y年
     * @param m
     * 月
     * @param d天
     * @return yyyy-MM-dd格式化时间
     */

    public static final int FIRST = 1;

    public static String getStringDate(int year, int month, int day) {// date--string
        // int
        // int int
        // -->string
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy.MM.dd");
        return simpledateformat.format(calendar.getTime());
    }

    /**
     * 获取系统当前日期
     *
     * @param pattern 日期格式
     * @return
     */
    public static String getSystemDate(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 2012-06-07获得每个月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFistDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);// 8 9
        calendar.set(Calendar.DAY_OF_MONTH, FIRST);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(calendar.getTime());
    }

    public static String getFistDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, FIRST);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(calendar.getTime());
    }

    public static String getDate(String dates){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy HH:mm:ss aa",Locale.US);
		Date date = null;
		try {
			date = sdf.parse(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
		return sdfs.format(date);
    }

    public static String getLastDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(calendar.getTime());
    }
    /**
	 * 根据时间戳算出日期
	 * 
	 * @param c_time
	 *            时间戳
	 * @return
	 */
	public static String getDateByShiJianChuo(String c_time, String formet) {
		SimpleDateFormat sdf = new SimpleDateFormat(formet);
		String date = sdf.format(new Date(Long.parseLong(c_time) * 1000L))
				.toString();
		return date;
	}

	/**
	 * 获取string时间戳
	 */
	public static String getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d;
		try {
			d = sdf.parse(user_time);

			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return re_time;
	}
	
    /**
     * 2012-06-07获得每个月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(calendar.getTime());
    }

    public static String getStringDate(Calendar calendar) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(calendar.getTime());
    }
    
    public static String getStringDate(Calendar calendar,String format) {
    	SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
    	return simpledateformat.format(calendar.getTime());
    }
    /**
	 * 比较两个时间的大小
	 * 
	 * @param starttime
	 * @param endtime
	 * @return false starttime小 早
	 */
	public static boolean compDate(String starttime, String endtime,
			String format) {
		java.text.DateFormat df = new SimpleDateFormat(format);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(starttime));
			c2.setTime(df.parse(endtime));
		} catch (ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		if (result < 0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 根据总毫秒秒数 算出 时分秒
	 * 
	 * @param time_count
	 *            总毫秒数
	 * @return
	 */
	public static HashMap<String, Object> getTimeBymiaocount(long time_count) {
		int miao_count = (int) (time_count / 1000);
		String time = null;
		String miao;
		String min;
		String hour;
		if (miao_count < 60) {
			if (miao_count < 10) {
				time = "00:00:0" + miao_count;
			} else {
				time = "00:00:" + miao_count;
			}
		}
		if (miao_count >= 60 && miao_count < 3600) {
			if (miao_count / 60 < 10) {
				if (miao_count % 60 < 10) {
					time = "00:0" + miao_count / 60 + ":0" + miao_count % 60;
				} else {
					time = "00:0" + miao_count / 60 + ":" + miao_count % 60;
				}
			} else {
				if (miao_count % 60 < 10) {
					time = "00:" + miao_count / 60 + ":0" + miao_count % 60;
				} else {
					time = "00:" + miao_count / 60 + ":" + miao_count % 60;
				}
			}

		} else if (miao_count >= 3600) {
			if (miao_count / 3600 < 10) {
				if (miao_count % 3600 / 60 < 10) {
					if (miao_count % 60 < 10) {
						time = "0" + miao_count / 3600 + ":0" + miao_count
								% 3600 / 60 + ":0" + miao_count % 3600 % 60;
					} else {
						time = "0" + miao_count / 3600 + ":0" + miao_count
								% 3600 / 60 + ":" + miao_count % 3600 % 60;
					}
				} else {
					if (miao_count % 60 < 10) {
						time = "0" + miao_count / 3600 + ":" + miao_count
								% 3600 / 60 + ":0" + miao_count % 3600 % 60;
					} else {
						time = "0" + miao_count / 3600 + ":" + miao_count
								% 3600 / 60 + ":" + miao_count % 3600 % 60;
					}
				}
			}
			if (miao_count / 3600 >= 10) {
				if (miao_count % 3600 / 60 < 10) {
					if (miao_count % 60 < 10) {
						time = miao_count / 3600 + "0:" + miao_count % 3600
								/ 60 + ":0" + miao_count % 3600 % 60;
					} else {
						time = miao_count / 3600 + "0:" + miao_count % 3600
								/ 60 + ":" + miao_count % 3600 % 60;
					}
				} else {
					if (miao_count % 60 < 10) {
						time = miao_count / 3600 + ":" + miao_count % 3600 / 60
								+ ":0" + miao_count % 3600 % 60;
					} else {
						time = miao_count / 3600 + ":" + miao_count % 3600 / 60
								+ ":" + miao_count % 3600 % 60;
					}

				}
			}
		}
		hour = time.substring(0, time.indexOf(":"));
		min = time.substring(time.indexOf(":") + 1, time.lastIndexOf(":"));
		miao = time.substring(time.lastIndexOf(":") + 1);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("hour", hour);
		map.put("min", min);
		map.put("miao", miao);
		return map;
	}
	/**
	 * 根据日期算星期
	 * @return
	 */
	public static String getWeekd(Calendar c) {
		String Week = null;
	
		switch(c.get(Calendar.DAY_OF_WEEK)){
		case 1:
			Week = "日";
			break;
		case 2:
			Week = "一";
			break;
		case 3:
			Week = "二";
			break;
		case 4:
			Week = "三";
			break;
		case 5:
			Week = "四";
			break;
		case 6:
			Week = "五";
			break;
		case 7:
			Week = "六";
			break;
		default:
			break;          
		}   
		return Week;
	}
}
