package com.quickdone.znwh.enums;

/**
 * @Auther: psf
 * @Date: 2018/7/11 11:05
 * @Description:
 */
public enum IvrEnum {
    CONNECT("电话接通","1"),DISCINNECT("接通失败","2"),HANGUP("挂断","3"),NORMAL("正常","4"),COMPLETE("拨号完毕","5");
    private IvrEnum(String name, String value) {
        this.value=value;
        this.name=name;
    }

    String value;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
