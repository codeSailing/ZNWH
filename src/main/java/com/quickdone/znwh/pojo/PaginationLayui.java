package com.quickdone.znwh.pojo;

import java.io.Serializable;
import java.util.List;

//layui 插件专用
public class PaginationLayui<T> implements Serializable {
    private static final long serialVersionUID = -1797442067448688073L;
    private int start;
    private int length;

    private long recordsTotal;

    private List<T> data;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public static PaginationLayui formJQueryLayuiTableRequest(LayuiRequest dtReq) {
        PaginationLayui pagination = new PaginationLayui();
        pagination.setStart(dtReq.getPage());
        pagination.setLength(dtReq.getLimit());
        return pagination;
    }
}
