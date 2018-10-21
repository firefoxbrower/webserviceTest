package matech.utils.quartz;


/**
 * Cron表达式工具类
 * @author CHEN
 *
 */
public class CronUtil {
	/**
	 * 周期类型
	 */
	public enum PeriodType {
		MINUTE, HOUR, DAY, WEEK, MONTH, QUARTER, YEAR;
	}
	/**
	 * 生成Quatz的Cron表达式
	 * @param periodType 周期类型
	 * @param period 周期值
	 * @param startTime	开始时间  小时:分钟  格式
	 * @return String
	 */	
	public static String createCronExpr(String periodType, String period, String startTime) {
		
		String [] parts = startTime.split(":");
		int hour = Integer.valueOf(parts[0]);
		int minute = Integer.valueOf(parts[1]);
		
		return createCronExpr(periodType, Integer.valueOf(period), hour,minute);
	}

	/**
	 * 生成Quatz的Cron表达式
	 * @param periodType 周期类型
	 * @param period 周期值
	 * @param hour	小时数
	 * @param minute 分钟数
	 * @return String
	 */
	public static String createCronExpr(String periodType, int period, int hour, int minute) {
		
		String cronExpr = "";

		switch (PeriodType.valueOf(periodType)) {
		case MINUTE:
			cronExpr = createMinuteCronExpr(period);
			break;
		case HOUR:
			cronExpr = createHourCronExpr(period);
			break;
		case DAY:
			cronExpr = createDayCronExpr(minute,hour,period);
			break;
		case WEEK:
			cronExpr = createWeekCronExpr(minute, hour, period);
			break;
		case MONTH:
			cronExpr = createMonthCronExpr(minute, hour, period);
			break;
		case QUARTER:
			cronExpr = createQuarterCronExpr(minute, hour, period);
			break;
		case YEAR:
			//格式如 ：9****
			String monthDay = String.valueOf(period);
			int month = Integer.valueOf(monthDay.substring(1,3));
			int day = Integer.valueOf(monthDay.substring(3, 5));
			cronExpr = createYearCronExpr(minute, hour, day, month) ;
			break;
		default:
			break;
		}
		return cronExpr;
	}
	/**
	 * 每隔 n分钟
	 * @param n
	 * @return
	 */
	public static String createMinuteCronExpr(int n){
		return String.format("0 0/%d * ? * *", n);
	}

	/**
	 * 每隔n小时
	 * @param n
	 * @return
	 */
	public static String createHourCronExpr(int n){
		return String.format("0 0 0/%d ? * *", n);
	}

	
	/**
	 * 每隔 n天
	 * 开始时间 hour:minute
	 * @param minute
	 * @param hour
	 * @param n
	 * @return
	 */
	public static String createDayCronExpr(int minute,int hour,int n){
		String cronExpr="0 %d %d 1/%d * ?";
		return String.format(cronExpr, minute, hour, n);
	}
	
	/**
	 * 每周 n
	 * 开始时间 hour:minute
	 * @param minute
	 * @param hour
	 * @param n
	 * @return
	 */
	public static String createWeekCronExpr(int minute,int hour,int n){
		
		return String.format("0 %d %d ? * %d", minute, hour, convertWeekDay(n));
	}
	
	/**
	 * 每月 第day号
	 * 开始时间 hour:minute
	 * @param minute
	 * @param hour
	 * @param day
	 * @return
	 */
	public static String createMonthCronExpr(int minute,int hour,int day){
		return String.format("0 %d %d %d * ?", minute, hour, day);
	}

	/**
	 * 每季度 第day号
	 * 开始时间 hour:minute
	 * @param minute
	 * @param hour
	 * @param day
	 * @return
	 */
	public static String createQuarterCronExpr(int minute,int hour,int day){
		
		return String.format("0 %d %d %d 1,4,7,10 ?", minute, hour, day);
	}

	/**
	 * 每季度 第month月第day号
	 * 开始时间 hour:minute
	 * @param minute
	 * @param hour
	 * @param day
	 * @return
	 */
	public static String createQuarterCronExpr(int minute,int hour,int day,int month){
		String months="";
		months=""+(month)+","+(3+month)+","+(6+month)+","+(9+month)+"";
		String cronExpr="0 %d %d %d "+months+" ?";
		return String.format(cronExpr, minute, hour, day);
	}
	
	/**
	 * 每年month月day号
	 * 开始时间 hour:minute
	 * @param minute
	 * @param hour
	 * @param day
	 * @param month
	 * @return
	 */
	public static String createYearCronExpr(int minute,int hour,int day,int month){
		return String.format("0 %d %d %d %d ?", minute, hour,day,month);
	}
	/**
	 * 转换每周 周数
	 * 开始时间 hour:minute
	 * @param n 1-7 表示周一至周日
	 * @return  2-7-1 表示 周一至周日 quartz用1-7 表示SUN-SAT
	 */
	public static int convertWeekDay(int n){
		return n%7+1;
	}	

}
