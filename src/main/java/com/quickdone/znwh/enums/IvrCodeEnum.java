package com.quickdone.znwh.enums;

/**
 * @Author: zhum
 * @Date: 2018/8/23 9:31
 * @Description:
 */
public enum IvrCodeEnum {

    NORMAL("正常","0000"),
    HANGUP("挂断","1000"),
    ParameterError("参数错误","1001"),
    PROCESSNOTEXIST("流程不存在","1002"),
    CODEEXCEPTION("代码异常","1003");


    String value;
    String name;
    private IvrCodeEnum(String name, String value) {
        this.value=value;
        this.name=name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
