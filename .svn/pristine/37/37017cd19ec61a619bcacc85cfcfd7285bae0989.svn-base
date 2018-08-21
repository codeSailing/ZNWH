package com.quickdone.znwh.service.impl;

import com.quickdone.znwh.dao.CallRecordRepository;
import com.quickdone.znwh.entity.CallRecord;
import com.quickdone.znwh.entity.CallTask;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CallRecordService;
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
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 外呼记录
 */
@Service
@Transactional
public class CallRecordServiceImpl implements CallRecordService<CallRecord,Long> {
    @Resource
    private CallRecordRepository callRecordRepository;

    @Override
    public CallRecord addCallRecord(CallRecord callRecord) {
        callRecord.setCreateDate(new Date());
        return callRecordRepository.save(callRecord);
    }

    @Override
    public ResponseData delCallRecord(String callRecordIds) {
        String[] ids = callRecordIds.split(",");
        for (String callRecordId : ids) {
            callRecordRepository.delete(Long.valueOf(callRecordId));
        }
        return ResponseData.getSuccessResponse("删除成功！");
    }

    @Override
    public void findAll(final Map<String, Object> searchParams, PaginationMapLayui pagination) {
        Pageable pageable = new PageRequest(pagination.getStart(), pagination.getLength(), new Sort(Sort.Direction.DESC, "createDate"));//根据时间倒叙
        Page<CallRecord> page = callRecordRepository.findAll(new Specification<CallRecord>() {
            @Override
            public Predicate toPredicate(Root<CallRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if(searchParams.containsKey("name")){//任务名称
                    Predicate p1 = criteriaBuilder.like(root.<CallTask>get("callTask").<String>get("name"),"%"+searchParams.get("name").toString()+"%");
                    predicateList.add(p1);
                }
                if(searchParams.containsKey("executeResult")){//执行结果
                    Predicate p2 = criteriaBuilder.equal(root.<Integer>get("executeResult"),Integer.parseInt(searchParams.get("executeResult").toString()));
                    predicateList.add(p2);
                }
                Predicate[] p = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(p));
            }
        },pageable);
        List<CallRecord> list = page.getContent();
        List<Map> resuList = new ArrayList<Map>();
        for (CallRecord callRecord : list) {
            Map map = ToClass.beanToMap(callRecord);
            resuList.add(map);
        }
        pagination.setData(resuList);
        pagination.setRecordsTotal(page.getTotalElements());
    }
}
