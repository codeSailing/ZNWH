package com.quickdone.znwh.service.impl;


import com.quickdone.znwh.dao.OutBoundTaskInfoRepository;
import com.quickdone.znwh.entity.OutBoundTaskInfo;
import com.quickdone.znwh.entity.OutBoundTaskRecord;
import com.quickdone.znwh.service.OutBoundTaskInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: ly
 * @Date: 2018/8/23
 * @Description:
 */
@Service
@Transactional
public class OutBoundTaskInfoServiceImpl implements OutBoundTaskInfoService<OutBoundTaskInfo, Long> {
    @Resource
    private OutBoundTaskInfoRepository outBoundTaskInfoRepository;

    @Override
    public OutBoundTaskInfo addOutBoundTaskInfo(OutBoundTaskRecord outBoundTaskRecord, String info) {
        OutBoundTaskInfo outBoundTaskInfo=new OutBoundTaskInfo();
        outBoundTaskInfo.setCreateTime(new Date());
        outBoundTaskInfo.setOutBoundTaskRecord(outBoundTaskRecord);
        outBoundTaskInfo.setInfo(info);
        return outBoundTaskInfoRepository.save(outBoundTaskInfo);
    }

    @Override
    public OutBoundTaskInfo findById(Long id) {
        return outBoundTaskInfoRepository.findById(id);
    }

    @Override
    public List<OutBoundTaskInfo> getOutBoundTaskInfo(Long recordId) {
        return outBoundTaskInfoRepository.findByOutBoundTaskRecord_id(recordId);
    }
}
