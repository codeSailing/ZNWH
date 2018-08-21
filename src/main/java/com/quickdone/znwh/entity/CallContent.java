package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:10
 * @Description:外呼内容
 */

@Entity
@Table(name = "t_call_content")
public class CallContent implements Serializable {
    private static final long serialVersionUID = -3965480913107751020L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id; //主键

    @Column(name = "title", columnDefinition = "varchar(255) COMMENT '内容标题'")
    private String title; //内容标题

    @Column(name = "content_type", columnDefinition = "tinyint(1) COMMENT '0:文本  1:音频'")
    private Integer contentType; //0:文本  1:音频

    @Column(name = "detail", columnDefinition = "varchar(1000) COMMENT '内容详情'")
    private String detail; //内容详情

    @Column(name = "content", columnDefinition = "varchar(1000) COMMENT '内容描述'")
    private String content; //内容描述

    @Column(name = "content_subject_id", columnDefinition = "bigint(20) COMMENT '内容主题id'")
    private Long contentSubjectId; //关联内容主题表id

    @Column(name = "content_subject_name", columnDefinition = "varchar(200) COMMENT '内容主题名称'")
    private String contentSubjectName; //内容主题名称

    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    private Date updateTime; //更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getContentSubjectId() {
        return contentSubjectId;
    }

    public void setContentSubjectId(Long contentSubjectId) {
        this.contentSubjectId = contentSubjectId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContentSubjectName() {
        return contentSubjectName;
    }

    public void setContentSubjectName(String contentSubjectName) {
        this.contentSubjectName = contentSubjectName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }
}
