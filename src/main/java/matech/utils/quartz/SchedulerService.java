package matech.utils.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 调度计划管理服务类 
 *
 */
public class SchedulerService {

	private static Log log = LogFactory.getLog(SchedulerService.class);

	public static Scheduler scheduler;

	static {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			log.info("调度器启动...");

		} catch (Exception e) {
			log.error("调度器启动出错"+e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * 停止默认组触发器
	 * @param triggerName 触发器名称
	 * @return
	 */
	public static boolean pauseTrigger(String triggerName) {
		return pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
	}

	
	/**
	 * 停止触发器
	 * @param triggerName  触发器名称
	 * @param group  触发器所属组
	 * @return
	 */
	public static boolean pauseTrigger(String triggerName, String group) {
		boolean isSuccess = false;
		try {
			scheduler.pauseTrigger(triggerName, group);// 停止触发器
			isSuccess = true;
		} catch (SchedulerException e) {
			log.error("停止调度器出错"+e.getMessage());
			throw new RuntimeException(e);
		}

		if (isSuccess) {
			log.info("触发器：" + triggerName + "停止成功！");
		} else {
			log.info("触发器：" + triggerName + "停止失败！");
		}
		return isSuccess;
	}

	/**
	 * 注册调度任务
	 * @param jobDetail 任务
	 * @param triggerName 触发器名称
	 * @param groupName  触发器所属组
	 * @param cronExpression cron表达式
	 */
	public static void schedule(JobDetail jobDetail, String triggerName, String groupName, String cronExpression) {

		Trigger trigger = null;
		try {
			trigger = new CronTrigger(triggerName, groupName, jobDetail.getName(),jobDetail.getGroup(),cronExpression);
			//设置错过执行时，使用的策略，2 - 代表 什么都不做
			trigger.setMisfireInstruction(2);
		} catch (ParseException e) {
			log.error("注册调度器出错："+"cronExpression: " + cronExpression+" "+e.getMessage());
			e.printStackTrace();
		}
		schedule(jobDetail, trigger);

	}

	
	/**
	 * 注册调度任务
	 * @param jobDetail 任务
	 * @param trigger 触发器
	 */
	public static void schedule(JobDetail jobDetail, Trigger trigger) {

		Date firstStartTime = null;
		try {
			if (isTriggerExist(trigger)) {
				firstStartTime = scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
			} else {
				firstStartTime = scheduler.scheduleJob(jobDetail, trigger);
			}
			log.info("任务名称: " + jobDetail.getFullName() );
			log.info("触发器：" + trigger.getFullName());
			log.info("开始时间为：" + firstStartTime);

		} catch (SchedulerException e) {
			log.error("注册调度器出错："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 重启触发器
	 * @param triggerName 触发器名称
	 * @param group  触发器所属组
	 */
	public static void resumeTrigger(String triggerName, String group) {
		try {
			// 重启触发器
			scheduler.resumeTrigger(triggerName, group);
		} catch (SchedulerException e) {
			log.error("重启调度器出错："+e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除 触发器
	 * @param triggerName 触发器名称
	 * @param group 触发器所属组
	 * @return
	 */
	public static boolean removeTrigger(String triggerName, String group) {
		boolean result=false;
		try {
			
			log.info("移除触发器: " + triggerName );
			if (isTriggerExist(triggerName, group)){
				// 停止触发器
				scheduler.pauseTrigger(triggerName, group);
				// 移除触发器
				result=scheduler.unscheduleJob(triggerName, group);	
			}
		} catch (SchedulerException e) {
			log.error("移除调度器出错："+e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	
	/**
	 * 获得所有触发器列表
	 * @return
	 */
	public List<Trigger> getDefaultGroupTriggers() {

		return getTriggersByGroupName(Scheduler.DEFAULT_GROUP);
	}


	/**
	 * 获得该组的触发器列表
	 * @param groupName 触发器组名
	 * @return
	 */
	public static List<Trigger> getTriggersByGroupName(String groupName) {

		List<Trigger> triggers = new ArrayList<Trigger>();
		try {
			String[] triggerNames = scheduler.getTriggerNames(groupName);

			for (int i = 0; i < triggerNames.length; i++) {
				Trigger tigger = scheduler.getTrigger(triggerNames[i], groupName);
				triggers.add(tigger);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		return triggers;
	}

	/**
	 * 获得所有触发器列表
	 * @return
	 */
	public static List<Trigger> getAllTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();

		try {
			String[] TriggerGroupNames = scheduler.getTriggerGroupNames();
			for (int i = 0; i < TriggerGroupNames.length; i++) {
				triggers.addAll(getTriggersByGroupName(TriggerGroupNames[i]));
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		return triggers;
	}

	/**
	 * 检查触发器是否存在
	 * @param trigger
	 * @return
	 */
	public static boolean isTriggerExist(Trigger trigger) {
		boolean result = false;
		
		try {
			
			String triggerName = trigger.getName();
			String groupName = trigger.getGroup();
			log.info("获取触发器："+triggerName);
			Trigger newTrigger = scheduler.getTrigger(triggerName, groupName);
			if (null != newTrigger) {
				result = true;
				log.info("触发器：" + trigger.getFullName() + "已经存在！");
			}else{
				log.info("触发器：" + trigger.getFullName() + "不存在！");
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 检查触发器是否存在
	 * @param trigger
	 * @return
	 */
	public static boolean isTriggerExist(String triggerName,String groupName) {
		boolean result = false;
		
		try {
			log.info("获取触发器："+triggerName);
			Trigger newTrigger = scheduler.getTrigger(triggerName, groupName);
			if (null != newTrigger) {
				result = true;
			}else{
				log.info("触发器：" + triggerName + "不存在！");
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return result;
	}	
}
