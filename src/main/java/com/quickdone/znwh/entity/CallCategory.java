package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:12
 * @Description:任务类别
 */

@Entity
@Table(name = "t_call_category")
public class CallCategory implements Serializable {
    private static final long serialVersionUID = 6179311333361295039L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键

    @Column(columnDefinition = "varchar(200) DEFAULT NULL COMMENT '名称'")
    private String name;

    @Column(columnDefinition = "varchar(200) DEFAULT NULL COMMENT '描述'")
    private String descri;

    @Column(columnDefinition = "bigint COMMENT '父类id'")
    private Long parentId;

    @Column(name="is_delete",nullable = false,columnDefinition="tinyint(5)  COMMENT '0未删除 1删除'")
    private Integer isDelete = 0;

    @Column(name = "create_date",columnDefinition = "datetime COMMENT '创建日期'")
    private Date createDate;

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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
