package com.quickdone.znwh.enums;

/**
 * Created by psf on 2017/10/22.
 */
public enum TestEnum {
    IMAGE("图片","1"),TEXT("文本","2"),AUDIO("音频","3"),VEDIO("视频","4");
    String name;
    String value;
    private TestEnum(String name, String value) {
        this.value=value;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void main(String args[]){
        TestEnum myenum=TestEnum.TEXT;
        System.out.println("枚举实例的值====="+TestEnum.TEXT);
        System.out.println("枚举实例的值====="+myenum.getValue()+"    "+myenum.getName());
        System.out.println("枚举实例内置方法====="+myenum.name());

        System.out.println("枚举实例内置方法====="+myenum.ordinal());
        System.out.println("枚举实例内置方法====="+myenum.toString());

    }
}
