package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.Customer;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * @Auther: psf
 * @Date: 2018/7/9 21:42
 * @Description:
 */
public interface CustomerService<Customer,Long> {

    /**
     * 分页查询客户
     * @param searchParams
     * @param pagination
     */
    public void pageCustomer(Map<String, Object> searchParams, PaginationMapLayui pagination);

    /**
     * 添加客户
     * @auther: Hou-jun
     * @date: 2018/7/10
     */
    public ResponseData addCustomer(Customer customer);

    /**
     * 根据手机号查询
     * @auther: Hou-jun
     * @date: 2018/7/10
     */
    public Customer findByTelephone(String telephone);

    /**
     * 根据客户id查询
     * @auther: Hou-jun
     * @date: 2018/7/10
     */
    public Customer findById(Long id);

    /**
     * 修改客户信息
     * @auther: Hou-jun
     * @date: 2018/7/10
     */
    public ResponseData updateCustomer(Customer customer);

    /**
     * 删除客户
     * @auther: Hou-jun
     * @date: 2018/7/10
     */
    public ResponseData delCustomer(Customer customer);

    /**
     * 批量导入
     * @auther: Hou-jun
     * @date: 2018/7/25
     */
    public ResponseData saveImportCustomer(List<Map<String, Object>> mapList);
}
