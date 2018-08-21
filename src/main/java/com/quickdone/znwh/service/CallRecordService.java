package com.quickdone.znwh.service;

import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.Map;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 外呼记录
 */
public interface CallRecordService<CallRecord,Long> {
    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 添加
     */
    public CallRecord addCallRecord(CallRecord callRecord);

    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 删除
     */
    public ResponseData delCallRecord(String callRecordIds);

    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 分页查询
     */
    public void findAll(Map<String,Object> searchParams, PaginationMapLayui pagination);
}
