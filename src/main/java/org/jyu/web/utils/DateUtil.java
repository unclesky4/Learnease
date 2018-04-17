package org.jyu.web.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Format YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Format YMD = new SimpleDateFormat("yyyy-MM-dd");
    
	/**
	 * 将时间转换为2015-06-19 11:38:13格式
	 */
	public static String DateToString(Format ft,Date date) {
		if(date == null) return null;
		return ft.format(date);
	}
	
	/**
	 * 将时间戳转换为2015-06-19 11:38:13格式
	 */
	public static String DateToString(Format ft,long date) {
		return ft.format(date);
	}
	
	/**
	 * 将long型或者2015-06-19 11:38:13格式字符串转换为时间
	 * @return 失败返回null
	 */
	public static Date stringToDate(Format ft,String str) {
		try {
			long time = Long.parseLong(str);
			return new Date(time);
		} catch(Exception e) {
			try {
				return (Date) ft.parseObject(str);
			} catch (Exception e1) {}
		}
		return null;
	}
	
	/**
	 * 将long型时间差转换为中文具体时间
	 */
	public static String LongToDuration(long diff){
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/1000;
		return ""+days+"天"+hours+"小时"+minutes+"分"+seconds+"秒"; 
	}
	
	/**
	 * 时间前推或后推分钟,其中minute表示分钟.
	 */
	public static String deadLine(String startTime, int minute) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String mydate1 = "";
	    try {
	        Date date1 = format.parse(startTime);
	        long Time = (date1.getTime() / 1000) + minute * 60;
	        date1.setTime(Time * 1000);
	        mydate1 = format.format(date1);
	      } catch (Exception e) {
	      }
	      return mydate1;
	}
	
	/**
	 * 两个时间之间的天数
	 * @param nowTime      yyyy-MM-dd HH:mm:ss
	 * @param lastTime     yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long getHours(String nowTime, String lastTime) {
	    if (nowTime == null || nowTime.equals(""))
	      return 0;
	    if (lastTime == null || lastTime.equals(""))
	      return 0;
	    // 转换为标准时间
	    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date date1 = null;
	    java.util.Date date2 = null;
	    try {
	    	date1 = myFormatter.parse(nowTime);
	    	date2 = myFormatter.parse(lastTime);
	    } catch (Exception e) {
	    }
	    long hour = (date2.getTime() - date1.getTime()) / (60 * 60 * 1000);
	    return hour;
	}
	
	/**
	 * 某个时间距离现在时间的秒数
	 * @param time
	 * @return
	 */
	public static long getSeconds(String time) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds = 0L;
		try {
			Date date = myFormatter.parse(time);
			seconds = (date.getTime() - new Date().getTime())/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return seconds;
	}
}
