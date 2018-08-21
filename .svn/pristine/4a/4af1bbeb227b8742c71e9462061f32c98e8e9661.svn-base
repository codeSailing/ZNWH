package com.quickdone.znwh.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by psf on 2017/11/18.
 */
public class LayuiResponse<E extends Serializable> implements Serializable {
    private static final long serialVersionUID = 4417713984320688707L;
    private int code = 0;
    private String msg = "";
    private long count;
    private List data;

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

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public static <T extends Serializable> LayuiResponse<T> fromPagination(PaginationLayui<T> pagination) {
        LayuiResponse<T> dtResp = new LayuiResponse<T>();
        dtResp.setCode(0);
        dtResp.setMsg("");
        dtResp.setCount(pagination.getRecordsTotal());
        dtResp.setData(pagination.getData());
        return dtResp;
    }


}
