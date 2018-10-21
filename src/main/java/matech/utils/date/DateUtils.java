package matech.utils.date;

import matech.utils.string.StringUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * 日期处理函数
 * 
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author MATECH
 * @version 1.0
 */
public class DateUtils {
	private final static Log log = LogFactory.getLog(DateUtils.class);
	// 一天的毫秒数 60*60*1000*24
	private final static long DAY_MILLIS = 86400000;
	// 日期格式化转换对象
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 不能外部实例化
    private DateUtils(){
        
    }
    /**
     * 根据格式化字符显示日期
     * 
     * @String 格式字符串
     * @param millis
     *            长整型
     * @return String
     */
    public static String formatDate(String format,long millis) {
        String date = DateFormatUtils.format(millis, format,TimeZone.getTimeZone("GMT+0800"));
        return date;
    }
    /**
     * 根据格式化字符显示当前日期
     * 
     * @String 格式字符串
     *            长整型
     * @return String
     */
    public static String formatDate(String format) {
        String date = DateFormatUtils.format(getTimeInMillis(), format,TimeZone.getTimeZone("GMT+0800"));
        return date;
    }
	/**
	 * 
	 * 将字符串按照格式转换成日期格式
	 * 
	 */
	public static Date parseDate(SimpleDateFormat format, String date,ParsePosition parsePosition) {
		Date dt = null;
		try {
			format.setTimeZone(TimeZone.getTimeZone("GMT+0800"));
			dt = format.parse(date,parsePosition);
		} catch (Exception e) {
			log.error("执行方法Date parseDate(SimpleDateFormat format, String date,ParsePosition parsePosition)出错："+e.getMessage());
			e.printStackTrace();
		}
		return dt;
	}
	/**
	 * 
	 * 将字符串按照格式转换成日期格式
	 * 
	 */
	public static Date parseDate(SimpleDateFormat format, String date) {
		Date dt = null;
		try {
			format.setTimeZone(TimeZone.getTimeZone("GMT+0800"));
			dt = format.parse(date);
		} catch (Exception e) {
			log.error("执行方法Date parseDate(SimpleDateFormat format, String date)出错："+e.getMessage());
			e.printStackTrace();
		}
		return dt;
	}
	/**
	 * 
	 * 将字符串转换成日期格式
	 * 
	 */
	public static Date parseDate(String dateStr) {
		Date date = null;
		if (null == dateStr || 0 == dateStr.length())
			return null;
	
		if (dateStr.length() > 10){
			date =parseDate(DATE_TIME_FORMAT,dateStr); 
		}else{
			date = parseDate(DATE_FORMAT,dateStr);			
		}
		return date;
	}
	
