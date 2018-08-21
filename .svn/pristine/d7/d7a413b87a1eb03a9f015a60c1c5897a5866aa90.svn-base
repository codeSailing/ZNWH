package com.quickdone.znwh.taskhandle;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.*;

/**
 * @Auther: psf
 * @Date: 2018/7/13 14:24
 * @Description:定时任务管理类，
 */
public class QuartzManager {
    private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);
    private static String JOB_GROUP_NAME = "JOB_GROUP_ZNWH";
    private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_ZNWH";



    /**
     * 每次任务新建以后调用这个方法，生成一个定时任务
     * param：1、任务id,2、电话号码串，“，”隔开,cron 时间表达式
     */

    public void generateScheduleTask(Scheduler sched, String jobName,  String crontime, String currentid,String  taskid, String  phones,Map<String,Object> params) throws SchedulerException {
        logger.info("开始生成一个定时任务："+jobName+"  crontime:"+crontime);
        JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类
        JobDetail jobDetail = newJob(QuartzTask.class).withIdentity(jobKey).build();
        JobDataMap jobDataMap=jobDetail.getJobDataMap();
        jobDataMap.put("currentNode",currentid);
        jobDataMap.put("taskid",taskid);
        TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);// 触发器
        Trigger trigger = newTrigger().withIdentity(triggerKey)
                .withSchedule(cronSchedule(crontime)).build();// 触发器时间设定
        sched.scheduleJob(jobDetail, trigger);
        if (!sched.isShutdown()) {
            sched.start();// 启动
        }

    }


    //模拟实际场景的任务实例
    public void generateScheduleTask1(HttpServletResponse response,Scheduler sched, String jobName, String crontime, String currentid, String  taskid, String flowid, String  phones) throws SchedulerException {
        logger.info("开始生成定时任务："+jobName+"  crontime:"+crontime);

        //将号码、任务id传送给IVR
        String[] telephone=phones.split(",");
        JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类
        JobDetail jobDetail = newJob(QuartzTask.class).withIdentity(jobKey).build();

        JobDataMap jobDataMap=jobDetail.getJobDataMap();
        jobDataMap.put("telephones",telephone);
        jobDataMap.put("taskid",taskid);
        jobDataMap.put("flowid",flowid);
        jobDataMap.put("response",response);

        TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);// 触发器
        Trigger trigger = newTrigger().withIdentity(triggerKey)
                .withSchedule(cronSchedule(crontime)).build();// 触发器时间设定
        sched.scheduleJob(jobDetail, trigger);
        if (!sched.isShutdown()) {
            sched.start();// 启动
        }

    }
}
