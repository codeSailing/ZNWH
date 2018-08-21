package com.quickdone.znwh.service;

import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 每个客户的外呼流程记录,客户任务
 */
public interface CustomerCallFlowService<CustomerCallFlow,Long> {
    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 添加
     */
    public CustomerCallFlow addCustomerCallFlow(CustomerCallFlow customerCallFlow);

    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 删除
     */
    public ResponseData delCustomerCallFlow(String customerCallFlowIds);

    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 分页查询
     */
    public void findAll(Map<String, Object> searchParams, PaginationMapLayui pagination);

    //根据电话号码查询 CustomerCallFlow
    public List<CustomerCallFlow> findBytelephoneAndtaskid(String telephone,String taskid);

    //根据电话号码查询 CustomerCallFlow
    public List<CustomerCallFlow> findByTaskid(String findByTaskid);

}
