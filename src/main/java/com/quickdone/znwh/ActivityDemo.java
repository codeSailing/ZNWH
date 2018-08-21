package com.quickdone.znwh;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by psf on 2018/7/3.
 *
 * 创建流程引擎
 * 发布流程
 * 启动流程
 * 完成任务，执行流程
 * 流程完毕
 */
public class ActivityDemo {
    private static Logger logger = LoggerFactory.getLogger(ActivityDemo.class);
    private static ProcessEngine processEngine;

    /**
     * 根据配置文件创建processEngine
     */

    public void createProcessEngine(){
         processEngine = ProcessEngines.getDefaultProcessEngine();
//        ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("");
//        processEngine=processEngineConfiguration.buildProcessEngine();
    }

    /**
     *流程发布
     */

    public void deploymentProcessDefinition(){
       // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService().createDeployment().name("hello word activiti!")
                .addClasspathResource("diagrams/helloword.bpmn").deploy();

        System.out.println("部署完成===id:" + deployment.getId() + "  名称：" + deployment.getName());
    }

    /**
     * 流程启动
     */
    public void  startProcessInstance(){
        //ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("helloword");
        System.out.println("流程启动完成===id:" + pi.getId() + "  名称：" + pi.getName());
    }

    /**
     * 查询当前个人任务
     */

    /**
     * 产生RepositoryService,管理流程定义
     */
    public void createProcessService(){
        RepositoryService repositoryService =processEngine.getRepositoryService();

    }

    /**
     *  RuntimeService 执行管理，包括启动，推进，删除流程实例等操作
     */
    public  void createRunTimeService(){
        RuntimeService runtimeService =processEngine.getRuntimeService();
    }

    /**
     * TaskService  任务管理
     */
    public  void  createTaskService(){
        TaskService taskService =processEngine.getTaskService();
    }



    public static void  main(String  args[]){
        ActivityDemo activityDemo=new ActivityDemo();
        activityDemo.createProcessEngine();
        activityDemo. deploymentProcessDefinition();
        activityDemo.startProcessInstance();
    }
}
