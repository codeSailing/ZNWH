package com.quickdone.znwh.pojo;

import java.io.Serializable;

//layui 插件专用
public class PaginationMapLayui implements Serializable {
    private static final long serialVersionUID = -1797442067448688073L;
    private int start;
    private int length;
    private int startNumber;//从哪一行开始
    private int endNumber; //从哪一行结束

    public int getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(int endNumber) {
        this.endNumber = endNumber;
    }

    private long recordsTotal;

    private Object data;


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

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public static PaginationMapLayui formJQueryLayuiTableRequest(LayuiRequest dtReq) {
        PaginationMapLayui pagination = new PaginationMapLayui();
        pagination.setStart(dtReq.getPage());
        pagination.setLength(dtReq.getLimit());
        pagination.setStartNumber(dtReq.getPage() * dtReq.getLimit());
        pagination.setEndNumber(dtReq.getPage() * dtReq.getLimit()+dtReq.getLimit());
        return pagination;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
