package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:45
 * @Description:外呼记录
 */

@Entity
@Table(name = "t_call_record")
public class CallRecord implements Serializable {
    private static final long serialVersionUID = -674547159893114L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键


    private long callTaskid;

    @Column(name = "create_date",columnDefinition = "datetime COMMENT '创建日期'")
    private Date createDate;

    @Column(name = "execute_result",columnDefinition = "tinyint(5) COMMENT '执行结果'")
    private Integer executeResult;//TODO 待扩展字段

    @Column(name = "call_time",columnDefinition = "datetime  COMMENT '通话时长'")
    private Date callTime;

    @Column(columnDefinition = "tinyint(5) COMMENT '业务指标'")
    private Integer indicators = 0;//TODO 待扩展字段

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCallTaskid() {
        return callTaskid;
    }

    public void setCallTaskid(long callTaskid) {
        this.callTaskid = callTaskid;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(Integer executeResult) {
        this.executeResult = executeResult;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Integer getIndicators() {
        return indicators;
    }

    public void setIndicators(Integer indicators) {
        this.indicators = indicators;
    }
}
