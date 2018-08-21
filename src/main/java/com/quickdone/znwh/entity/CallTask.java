package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:37
 * @Description:外呼任务
 */

@Entity
@Table(name = "t_call_task")
public class CallTask implements Serializable {
    private static final long serialVersionUID = 2713957236372055729L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键

    @Column(name="name",columnDefinition="varchar(200) DEFAULT NULL COMMENT '任务名称'")
    private String name;

    @Column(name="type",nullable = false,columnDefinition="tinyint(5)  COMMENT '启用/禁用 0启用 1禁用'")
    private Integer type = 0;//0启用 1禁用

    @Column(name="is_delete",nullable = false,columnDefinition="tinyint(5)  COMMENT '0未删除 1删除'")
    private Integer isDelete = 0;

    @Column(name = "callCategory_Id", columnDefinition = "bigint(20) COMMENT '任务类型id'")
    private Long callCategoryId;//任务类型id

    @Column(name="callCategory_name",columnDefinition="varchar(200) DEFAULT NULL COMMENT '任务分类名称'")
    private String callCategoryName;

    @Column(name = "callContentSubject_Id", columnDefinition = "bigint(20) COMMENT '外呼主题外键id'")
    private Long callContentSubjectId;//外呼主题外键id

    @Column(name="callContentSubject_name",columnDefinition="varchar(200) DEFAULT NULL COMMENT '主题名称'")
    private String callContentSubjectName;

    @Column(name = "customerGroup_Id", columnDefinition = "bigint(20) COMMENT '客户组外键id'")
    private Long customerGroupId;//客户组外键

    @Column(name="customerGroup_name",columnDefinition="varchar(200) DEFAULT NULL COMMENT '客户组名称'")
    private String customerGroupName;

    @Column(name = "plan_type",nullable = false,columnDefinition = "tinyint(5)  COMMENT '计划类型： 一次性/周期性 0一次性 1周期性'")
    private Integer planType = 0;//计划类型

    @Temporal(TemporalType.DATE)
    @Column(name="execute_begin_date",columnDefinition="datetime COMMENT '执行开始日期'")
    private Date executeBeginDate;

    @Temporal(TemporalType.DATE)
    @Column(name="execute_end_date",columnDefinition="datetime COMMENT '执行结束日期'")
    private Date executeEndDate;

    @Column(name="execute_time",columnDefinition="datetime COMMENT '执行时间'")
    private Date executeTime;

    @Column(name = "callFlow_Id", columnDefinition = "bigint(20) COMMENT '外呼流程外键id'")
    private Long callFlowId;//外呼流程外键

    @Column(name="callFlow_name",columnDefinition="varchar(200) DEFAULT NULL COMMENT '流程名称'")
    private String callFlowName;

    @Column(name="callcj_number",columnDefinition="varchar(200) DEFAULT NULL COMMENT '拨号出局码'")
    private String callcjNumber;

    @Column(name = "try_count",nullable = false,columnDefinition = "tinyint(5)  COMMENT '失败尝试次数'")
    private Integer tryCount = 3;

    @Column(name="callNum_show",columnDefinition="varchar(200) DEFAULT NULL COMMENT '外呼显示号码'")
    private String callNumShow;

    @Column(name = "callNum_show_type",nullable = false,columnDefinition = "tinyint(5)  COMMENT '外呼显示号码类型 0：随机 1：统一号码'")
    private Integer callNumShowType = 0;

    @Column(name="descri",columnDefinition="varchar(200) DEFAULT NULL COMMENT '描述'")
    private String descri;

    @Column(name = "create_date",columnDefinition = "datetime COMMENT '创建日期'")
    private Date createDate;

    @Column(name="status",nullable = false,columnDefinition="tinyint(5)  COMMENT '任务进行状态 0待执行 1执行中 2执行完毕'")
    private Integer status = 0;

    @Column(name="basicNum",nullable = false,columnDefinition="tinyint(5)  COMMENT '外呼基数'")
    private Integer basicNum = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Date getExecuteBeginDate() {
        return executeBeginDate;
    }

    public void setExecuteBeginDate(Date executeBeginDate) {
        this.executeBeginDate = executeBeginDate;
    }

    public Date getExecuteEndDate() {
        return executeEndDate;
    }

    public void setExecuteEndDate(Date executeEndDate) {
        this.executeEndDate = executeEndDate;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getCallcjNumber() {
        return callcjNumber;
    }

    public void setCallcjNumber(String callcjNumber) {
        this.callcjNumber = callcjNumber;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getCallNumShow() {
        return callNumShow;
    }

    public void setCallNumShow(String callNumShow) {
        this.callNumShow = callNumShow;
    }

    public Integer getCallNumShowType() {
        return callNumShowType;
    }

    public void setCallNumShowType(Integer callNumShowType) {
        this.callNumShowType = callNumShowType;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getBasicNum() {
        return basicNum;
    }

    public void setBasicNum(Integer basicNum) {
        this.basicNum = basicNum;
    }

    public Long getCallCategoryId() {
        return callCategoryId;
    }

    public void setCallCategoryId(Long callCategoryId) {
        this.callCategoryId = callCategoryId;
    }

    public Long getCallContentSubjectId() {
        return callContentSubjectId;
    }

    public void setCallContentSubjectId(Long callContentSubjectId) {
        this.callContentSubjectId = callContentSubjectId;
    }

    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public Long getCallFlowId() {
        return callFlowId;
    }

    public void setCallFlowId(Long callFlowId) {
        this.callFlowId = callFlowId;
    }

    public String getCallCategoryName() {
        return callCategoryName;
    }

    public void setCallCategoryName(String callCategoryName) {
        this.callCategoryName = callCategoryName;
    }

    public String getCallContentSubjectName() {
        return callContentSubjectName;
    }

    public void setCallContentSubjectName(String callContentSubjectName) {
        this.callContentSubjectName = callContentSubjectName;
    }

    public String getCustomerGroupName() {
        return customerGroupName;
    }

    public void setCustomerGroupName(String customerGroupName) {
        this.customerGroupName = customerGroupName;
    }

    public String getCallFlowName() {
        return callFlowName;
    }

    public void setCallFlowName(String callFlowName) {
        this.callFlowName = callFlowName;
    }
}
