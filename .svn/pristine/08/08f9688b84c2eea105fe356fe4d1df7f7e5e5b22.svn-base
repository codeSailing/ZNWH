package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: psf
 * @Date: 2018/7/9 16:44
 * @Description:外呼流程节点
 */

@Entity
@Table(name = "t_callflow_node")
public class CallFlowNode implements Serializable {
    private static final long serialVersionUID = -9191837339225226467L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,columnDefinition="bigint(20) COMMENT '主键'")
    private Long id;//主键


    @Column(columnDefinition="bigint(10)   COMMENT '流程id'")
    private int flowid;

    @Column(columnDefinition="bigint(10)   COMMENT '任务id'")
    private int taskid;

    @Column(columnDefinition="bigint(10)   COMMENT '状态'")
    private int status;//  1 执行正常  3 用户挂断

    @Column(columnDefinition="bigint(10)   COMMENT '下一环节'")
    private int nextNodeid;

    @Column(columnDefinition="bigint(10)   COMMENT '上一环节id'")
    private int parentNodeid;

    @Column(columnDefinition="varchar(1000) DEFAULT NULL COMMENT '当前环节的内容'")
    private String content;

    @Column(columnDefinition="varchar(1000)   COMMENT '当前节点的标准回答句式'")
    private String answer;


    @Column(columnDefinition="varchar(11)   COMMENT '客户电话'")
    private String telephone;


    @Column(columnDefinition="varchar(11)   COMMENT '当前环节的节点id'")
    private String currentNodeid;

    private int execute_time;

    public int getFlowid() {
        return flowid;
    }

    public void setFlowid(int flowid) {
        this.flowid = flowid;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNextNodeid() {
        return nextNodeid;
    }

    public void setNextNodeid(int nextNodeid) {
        this.nextNodeid = nextNodeid;
    }

    public int getParentNodeid() {
        return parentNodeid;
    }

    public void setParentNodeid(int parentNodeid) {
        this.parentNodeid = parentNodeid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getExecute_time() {
        return execute_time;
    }

    public void setExecute_time(int execute_time) {
        this.execute_time = execute_time;
    }

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

    public String getCurrentNodeid() {
        return currentNodeid;
    }

    public void setCurrentNodeid(String currentNodeid) {
        this.currentNodeid = currentNodeid;
    }


}
