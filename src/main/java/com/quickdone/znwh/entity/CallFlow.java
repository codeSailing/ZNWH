package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:39
 * @Description:外呼流程
 */

@Entity
@Table(name = "t_callflow")
public class CallFlow implements Serializable {
    private static final long serialVersionUID = -4427927857486700896L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键

    @Column(columnDefinition="varchar(200) DEFAULT NULL COMMENT '名称'")
    private String name;

    @Column(columnDefinition="varchar(200) DEFAULT NULL COMMENT '描述'")
    private String descri;

    @Column(nullable = true,columnDefinition="bigint(20) COMMENT '更新人员id'")
    private Long updateUser;

    @Column(name = "update_date",columnDefinition = "datetime COMMENT '更新日期'")
    private Date updateTime;

    @Column(name = "create_date",columnDefinition = "datetime COMMENT '创建日期'")
    private Date createDate;

    @Column(name="is_delete",nullable = false,columnDefinition="tinyint(5)  COMMENT '0未删除 1删除'")
    private Integer isDelete = 0;

    @Column(name = "resource_path",columnDefinition="varchar(200) DEFAULT NULL COMMENT 'xml文件路径'")
    private String resourcePath;

    @Column(name = "callFlow_info",columnDefinition="varchar(21000) COMMENT '流程配置信息'")
    private String callFlowInfo;

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

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getCallFlowInfo() {
        return callFlowInfo;
    }

    public void setCallFlowInfo(String callFlowInfo) {
        this.callFlowInfo = callFlowInfo;
    }
}
