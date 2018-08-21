package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CallContentSubject;
import com.quickdone.znwh.entity.CustomerGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Hot-jun
 * @Date: 2018/7/10
 * @Description:
 */
@Repository
public interface CustomerGroupRepository extends BaseRepository<CustomerGroup,Long> {
    /**
     * @Author: zhum
     * @Date: 2018/7/10 15:51
     * @Description: 根据id查询分组
     */
    CustomerGroup findById(Long id);

    /**
     * 根据层级id查询
     * @param groupId
     * @return
     */
    public List<CustomerGroup> findByGroupId(Long groupId);

}
