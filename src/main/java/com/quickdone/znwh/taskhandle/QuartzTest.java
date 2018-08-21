package com.quickdone.znwh.taskhandle;

import com.quickdone.znwh.utils.CronUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: psf
 * @Date: 2018/7/13 16:28
 * @Description: 定时任务测试类
 */
public class QuartzTest {


    public  void test(){
        QuartzManager quartzManager=new QuartzManager();
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        Scheduler sche;

        {
            try {
                sche = gSchedulerFactory.getScheduler();
                String job_name = "动态任务调度";
                System.out.println("【系统启动】开始(每1秒输出一次)...");
                String clazz = "com.axujie.task.jobs.HelloWorld";
                String cron = CronUtil.getCron("2018-07-20 11:16:10");  //使用Class.forName动态的创建
                String currentnode="2";
                String phones="1888888,188880000,1873223233";
                Map<String,Object> params=new HashMap<String,Object>();
                quartzManager.generateScheduleTask(sche, job_name,cron,currentnode,"1",phones,params);
            } catch (SchedulerException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
        QuartzTest quartzTest=new QuartzTest();
        quartzTest.test();

    }




}
