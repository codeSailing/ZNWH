package com.quickdone.znwh.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @Auther: Hot-jun
 * @Date: 2018/7/11
 * @Description: 客户表与客户组表进行间接关联
 */
@Entity
@Table(name = "t_customerBetweenGroup")
public class CustomerBetweenGroup implements Serializable {

    private static final long serialVersionUID = 2325913244576166625L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id;

    /**
     *  客户组表主键
     */
    @Column(name = "CUSTOMERGROUP_ID",columnDefinition = "bigint(5) COMMENT '客户组表主键'")
    private Long customerGroupId;

    /**
     *  客户表主键
     */
    @Column(name = "CUSTOMER_ID",columnDefinition = "bigint(5) COMMENT '客户组主键'")
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
