package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:09
 * @Description: 客户组表
 */
@Entity
@Table(name = "t_customerGroup")
public class CustomerGroup implements Serializable {
    private static final long serialVersionUID = -6428370320561962250L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id;

    /**
     * 组Id 最顶层groupId=null 下级则为上级的主键
     */
    @Column(columnDefinition = "bigint(20)  COMMENT '组Id 最顶层groupId=null 下级则为上级的主键'")
    private Long groupId;

    /**
     * 分组名称
     */
    @Column(nullable = false, columnDefinition = "varchar(50)  COMMENT '分组名称'")
    private String name;

    /**
     * 描述
     */
    @Column(columnDefinition = "varchar(255) COMMENT '描述'")
    private String descri;

    /**
     * 添加时间
     */
    @Column(name = "add_time", columnDefinition = "datetime COMMENT '添加时间'")
    private Date addTime; //添加时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
