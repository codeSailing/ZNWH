package com.quickdone.znwh.pojo;

/**
 * @Author: zhum
 * @Date: 2018/8/22 8:39
 * @Description:
 */
public class IvrRequest {
    private String content;
    private String uid;
    private String sessionId;
    private String currentNode;
    private String callFlowId;
    private String taskName;
    private String phone;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getCallFlowId() {
        return callFlowId;
    }

    public void setCallFlowId(String callFlowId) {
        this.callFlowId = callFlowId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "IvrRequest{" +
                "content='" + content + '\'' +
                ", uid='" + uid + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", currentNode='" + currentNode + '\'' +
                ", callFlowId='" + callFlowId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
