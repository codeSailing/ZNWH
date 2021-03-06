package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:11
 * @Description: 外呼内容主题
 */

@Entity
@Table(name = "t_call_content_subject")
public class CallContentSubject implements Serializable {
    private static final long serialVersionUID = -4672425262351057074L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id;//主键

    @Column(name = "parent_id", columnDefinition = "bigint(20) COMMENT '父节点内容主题id'")
    private Long parentId; //父节点内容主题id

    @Column(name = "name", nullable = false, columnDefinition = "varchar(200) COMMENT '内容主题名称'")
    private String name; //

    @Column(name = "description", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '内容主题描述'")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
