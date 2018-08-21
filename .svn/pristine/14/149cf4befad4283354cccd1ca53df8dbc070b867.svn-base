package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.CallFlowNode;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * @Auther: psf
 * @Date: 2018/7/14 10:51
 * @Description:
 */
public interface CallFlowNodeService<CallFlowNode,Long> {

    //添加流程节点
    CallFlowNode add( CallFlowNode callFlowNode);

    //wxn --根据电话和taskid查询数据
    public List<com.quickdone.znwh.entity.CallFlowNode> findByTelephoneAndTaskid(String telePhone, Integer taskId);

}
