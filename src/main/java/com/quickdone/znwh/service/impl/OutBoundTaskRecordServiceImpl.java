package com.quickdone.znwh.service.impl;


import com.quickdone.znwh.common.Constants;
import com.quickdone.znwh.dao.OutBoundTaskRecordRepository;
import com.quickdone.znwh.entity.OutBoundTaskRecord;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.OutBoundTaskRecordService;
import com.quickdone.znwh.utils.ToClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ly
 * @Date: 2018/8/23
 * @Description:
 */
@Service
@Transactional
public class OutBoundTaskRecordServiceImpl implements OutBoundTaskRecordService<OutBoundTaskRecord, Long> {
    @Resource
    private OutBoundTaskRecordRepository outBoundTaskRecordRepository;

    @Override
    public void findAll(final Map<String, Object> searchParams, PaginationMapLayui pagination) {
        Pageable pageable = new PageRequest(pagination.getStart(), pagination.getLength(), new Sort(Sort.Direction.DESC, "beginDate"));//根据时间倒叙
        Page<OutBoundTaskRecord> page = outBoundTaskRecordRepository.findAll(new Specification<OutBoundTaskRecord>() {
            @Override
            public Predicate toPredicate(Root<OutBoundTaskRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if(searchParams.containsKey("taskName")){//名称
                    Predicate p1 = criteriaBuilder.like(root.<String>get("taskName"),"%"+searchParams.get("taskName").toString()+"%");
                    predicateList.add(p1);
                }
                if(searchParams.containsKey("status")){//名称
                    Predicate p2 = criteriaBuilder.equal(root.<String>get("status"),Integer.parseInt(searchParams.get("status").toString()));
                    predicateList.add(p2);
                }
                Predicate[] p = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(p));
            }
        },pageable);
        List<OutBoundTaskRecord> list = page.getContent();
        List<Map> resuList = new ArrayList<Map>();
        for (OutBoundTaskRecord outBoundTaskRecord : list) {
            Map map = ToClass.beanToMap(outBoundTaskRecord);
            resuList.add(map);
        }
        pagination.setData(resuList);
        pagination.setRecordsTotal(page.getTotalElements());

    }

    @Override
    public OutBoundTaskRecord addOutBoundTaskRecord(String sessionId,String callFlowId ,String taskName,String phone) {
        OutBoundTaskRecord outBoundTask=outBoundTaskRecordRepository.findBySessionId(sessionId);
        if(outBoundTask!=null){
            outBoundTask.setUpdateDate(new Date());
            return outBoundTaskRecordRepository.save(outBoundTask);
        }else{
            OutBoundTaskRecord outBoundTaskRecord=new OutBoundTaskRecord();
            outBoundTaskRecord.setSessionId(sessionId);
            outBoundTaskRecord.setCallFlowId(callFlowId);
            outBoundTaskRecord.setTaskName(taskName);
            outBoundTaskRecord.setPhone(phone);
            outBoundTaskRecord.setStatus(Constants.RecordStatus.JIETONG);
            outBoundTaskRecord.setBeginDate(new Date());
            outBoundTaskRecord.setUpdateDate(new Date());
            outBoundTaskRecord.setCustomerLabel("");//客户标签
            return outBoundTaskRecordRepository.save(outBoundTaskRecord);
        }
    }

    @Override
    public OutBoundTaskRecord findById(Long id) {
        return outBoundTaskRecordRepository.findById(id);
    }

    @Override
    public OutBoundTaskRecord updateOutBoundTaskRecord(OutBoundTaskRecord outBoundTaskRecord) {
        return outBoundTaskRecordRepository.save(outBoundTaskRecord);
    }
}