	/**
	 * 
	 * 将日期字符串转换成中文字符串
	 * 
	 */
	public static String transToChineseDay(String date){
		String result=date;
		String[] params=date.split("-");
		for(int i=0;i<params.length;i++){
			if(i==0){
				result=params[i]+"年";
			}
			if(i==1){
				result=result+params[i]+"月";
			}
			if(i==2){
				result=result+params[i]+"日";
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 将对象转换成日期格式
	 * 
	 */
	public static Date parseDate(Object object) {
		if (object instanceof Date)
			return (Date) object;

		if (object instanceof Calendar)
			return ((Calendar) object).getTime();

		return parseDate(object.toString());
	}
	/**
	 * 
	 * 将对象转换成日期格式
	 * 
	 */
	public static Date parseDate(long millis) {
		Date date=new Date(millis);
		return date;
	}
	/**
	 * 
	 * 将字符串按照格式转换成日期格式
	 * 
	 */
	public static Calendar parseCalendar(Object format, String string) {
		Calendar cal = null;
		Date date = null;

		if (format instanceof SimpleDateFormat){
			date = parseDate((SimpleDateFormat) format, string);
		}else{
			date = parseDate(string);
		}
		if (null != date) {
			cal = Calendar.getInstance();
			cal.setTime(date);
		}
		return cal;
	}
	/**
	 * 
	 * 将字符串转换成日期格式
	 * 
	 */
	public static Calendar parseCalendar(String string) {
		Calendar cal = null;
		Date date = null;

		date = parseDate(string);

		if (null != date) {
			cal = Calendar.getInstance();
			cal.setTime(date);
		}

		return cal;
	}
	/**
	 * 
	 * 将对象转换成日期格式
	 * 
	 */
	public static Calendar parseCalendar(Object object) {
		Calendar cal = Calendar.getInstance();

		if (object instanceof Date) {
			cal.setTime((Date) object);
			return cal;
		}

		if (object instanceof Calendar)
			return (Calendar) object;

		return parseCalendar(object.toString());
	}
    /**
     * 
     * 取得当前日期毫秒数
     * 
     * @return long
     */
    public static long getTimeInMillis(){
    	Calendar cal=Calendar.getInstance(); 
    	//cal.setTimeZone(TimeZone.getTimeZone("GMT+0800"));
    	return cal.getTimeInMillis();
    }
    /**
     * 
     * 得到日dd
     * 
     * @return 
     */
	public static String getDay(long millis) {
		return DateUtils.formatDate("dd",millis);
	}
    /**
     * 
     * 得到当前日dd
     * 
     * @return 
     */
	public static String getCurrentDay() {
		return DateUtils.formatDate("dd");
	}
    /**
     * 根据日期获得星期几
     * 
     * @param long
     *            指定的日期
     * 
     * @return String
     */
    public static String getWeek(long millis) {
 
        String days[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cale = Calendar.getInstance();
        cale.setTimeInMillis(millis);
        cale.setFirstDayOfWeek(Calendar.SUNDAY);

        return days[cale.get(Calendar.DAY_OF_WEEK) - 1];
    } 
    /**
     * 获得当前星期几
     * 
     * 
     * @return String
     */
    public static String getCurrentWeek() {
        return getWeek(DateUtils.getTimeInMillis());
    } 
    /**
     * 
     * 得到月MM
     * 
     * @return String
     */
    public static String getMonth(long millis) {
    	return DateUtils.formatDate("MM",millis);
    }
    /**
     * 
     * 得到当前月MM
     * 
     * @return String
     */
    public static String getCurrentMonth() {
    	return DateUtils.formatDate("MM");
    }
    /**
     * 
     * 得到季度
     * 
     * @return String
     */
    public static String getQuarter(long millis) {
        String quarter = "";
        String month = getMonth(millis);
        if (month.equals("01") || month.equals("02") || month.equals("03")) {
            quarter = "第一季度";
        } else if (month.equals("04") || month.equals("05")
                || month.equals("06")) {
            quarter = "第二季度";
        } else if (month.equals("07") || month.equals("08")
                || month.equals("09")) {
            quarter = "第三季度";
        } else {
            quarter = "第四季度";
        }
        return quarter;
    }
    
    public static String getCurQuarter(long millis) {
        String quarter = "";
        String month = getMonth(millis);
        if (month.equals("01") || month.equals("02") || month.equals("03")) {
            quarter = "1";
        } else if (month.equals("04") || month.equals("05")
                || month.equals("06")) {
            quarter = "2";
        } else if (month.equals("07") || month.equals("08")
                || month.equals("09")) {
            quarter = "3";
        } else {
            quarter = "4";
        }
        return quarter;
    }
    
    /**
     * 
     * 得到当前季度
     * 
     * @return String
     */
    public static String getCurrentQuarter() {
        return getQuarter(DateUtils.getTimeInMillis());
    }
    /**
     * 
     * 得到年yyyy
     * 
     * @return String
     */
    public static String getYear(long millis) {
    	return DateUtils.formatDate("yyyy",millis);
    } 
    /**
     * 
     * 得到当前年yyyy
     * 
     * @return String
     */
    public static String getCurrentYear() {
    	return DateUtils.formatDate("yyyy");
    }
    /**
     * 
     * 得到年-月yyyy-MM
     * 
     * @return String
     */
    public static String getYearAndMonth(long millis) {
    	return DateUtils.formatDate("yyyy-MM",millis);
    }
    /**
     * 
     * 得到当前年-月yyyy-MM
     * 
     * @return String
     */
    public static String getCurrentYearAndMonth() {
    	return DateUtils.formatDate("yyyy-MM");
    }

    /**
     * 格式化输入的日期 时间
     * 
     * @param date
     *            日期 时间
     * @return 日期
     */
    public static java.sql.Date getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * 把字符串形式的日期 转换为 date类型
     * 
     * @param dateString
     * @return 日期
     */
    public static java.sql.Date getDate(String dateString) {
        return java.sql.Date.valueOf(dateString);
    }   
    /**
     * 
     * 得到当前日期yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate(long millis) {
    	return DateUtils.formatDate("yyyy-MM-dd",millis);
    }
    /**
     * 
     * 得到当前日期yyyy-MM-dd
     * 
     * @return String
     */
    public static String getCurrentDate() {
    	return DateUtils.formatDate("yyyy-MM-dd");
    }
    /**
     * 
     * 得到当前时间yyyy-MM-dd HH:mm:ss
     * 
     * @return String
     */
    public static String getCurrentDateTime() {
    	return DateUtils.formatDate("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 
     * 得到当前时间yyyy-MM-dd HH:mm:ss
     * 
     * @return String
     */
    public static String getCurrentTime() {
    	return DateUtils.formatDate("HH:mm:ss");
    }
    /**
     * 根据日期获得日期星期几格式的字符串，如“2004-07-28 星期三”
     *
     *            指定的日期
     * 
     * @return 返回的日期星期几格式的字符串
     */
    public static String getDayWeek(long millis) {
 
        String days[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cale = Calendar.getInstance();
        cale.setTimeInMillis(millis);
        cale.setFirstDayOfWeek(Calendar.SUNDAY);

        return DateUtils.formatDate("yyyy-MM-dd",millis) + " " + days[cale.get(Calendar.DAY_OF_WEEK) - 1];
    } 
        
    /**
     * 取得当前日期
     * 
     * @return 当前日期
     */
    public static java.sql.Date getNowDate() {
        return new java.sql.Date(DateUtils.getTimeInMillis());
    }
    /**
     * 取得当前日期，时间
     * 
     * @return 当前日期 时间
     */
    public static Timestamp getNowTimestamp() {
        return new Timestamp(DateUtils.getTimeInMillis());
    }

    /**
     * 用于获取指定日期该月的所有日期
     *
     *            java.sql.Date 要获取的月日期列表的指定日期
     * @return Date[] java.sql.Date 返回的日期列表
     */
    public static java.sql.Date[] getMonthDays(String dateString) {
    	java.sql.Date date=DateUtils.getDate(dateString);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);

        int today = cale.get(Calendar.DAY_OF_MONTH);
        int days = cale.getActualMaximum(Calendar.DAY_OF_MONTH);
        long millis = cale.getTimeInMillis();

        java.sql.Date dates[] = new java.sql.Date[days];
        for (int i = 1; i <= days; i++) {
            long sub = (today - i) * DAY_MILLIS;
            dates[i - 1] = new java.sql.Date(millis - sub);
        }

        cale = null;
        return dates;
    }

    /**
     * 用于获取指定日期该周的所有日期
     *
     *            java.sql.Date 要获取的周日期列表的指定日期
     * @return Date[] java.sql.Date 返回的日期列表
     */
    public static java.sql.Date[] getWeekDays(String dateString) {
    	java.sql.Date date=DateUtils.getDate(dateString);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.setFirstDayOfWeek(Calendar.SUNDAY);

        int days = 7;
        int today = cale.get(Calendar.DAY_OF_WEEK);
        long millis = cale.getTimeInMillis();

        java.sql.Date dates[] = new java.sql.Date[days];
        for (int i = 1; i <= days; i++) {
            long sub = (today - i) * DAY_MILLIS;
            dates[i - 1] = new java.sql.Date(millis - sub);
        }

        cale = null;
        return dates;
    }

    /**
     * 根据开始时间和结束时间返回相应的时间段字符串，如果开始时间和结束时间在同一天，
     * 则返回的格式为“X点X分-X点X分”，如果不在同一天，返回的格式为“X月X日-X月X日”
     * 
     * @param startTime
     *            开始时间
     * 
     * @param endTime
     *            结束时间
     * 
     * @return 返回的时间段字符串
     */
    public static String getTimeSlice(Timestamp startTime, Timestamp endTime) {

        String rtn = "";

        Calendar caleStart = Calendar.getInstance();
        Calendar caleEnd = Calendar.getInstance();
        caleStart.setTimeInMillis(startTime.getTime());
        caleEnd.setTimeInMillis(endTime.getTime());

        String dayStart = caleStart.get(Calendar.YEAR) + "年"
                + (caleStart.get(Calendar.MONTH) + 1) + "月"
                + caleStart.get(Calendar.DAY_OF_MONTH) + "日";
        String dayEnd = caleEnd.get(Calendar.YEAR) + "年"
                + (caleEnd.get(Calendar.MONTH) + 1) + "月"
                + caleEnd.get(Calendar.DAY_OF_MONTH) + "日";

        if (dayStart.equals(dayEnd)) {
            //同一天
            rtn = caleStart.get(Calendar.HOUR_OF_DAY) + "点"
                    + caleStart.get(Calendar.MINUTE) + "分-"
                    + caleEnd.get(Calendar.HOUR_OF_DAY) + "点"
                    + caleEnd.get(Calendar.MINUTE) + "分";
        } else {
            //不在同一天
            rtn = (caleStart.get(Calendar.MONTH) + 1) + "月"
                    + caleStart.get(Calendar.DAY_OF_MONTH) + "日" + "-"
                    + (caleEnd.get(Calendar.MONTH) + 1) + "月"
                    + caleEnd.get(Calendar.DAY_OF_MONTH) + "日";
        }

        caleStart = null;
        caleEnd = null;
        return rtn;
    }
    /**
     * 获得指定日期所在月最小时间，时间部分为“00:00:00.000” 例如：param:2004-08-20 return:2004-08-01
     * 00.00.00.000
     *            指定的日期
     * @return 指定日期所在月的最小时间
     */
    public static Timestamp getMinDayInMonth(String dateString) {
    	java.sql.Date date=DateUtils.getDate(dateString);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMinimum(Calendar.DAY_OF_MONTH));
        java.sql.Date newDate = new java.sql.Date(cale.getTimeInMillis());
        cale = null;
        return Timestamp.valueOf(newDate.toString() + " 00:00:00.000");

    }

    /**
     * 获得指定日期所在月的最大时间，时间部分为“23.59.59.999” 例如：param:2004-08-20,return:2004-08-31
     * 23.59.59.999
     * @return Timestamp
     */
    public static Timestamp getMaxDayInMonth(String dateString) {
    	java.sql.Date date=DateUtils.getDate(dateString);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        java.sql.Date newDate = new java.sql.Date(cale.getTimeInMillis());

        cale = null;
        return Timestamp.valueOf(newDate.toString() + " 23:59:59.999");
    }
    /**
     * 
     * 获得一年第一天
     * @return String
     */    
	public static long getYearStartTime(int year) {
		
		Date date = null;

		date = DateUtils.parseDate(DATE_TIME_FORMAT, year +"-01-01 00:00:00");

		return date.getTime();
	}
	/**
     * 获得一年最后一天
     *
     * @return String
     */	
	public static long getYearEndTime(int year) {
		Date date = null;

		date = DateUtils.parseDate(DATE_TIME_FORMAT, year + "-12-31 23:59:59");

		return date.getTime();
	
	}
	/**
     * 获得 日期这年的剩余月数
     * 
     * @return int
     */	
	// 
	public static int getRemainMonthOfYear() {
		return 12 - Integer.valueOf(DateUtils.getCurrentMonth());
	}
	/**
	 * 
	 * 根据开始日期算出n个月之后的那一天
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param month
	 *            月数(多少个月后)
	 * @return
	 */
	public static String getEndDate(String beginDate, int month) {
		Calendar calendar = Calendar.getInstance();
		Date sd = DateUtils.parseDate(DATE_FORMAT, beginDate,new ParsePosition(0));
		calendar.setTime(sd);
		calendar.add(Calendar.MONTH, month);
		String endDate = DateUtils.formatDate("yyyy-MM-dd",calendar.getTimeInMillis());
		return endDate;
	}
	/**
	 * 
	 * 计算两个日期相差的天数
	 *
	 * @return int
	 */
	public static int getDifDate(String startDate, String endDate) {

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		Date beginDate = DateUtils.parseDate(DATE_FORMAT, startDate,new ParsePosition(0));
		Date finishDate =DateUtils.parseDate(DATE_FORMAT, endDate,new ParsePosition(0)); 
		
		start.setTime(beginDate);
		end.setTime(finishDate);

		long temp = end.getTimeInMillis() - start.getTimeInMillis();
		return (int) (temp / DAY_MILLIS);
	}
	/**
	 * 
	 * 计算两个月相差的月数
	 * @return int
	 */
	public static int getDifMonth(String begin, String end) throws Exception {

		Date beginDate = DateUtils.parseDate(DATE_FORMAT, begin,new ParsePosition(0));
		Date endDate =DateUtils.parseDate(DATE_FORMAT, end,new ParsePosition(0)); 
		
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);

		int beginYear = beginCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		
		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth =endCalendar.get(Calendar.MONTH);

		int difMonth = (endYear - beginYear) * 12 + (endMonth - beginMonth);

		return difMonth;

	}
	/**
	 * 
	 * 相差的年数
	 * @return int
	 */
	public static int getDifYear(String begin, String end) {
		Date beginDate = DateUtils.parseDate(DATE_FORMAT, begin,new ParsePosition(0));
		Date endDate =DateUtils.parseDate(DATE_FORMAT, end,new ParsePosition(0)); 
		
		Calendar start = Calendar.getInstance();
		start.setTime(beginDate);
		
		Calendar finish = Calendar.getInstance();
		finish.setTime(endDate);

		return finish.get(Calendar.YEAR)-start.get(Calendar.YEAR);
	}
    /**
     * 将java.sql.Date类对象转换为java.sql.Timestamp类对象， 时间部分为"00:00:00.000"
     * 
     * @param date
     *            java.sql.Date 要转换的Date型对象
     * @return java.sql.Timestamp 转换后的Timestamp型对象
     */
    public static Timestamp convertDateToTimestampMin(java.sql.Date date) {
        return Timestamp.valueOf(date.toString() + " 00:00:00.000");
    }

    /**
     * 将java.sql.Date类对象转换为java.sql.Timestamp类对象， 时间部分为"23:59:59.999"
     * 
     * @param date
     *            java.sql.Date 要转换的Date型对象
     * @return java.sql.Timestamp 转换后的Timestamp型对象
     */
    public static Timestamp convertDateToTimestampMax(java.sql.Date date) {
        return Timestamp.valueOf(date.toString() + " 23:59:59.999");
    }
	/**
	 * 生成时间 如： 09:12
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static String createTime(String hour, String minute){
		
		return StringUtil.fixPreZero(hour) + ":" + StringUtil.fixPreZero(minute);
	}	
	/**
	 * 将格式为yyyymm的字符串转化为yyyy-mm的字符串。如：200810转化为2008-10
	 * 
	 * @param str
	 *            yyyymm格式字符串
	 * @return yyyy-mm格式字符串
	 */
	public static String stringToMonth(String str) {
		return str.substring(0, str.length() - 2) + "-"+ str.substring(str.length() - 2, str.length());
	}

	/**
	 * 将格式为yyyymmdd的字符串转化为yyyy-mm-dd的字符串。如：20081010转化为2008-10-10
	 * 
	 * @param str
	 *            yyyymmdd格字符串
	 * @return yyyy-mm-dd字符串
	 */
	public static String stringToDay(String str) {
		return str.substring(0, 4) + "-" + str.substring(4, 6) + "-"+ str.substring(6, 8);
	}
	
	public static String simpleDate(String auditDate) {
		// TODO Auto-generated method stub
		if(!"".equals(StringUtil.showNull(auditDate))){
			int year = Integer.parseInt(auditDate.substring(0,4));
			int month = Integer.parseInt(auditDate.substring(5,7));
			int day = Integer.parseInt(auditDate.substring(8,10));
			return year+"年"+month+"月"+day+"日";
		}else
			return "";
	}
}
