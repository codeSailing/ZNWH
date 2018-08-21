package com.quickdone.znwh.pojo;

import java.io.Serializable;

public class LayuiResponseMap implements Serializable {
    private static final long serialVersionUID = 7358416439136448833L;

    private int code = 0;
    private String msg = "";
    private long count;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static LayuiResponseMap fromPagination(PaginationMapLayui pagination) {
        LayuiResponseMap dtResp = new LayuiResponseMap();
        dtResp.setCode(0);
        dtResp.setMsg("");
        dtResp.setCount(pagination.getRecordsTotal());
        dtResp.setData(pagination.getData());
        return dtResp;
    }


}
