package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CallFlowNode;
import com.quickdone.znwh.entity.CustomerCallFlow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: psf
 * @Date: 2018/7/14 10:59
 * @Description:
 */
@Repository
public interface CallFlowNodeRepository  extends BaseRepository<CallFlowNode,Long>{
    //wxn --根据电话和taskid查询数据
    public List<CallFlowNode> findByTelephoneAndTaskid(String telePhone,Integer taskId);
}
