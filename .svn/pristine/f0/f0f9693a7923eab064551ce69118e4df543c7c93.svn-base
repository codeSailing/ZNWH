package com.quickdone.znwh.service;

import com.quickdone.znwh.pojo.PaginationMapLayui;

import java.util.Map;

/**
 * @Author: ly
 * @Date: 2018/8/23
 * @Description: 任务客户外呼记录
 */
public interface OutBoundTaskRecordService<OutBoundTaskRecord,Long> {

    /**
     * 分页查询
     * @param searchParams
     * @param pagination
     */
    public void findAll(Map<String, Object> searchParams, PaginationMapLayui pagination);

    /**
     * 添加
     * @Author: ly
     * @Date: 2018/8/23
     */
    public OutBoundTaskRecord addOutBoundTaskRecord(String sessionId,String callFlowId ,String taskName,String phone);

    /**
     * 根据id查询
     * @Author: ly
     * @Date: 2018/8/23
     */
    public OutBoundTaskRecord findById(Long id);

    /**
     * 修改
     * @Author: ly
     * @Date: 2018/8/23
     */
    public OutBoundTaskRecord updateOutBoundTaskRecord(OutBoundTaskRecord outBoundTaskRecord);

}
