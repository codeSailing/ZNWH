package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CustomerCallFlow;

import java.util.List;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 每个客户的外呼流程记录,客户任务
 */
public interface CustomerCallFlowRepository  extends BaseRepository<CustomerCallFlow,Long>{
    List<CustomerCallFlow> findByTelephone(String telephone);

    List<CustomerCallFlow> findByTaskid(String findByTaskid);

    List<CustomerCallFlow> findByTelephoneAndTaskid(String te,String taskid);
}
