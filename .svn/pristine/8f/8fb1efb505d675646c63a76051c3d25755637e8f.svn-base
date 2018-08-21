package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:03
 * @Description: 客户表
 */

@Entity
@Table(name = "t_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = -5933138318301683324L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id;


    /**
     * 手机号
     */
    @Column(nullable = false, columnDefinition = "varchar(11)  COMMENT '手机号'")
    private String telephone;

    /**
     * 姓名
     */
    @Column(nullable = false, columnDefinition = "varchar(20)  COMMENT '姓名'")
    private String name;

    /**
     * 性别 0:男 1:女
     */
    @Column(columnDefinition = "tinyint(1)  COMMENT '性别 0:男 1:女'")
    private Integer sex;

    /**
     * 年龄
     */
    @Column(columnDefinition = "tinyint(3)  COMMENT '年龄'")
    private Integer age;

    /**
     * 省市区code
     */
    @Column(columnDefinition = "varchar(20)  COMMENT '省市区code'")
    private String areaCode;

    /**
     * 省市区名
     */
    @Column(columnDefinition = "varchar(50)  COMMENT '省市区名'")
    private String areaName;

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

    /**
     * 用户组id
     */
    private String customerGroupId;

    /**
     * 用户组名称
     */
    @Transient
    private String groupName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }
}
