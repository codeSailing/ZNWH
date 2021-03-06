package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/13 09:30
 * @Description: 每个客户的外呼流程记录,客户任务表
 */

@Entity
@Table(name = "t_customer_flowFlow")
public class CustomerCallFlow implements Serializable {
    private static final long serialVersionUID = 7246085137099477747L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id;
    private String telephone;
    private String taskid;
    private String flowid;
    private String flowNodeid;
    private String nodeContent;
    private String nodeAnswer;
    @Column(name="execute_time",columnDefinition="datetime COMMENT '执行时间'")
    private Date executetime;

    private int status;//执行状态 1、执行中 2、执行完毕 3、直接挂断

    private String result;//执行结果 1、正常挂断  2、忙线

    private long  callTime;//通话时长

    private String customer_tab;//客戶標簽

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public String getFlowNodeid() {
        return flowNodeid;
    }

    public void setFlowNodeid(String flowNodeid) {
        this.flowNodeid = flowNodeid;
    }

    public String getNodeContent() {
        return nodeContent;
    }

    public void setNodeContent(String nodeContent) {
        this.nodeContent = nodeContent;
    }

    public String getNodeAnswer() {
        return nodeAnswer;
    }

    public void setNodeAnswer(String nodeAnswer) {
        this.nodeAnswer = nodeAnswer;
    }

    public Date getExecutetime() {
        return executetime;
    }

    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
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

    public String getCustomer_tab() {
        return customer_tab;
    }

    public void setCustomer_tab(String customer_tab) {
        this.customer_tab = customer_tab;
    }
}
