package com.quickdone.znwh.service.impl;

import com.quickdone.znwh.dao.CallFlowNodeRepository;
import com.quickdone.znwh.entity.CallFlowNode;
import com.quickdone.znwh.service.CallFlowNodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: psf
 * @Date: 2018/7/14 10:57
 * @Description:
 */
@Service
@Transactional
public class CallFlowNodeServiceImpl implements CallFlowNodeService<CallFlowNode,Long> {
    @Resource
    private CallFlowNodeRepository callFlowNodeRepository;

    @Override
    public CallFlowNode add(CallFlowNode callFlowNode) {
        return callFlowNodeRepository.save(callFlowNode);
    }

    @Override
    public List<CallFlowNode> findByTelephoneAndTaskid(String telePhone, Integer taskId) {
        return callFlowNodeRepository.findByTelephoneAndTaskid(telePhone,taskId);
    }
}
