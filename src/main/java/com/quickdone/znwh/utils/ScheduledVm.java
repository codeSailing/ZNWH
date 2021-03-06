package com.quickdone.znwh.utils;

/**
 * @Author: zhum
 * @Date: 2018/7/10 15:16
 * @Description:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author: zhum
 * @Date: 2018/7/10 15:33
 * @Description: 动态创建定时任务
 */
@Component
public class ScheduledVm {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    private int taskSchedulerCorePoolSize=50;

    static boolean isinitialized=false;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(taskSchedulerCorePoolSize);
        /**需要实例化线程*/
        threadPoolTaskScheduler.initialize();
        isinitialized=true;
        return threadPoolTaskScheduler;
    }

//    @Scheduled(fixedDelay = 3600000)
    public void getCron(String cron) {
        /**每次重新获取任务模板的时候重构定时任务*/
        /**设置为false,关闭线程池中的任务时,直接执行shutdownNow()*/
        try{
            if (isinitialized) {
                threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(false);
                threadPoolTaskScheduler.shutdown();
            }
        }catch (Exception e){

        }finally {
            threadPoolTaskScheduler();
        }
        threadScheduler(new Runnable() {
            @Override
            public void run() {
                //TODO 执行任务
                System.out.println("定时任务=======  1");
            }
        },"0/5 * * * * *");

        threadScheduler(new Runnable() {
            @Override
            public void run() {
                //TODO 执行任务
                System.out.println("定时任务=======  2");
            }
        },"0/3 * * * * *");
    }

    private ScheduledFuture<?> threadScheduler(Runnable runnable, final String cron){
        /**动态创建定时任务*/
        return future = threadPoolTaskScheduler.schedule(runnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            }
        });
    }

    public String stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("停止定时任务...");
        return "stopCron";
    }


//    private String getCrons(Date date,Integer period)
//    {
////        String ss="*";
////        String mm="*";
////        String hh="*";
//        String dd="*";
//        String MM="*";
//        String yy="*";
//        String []time=format.format(date).split("-");
//        dd=time[2];
//        MM=time[1];
//        yy=time[0];
//        /**每隔数月*/
//        if (period==1||period==2)
//        {
//            MM=MM+"/"+period;
//        }else if (period==7||period==14)
//        {
//            /**每隔数天*/
//            dd=dd+"/"+period;
//        }
//
//        /**每隔数月时,表示,在yyyy-MM-dd 8:30:00时执行一次,然后每隔MM月再执行
//         每隔数天时,表示,再yyyy-MM-dd 8:30:00时执行一次,然后每隔dd天再执行*/
//        return "0 30 8"+" "+dd+" "+MM+" ?"+yy;
//    }

    public static void main(String[] args){

    }
}


