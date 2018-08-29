package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: zhum
 * @Date: 2018/8/23 10:20
 * @Description: 外呼内容记录
 */
@Entity
@Table(name = "t_outBoundTaskInfo")
public class OutBoundTaskInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="outBoundTaskRecord_id",nullable = false,columnDefinition = "bigint(20) COMMENT '外键关联  任务客户外呼记录表'")
    private OutBoundTaskRecord outBoundTaskRecord;

    @Column(name="info",columnDefinition="varchar(255) DEFAULT NULL COMMENT '对话信息'")
    private String info;//对话信息（包含坐席说的话及客户说的话）

    @Column(name = "create_date",columnDefinition = "datetime COMMENT '创建时间,精确到时分秒'")
    private Date createTime;//记录创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OutBoundTaskRecord getOutBoundTaskRecord() {
        return outBoundTaskRecord;
    }

    public void setOutBoundTaskRecord(OutBoundTaskRecord outBoundTaskRecord) {
        this.outBoundTaskRecord = outBoundTaskRecord;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
