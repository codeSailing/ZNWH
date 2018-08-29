package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: zhum
 * @Date: 2018/8/23 10:03
 * @Description: 任务客户外呼记录表
 */
@Entity
@Table(name = "t_outBoundTaskRecord")
public class OutBoundTaskRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键

    @Column(name="callFlow_id",columnDefinition="varchar(200) DEFAULT NULL COMMENT '流程id'")
    private String callFlowId;//流程id

    @Column(name="name",columnDefinition="varchar(200) DEFAULT NULL COMMENT '任务名称'")
    private String taskName;//任务名称

    @Column(name = "status",nullable = false,columnDefinition = "tinyint(5)  COMMENT '状态'")
    private Integer status;//状态 0接通 1挂断

    @Column(name="phone",columnDefinition="varchar(200) DEFAULT NULL COMMENT '客户号码'")
    private String phone;//客户号码

    @Column(name="customer_label",columnDefinition="varchar(200) DEFAULT NULL COMMENT '客户标签'")
    private String customerLabel;//客户标签

    @Column(name = "create_date",columnDefinition = "datetime COMMENT '通话开始时间,精确到时分秒'")
    private Date beginDate;//通话开始时间

    @Column(name = "update_date",columnDefinition = "datetime COMMENT '通话更新时间,精确到时分秒'")
    private Date updateDate;//通话更新时间，如果是最后一次更新时间则为通话结束时间

    @Column(name="session_id",columnDefinition="varchar(200) DEFAULT NULL COMMENT '会话唯一id'")
    private String sessionId;//会话唯一id

    public String getCallFlowId() {
        return callFlowId;
    }

    public void setCallFlowId(String callFlowId) {
        this.callFlowId = callFlowId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCustomerLabel() {
        return customerLabel;
    }

    public void setCustomerLabel(String customerLabel) {
        this.customerLabel = customerLabel;
    }
}
