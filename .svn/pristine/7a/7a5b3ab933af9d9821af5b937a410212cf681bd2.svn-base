package com.quickdone.znwh.dao;


import com.quickdone.znwh.entity.Customer;
import com.quickdone.znwh.entity.CustomerBetweenGroup;
import com.quickdone.znwh.entity.CustomerGroup;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/11
 * @Description:
 */
public interface CustomerBetweenGroupRepository extends BaseRepository<CustomerBetweenGroup,Long>{

    /**
     * 根据客户查询用户组
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    public List<CustomerBetweenGroup> findByCustomerId(Long customerId);

    /**
     * 根据用户组查询该组下的用户
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    public List<CustomerBetweenGroup> findByCustomerGroupId(Long customerGroupId);

    /**
     * 根据客户id删除
     * @auther: Hou-jun
     * @date: 2018/7/12
     */
    public void deleteByCustomerId(Long customerId);
}
