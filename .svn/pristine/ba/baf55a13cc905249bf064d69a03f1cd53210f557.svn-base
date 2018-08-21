package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 20:10
 * @Description:区域信息表
 */
@Entity
@Table(name = "t_area")
public class Area implements Serializable {
    private static final long serialVersionUID = 5262336439153737653L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键

    @Column(name = "AREA_NAME",length = 100,nullable = false)
    private String areaName;//地区名称

    @Column(name = "AREA_COMMENT")
    private String areaComment;//地区描述

    @Column(name = "AREA_CODE",length = 50)
    private String areaCode;//城市编码

    @Column(name = "ADD_DATE")
    private Date addDate;//第一次创建记录的时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaComment() {
        return areaComment;
    }

    public void setAreaComment(String areaComment) {
        this.areaComment = areaComment;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
