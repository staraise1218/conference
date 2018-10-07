package com.xq.conference.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	/**
	 * 获取系统时间
	 * @param part 时间格式，如：yyyy-MM-dd
	 * @return
	 */
	public static String getTime(String part){
		SimpleDateFormat sdf = new SimpleDateFormat(part);
		Date today = new Date();
		return sdf.format(today);
	}
	
	/**
	 * 获取默认系统时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getTime(){
		return getTime("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 获取系统日期 yyyy年MM月dd日
	 * @return
	 */
	public static String getDate(){
		return getTime("yyyy年MM月dd日");
	}
	/**
	 * 获取系统日期 yyyyMMdd
	 * @return
	 */
	public static String getDate2(){
		return getTime("yyyyMMdd");
	}
	/**
	 * 获取系统日期 yyMMdd
	 * @return
	 */
	public static String getDatePart(){
		return getTime("yyMMdd");
	}
	/**
	 * 获取系统日期 yyMM
	 * @return
	 */
	public static String getDatePart2(){
		return getTime("yyMM");
	}
	/**
	 * 将字符串转为时间类型
	 * @param time String 
	 * @param part 时间格式，如：yyyy-MM-dd
	 * @return
	 */
	public static Date getDate(String time, String part){
		SimpleDateFormat sdf = new SimpleDateFormat(part);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将字符串转为时间类型，默认格式：yyyy-MM-dd
	 * @param time String 
	 * @return
	 */
	public static Date getDate(String time){
		return getDate(time, "yyyy-MM-dd");
	}
	
	public static Date getDateMy(String time){
		return getDate(time, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前日期于周一相差的天数
	 * @return
	 */
	public static int getMondayPlus(){  
	    Calendar day=Calendar.getInstance();  
	    int dayOfWeek=day.get(Calendar.DAY_OF_WEEK);  
	    if(dayOfWeek==1){ //一周中第一天（周日）  
	        return -6;  
	    }else{  
	        return dayOfWeek-2;  
	    }  
	}
	
	   /** 
     * 将Date类型时间转换为字符串 
     * @param date 
     * @return 
     */  
    public static String toString(Date date) {  
  
        String time;  
        SimpleDateFormat formater = new SimpleDateFormat();  
        formater.applyPattern("yyyy-MM-dd");  
        time = formater.format(date);  
        return time;  
    }  
	
	/**
	 * 计算当前日期所在周的周一和周天的日期
	 * @param date 时间参数 格式为：yyyy-MM-dd
	 * @throws ParseException
	 */
	public static Map getWeekBeginAndEnd(String date) throws ParseException {
		Map<String,String> map = new HashMap<String,String> ();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
        Calendar cal = Calendar.getInstance();  
        Date time=sdf.parse(date);
        cal.setTime(time);  
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  

        System.out.println("所在周星期一的日期："+sdf.format(cal.getTime()));
       cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  

       int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
       cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
       String weekBegin = sdf.format(cal.getTime());
       cal.add(Calendar.DATE, 6);  
       String weekEnd = sdf.format(cal.getTime());
       map.put("weekBegin", weekBegin);
       map.put("weekEnd", weekEnd);
       return map;
	}
}
