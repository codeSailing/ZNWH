package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.CustomerGroup;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/10
 * @Description:
 */
public interface CustomerGroupService<CustomerGroup,Long> {

    /**
     * 根据用户组id查询
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    public CustomerGroup findById(Long id);

    /**
     * 查询所有的CustomerGroup
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    public List<CustomerGroup> findAll();

    /**
     * 添加用户组
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    public CustomerGroup addCustomerGroup(CustomerGroup customerGroup);

    /**
     * 修改用户组
     * @auther: Hou-jun
     * @date: 2018/7/24
     */
    public CustomerGroup updCustomerGroup(CustomerGroup customerGroup);

    /**
     * 删除用户组
     * @auther: Hou-jun
     * @date: 2018/7/11
     */
    public ResponseData delCustomerGroup(CustomerGroup customerGroup);

    List<CustomerGroup> findByGroupId(Long groupId);

    /**
     * 分页查询用户组
     * @param searchParams
     * @param pagination
     */
    public void pageCustomerGroup(Map<String, Object> searchParams, PaginationMapLayui pagination);
}
