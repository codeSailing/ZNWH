package com.quickdone.znwh.service.impl;

import com.quickdone.znwh.common.Constants;
import com.quickdone.znwh.dao.CallTaskRepository;
import com.quickdone.znwh.dao.CustomerGroupRepository;
import com.quickdone.znwh.entity.CallContent;
import com.quickdone.znwh.entity.CallTask;
import com.quickdone.znwh.entity.CustomerGroup;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CustomerGroupService;
import com.quickdone.znwh.utils.ToClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/10
 * @Description:
 */
@Service
@Transactional
public class CustomerGroupServiceImpl implements CustomerGroupService<CustomerGroup, Long> {

    @Resource
    private CustomerGroupRepository customerGroupRepository;
    @Resource
    private CallTaskRepository callTaskRepository;

    @Override
    public CustomerGroup findById(Long id) {
        return customerGroupRepository.findById(id);
    }

    @Override
    public List<CustomerGroup> findAll() {
        return customerGroupRepository.findAll();
    }

    @Override
    public CustomerGroup addCustomerGroup(CustomerGroup customerGroup) {
        customerGroup.setAddTime(new Date());
        return customerGroupRepository.save(customerGroup);
    }

    @Override
    public CustomerGroup updCustomerGroup(CustomerGroup customerGroup) {
        CustomerGroup customerGroupFind = customerGroupRepository.findById(customerGroup.getId());
        customerGroup.setAddTime(customerGroupFind.getAddTime());
        List<CallTask> callTaskList= callTaskRepository.findByCustomerGroupId(customerGroup.getId());
        for (CallTask callTask:callTaskList){
            callTask.setCustomerGroupName(customerGroup.getName());
            callTaskRepository.save(callTask);
        }
        return customerGroupRepository.save(customerGroup);
    }

    @Override
    public ResponseData delCustomerGroup(CustomerGroup customerGroup) {
        List<CallTask> callTaskList= callTaskRepository.findByCustomerGroupId(customerGroup.getId());
        for (CallTask callTask:callTaskList){
            if (callTask.getStatus()==Constants.callTaskStatus.zxz){
                return ResponseData.getErrorResponse("["+callTask.getName()+"]任务未执行完毕,["+customerGroup.getName()+"]客户组不能删除");
            }else {
                //删除对应的任务(未执行和已完成)
                callTaskRepository.delete(callTask.getId());
            }
        }
        customerGroupRepository.delete(customerGroup);
        return ResponseData.getSuccessResponse("删除成功");
    }

    @Override
    public List<CustomerGroup> findByGroupId(Long groupId) {
        return customerGroupRepository.findByGroupId(groupId);
    }

    @Override
    public void pageCustomerGroup(final Map<String, Object> searchParams, PaginationMapLayui pagination) {
        Pageable pageable = new PageRequest(pagination.getStart(), pagination.getLength(), new Sort(Sort.Direction.DESC, "addTime"));
        Page<CustomerGroup> page = customerGroupRepository.findAll(new Specification<CustomerGroup>() {
            @Override
            public Predicate toPredicate(Root<CustomerGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<String> name = root.get("name");
                if (searchParams.containsKey("searchParam")) {
                    Predicate p = cb.like(name, "%" + searchParams.get("searchParam") + "%");
                    predicates.add(p);
                }
                Path<Long> id = root.get("id");
                if (searchParams.containsKey("groupId")) {
                    Long groupId = Long.valueOf(searchParams.get("groupId").toString());
                    List<Long> returnList = new ArrayList<Long>();
                    List<Long> subjectIds = findChildGroups(returnList, groupId);
                    CriteriaBuilder.In<Long> in = cb.in(id);
                    for (Long subjectId : subjectIds) {
                        in.value(subjectId);
                    }
                    predicates.add(in);
                    //Predicate p = cb.equal(id, searchParams.get("groupId"));
                    //predicates.add(p);
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, pageable);
        List<CustomerGroup> contentList = page.getContent();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (CustomerGroup customerGroup : contentList) {
            Map<String, Object> map = ToClass.beanToMap(customerGroup);
            mapList.add(map);
        }
        pagination.setData(mapList);
        pagination.setRecordsTotal(page.getTotalElements());
    }

    /**
     * 递归查询
     *
     * @auther: Hou-jun
     * @date: 2018/7/14
     */
    public List<Long> findChildGroups(List<Long> list, Long groupId) {
        list.add(groupId);
        List<CustomerGroup> contentSubjectList = customerGroupRepository.findByGroupId(groupId);
        for (CustomerGroup customerGroup : contentSubjectList) {
            findChildGroups(list, customerGroup.getId());
        }
        return list;
    }
}
