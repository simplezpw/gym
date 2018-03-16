package com.vooda.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateTimeUtil {

	private static final Logger logger = Logger.getLogger(DateTimeUtil.class);
	private static String datePattern = "MM/dd/yyyy";
	private static String timePattern = "HH:mm";
	private static String dateTimePattern = "yyyyMMddHHmmss";
	private static final String ym_date_format = "yyyyMM";

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * dateTimePattern = "yyyyMMddHHmmss";
	 * 
	 * @return
	 */
	public static final String getDateTime(String pattern) {
		SimpleDateFormat df = null;
		String returnValue = "";

		df = new SimpleDateFormat(pattern);
		returnValue = df.format(new Date());

		return (returnValue);
	}

	public static final String getDateTime() {
		SimpleDateFormat df = null;
		String returnValue = "";

		df = new SimpleDateFormat(dateTimePattern);
		returnValue = df.format(new Date());

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (logger.isDebugEnabled()) {
			logger.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// logger.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			logger.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("converting date with pattern: " + datePattern);
			}

			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) {
			logger.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * 获得当月的第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		String day = "";
		Calendar cal = new GregorianCalendar();
		if ((cal.get(Calendar.MONTH) + 1) < 10) {
			day = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1)
					+ "-01";
		} else {
			day = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1)
					+ "-01";
		}
		return day;
	}

	/**
	 * 获得当月的最后一天
	 * 
	 * @return
	 */
	public static String getLastDayOfMonth() {
		String day = "";
		try {
			Calendar cal = new GregorianCalendar();
			int year;
			int month;
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1; // 实际月份的后有一个月
			if (month > 11) {
				year++;
				month = 12;
			}
			GregorianCalendar cal1 = new GregorianCalendar();
			cal1.set(year, month - 1, 1);
			cal1.roll(Calendar.DATE, -1); // 向前回滚得到当月的日期
			int date = cal1.get(Calendar.DATE);
			if (month < 10) {
				if (date < 10) {
					day = year + "-0" + month + "-0" + date;
				} else {
					day = year + "-0" + month + "-" + date;
				}
			} else {
				if (date < 10) {
					day = year + "-" + month + "-0" + date;
				} else {
					day = year + "-" + month + "-" + date;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 加减月
	 * 
	 * @param month
	 * @param offset
	 * @param pattern
	 * @return
	 */
	public static String addMonth(String month, int offset, String pattern) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Calendar cl = Calendar.getInstance();
			cl.setTime(sdf.parse(month));
			cl.add(Calendar.MONTH, offset);
			return sdf.format(cl.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换日期字符串
	 * 
	 * @param day
	 * @param srcPattern
	 * @param toPattern
	 * @return
	 */
	public static String convertDayString(String day, String srcPattern,
			String toPattern) {
		try {
			SimpleDateFormat srcFormat = new SimpleDateFormat(srcPattern);
			SimpleDateFormat toFormat = new SimpleDateFormat(toPattern);
			return toFormat.format(srcFormat.parse(day));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算时间差（返回小时，超过半小时则算一小时）
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long countTimeHours(Date begin, Date end) {
		long total_second = 0;
		Date begin_date = begin;
		Date end_date = end;
		total_second = (end_date.getTime() - begin_date.getTime())
				/ (1000); //秒
		
		long hours = total_second / 3600; 
		
		if(total_second - (hours * 3600) > 1800){//大于半小时算一小时
			hours = hours + 1;
		}
		
		return hours;
	}
	
	/**
	 * 计算时间差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Long countTime(Date begin, Date end) {
		long total_second = 0;
		Date begin_date = begin;
		Date end_date = end;
		total_second = (end_date.getTime() - begin_date.getTime())
				/ (1000); //秒
		
		return total_second;
	}

	
	
	public static String getYYMM(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(ym_date_format);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	public static Date getTopMouth(Date date){
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化对象
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}
	
	/**
	 * 几天后
	 * @param date 当前操作的时间
	 * @param numDay 多少天后
	 * @return
	 * @throws Exception 
	 */
	public static Date getAfterDay(Date date,int numDay) throws Exception{
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		date = sdf.parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, numDay);
		
		return cal.getTime();
	}
	
	/**
	 * 获取当前星期的星期几
	 * @param date 
	 * @param day 获取星期几 如： 6的话是返回星期六的日期
	 * @return
	 * @throws Exception 
	 */
	public static Date getIsToDay(Date date,int day) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(date == null){
			date = new Date();
		}
		
		Calendar cdr = Calendar.getInstance();
		cdr.setTime(date);
		//day 必须大于或者等于num
		int num = cdr.get(Calendar.DAY_OF_WEEK);//获取date是星期几
		
		cdr.set(Calendar.DATE, cdr.get(Calendar.DATE) + (day - num) + 1);//
		return sdf.parse(sdf.format(cdr.getTime()));
		
	}
	
	/**
	 * 获取date是今年第几个星期,星期日为一个星期的第一天
	 * @return
	 */
	public static int getYearToNumToDay(Date date){
		Calendar cdr = Calendar.getInstance();
		cdr.setTime(date);
		return cdr.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 获取最近半年的年月yyyyMM
	 * @return
	 */
	public static String[] getHalfYearToYM(Date date){
		
		String[] str = new String[6];
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		str[0] = sdf.format(calendar.getTime());
		for (int i = 1; i < str.length; i++) {
			calendar.add(Calendar.MONTH, -1);
			str[i] = sdf.format(calendar.getTime());
		}
//		
//		for (String s : str) {
//			System.out.println(s);
//		}
		return str;
	} 
	
	/**
	 * 根据出生日期返回年龄
	 * @param dstr 出生年月日1990-01-01
	 * @return
	 */
	public static int getDateToAge(String dstr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//得到当前的年份
		String cYear = sdf.format(new Date()).substring(0,4);
		//得到生日年份
		String birthYear = dstr.substring(0,4);

		return Integer.parseInt(cYear) - Integer.parseInt(birthYear);
	}
	

	public static void main(String[] args) throws Exception {
	//	System.out.println(getAfterDay(new Date(),7));
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//	Date t = sdf.parse("2014-12-28");
	//	System.out.println(sdf.format(getIsToDay(t,7)));
//		System.out.println(getNumYear(sdf.parse("2014-12-20")));
		
	//	getHalfYearToYM(new Date());
		System.out.println(getDateToAge("1990-01-01"));
	}
}
