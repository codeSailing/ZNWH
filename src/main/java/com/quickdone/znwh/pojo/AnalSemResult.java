package com.quickdone.znwh.pojo;

/**
 * Created by Administrator on 2018/2/2.
 * 解析语义结果的实体bean
 */
public class AnalSemResult {
    //讯飞知识库记录标准问的id
    private String id;
    //语义返回的得分
    private double score;
    //语义返回的标准问的名称
    private String questionName;
    //判断语义的蛀齿状态
    private String rc;

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
