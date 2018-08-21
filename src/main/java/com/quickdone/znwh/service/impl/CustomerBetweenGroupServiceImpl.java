package com.quickdone.znwh.service.impl;

import com.mysql.jdbc.StringUtils;
import com.quickdone.znwh.dao.CustomerBetweenGroupRepository;
import com.quickdone.znwh.entity.Customer;
import com.quickdone.znwh.entity.CustomerBetweenGroup;
import com.quickdone.znwh.entity.CustomerGroup;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CustomerBetweenGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/11
 * @Description: 客户表 客户组表关联service实现
 */
@Service
@Transactional
public class CustomerBetweenGroupServiceImpl implements CustomerBetweenGroupService<CustomerBetweenGroup,Long> {

    @Resource
    private CustomerBetweenGroupRepository customerBetweenGroupRepository;

    @Override
    public CustomerBetweenGroup addCustomerGroup(Long customerId,Long customerGroupId){
        CustomerBetweenGroup customerBetweenGroup=new CustomerBetweenGroup();
        customerBetweenGroup.setCustomerId(customerId);
        customerBetweenGroup.setCustomerGroupId(customerGroupId);
        return customerBetweenGroupRepository.save(customerBetweenGroup);
    }

    @Override
    public List<CustomerBetweenGroup> findByCustomerId(Long customerId){
        return customerBetweenGroupRepository.findByCustomerId(customerId);
    }

    @Override
    public List<CustomerBetweenGroup> findByCustomerGroupId(Long customerGroupId){
        return customerBetweenGroupRepository.findByCustomerGroupId(customerGroupId);
    }

    @Override
    public void deleteByCustomerId(Long customerId){
        customerBetweenGroupRepository.deleteByCustomerId(customerId);
    }

    @Override
    public ResponseData dataGrouping(String customerId,String cusGroupId){
        if (!StringUtils.isNullOrEmpty(customerId)){
            String[] customerIds = customerId.split(",");
            for (String cusId : customerIds) {
                customerBetweenGroupRepository.deleteByCustomerId(Long.valueOf(cusId));
                if (!StringUtils.isNullOrEmpty(cusGroupId)){
                    String[] customerGroups = cusGroupId.split(",");
                    for (String groId:customerGroups) {
                        addCustomerGroup(Long.valueOf(cusId),Long.valueOf(groId));
                    }
                }else {
                    return ResponseData.getErrorResponse("请选择需要的分组");
                }
            }
            return ResponseData.getSuccessResponse("操作完成");
        }else {
            return ResponseData.getErrorResponse("请选择需要分组的客户");
        }
    }
}
