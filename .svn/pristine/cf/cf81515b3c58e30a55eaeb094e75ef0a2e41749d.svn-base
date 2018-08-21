package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/10
 * @Description:
 */
@Repository
public interface CustomerRepository extends BaseRepository<Customer,Long> {

    /**
     * 根据手机号查询
     * @param telephone
     * @return
     */
    public Customer findByTelephone(String telephone);

    /**
     * 根据客户id查询(排除删除)
     * @auther: Hou-jun
     * @date: 2018/7/10
     */
    public Customer findById(Long id);

    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 根据用户组id查询
     */
    public List<Customer> findByCustomerGroupId(String id);

}
