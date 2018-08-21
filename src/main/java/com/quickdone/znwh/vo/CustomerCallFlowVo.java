package com.quickdone.znwh.vo;

import com.quickdone.znwh.entity.CallContent;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wxn
 * @Date: 2018/7/14 14:54
 * @Description: 外呼记录vo
 */
public class CustomerCallFlowVo implements Serializable {

    private static final long serialVersionUID = 2461795991540803290L;

    private Long id;

    private String theme;               //外呼主题

    private String name;                //客户姓名

    private  String  telephone;

    private  String taskid;

    @Temporal(TemporalType.TIME)
    private Date executetime;

    private String taskName;    //任务名称

    private String category;    //任务类型

    private int status;                 //执行状态 1、执行中 2、执行完毕

    private String result;              //执行结果 1、正常挂断  2、忙线

    private long  callTime;             //通话时长

    private String customer_tab;//客戶標簽

    public String getCustomer_tab() {
        return customer_tab;
    }

    public void setCustomer_tab(String customer_tab) {
        this.customer_tab = customer_tab;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getExecutetime() {
        return executetime;
    }

    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }
}
