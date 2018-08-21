package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CallTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhum
 * @Date: 2018/7/10 14:44
 * @Description:
 */
@Repository
public interface CallTaskRepository extends BaseRepository<CallTask, Long> {
   // CallTask findById(Long id);

    /**
     * @Description: 根据关联的主题id查询任务
     * @Auther: zhuLong
     * @Date: 2018/7/16 10:09
     * @Param: []
     * @return: java.util.List<com.quickdone.znwh.entity.CallTask>
     */
    List<CallTask> findByCallContentSubjectId(Long contentSubjectId);

    //根据类型id查询 wang
    public List<CallTask> findByCallCategoryId(Long callcCategoryId);

    //根据任务名称模糊查询
    List<CallTask> findByNameLike(String name);

    /**
     * 根据用户组id查询
     * @auther: Hou-jun
     * @date: 2018/7/24
     */
    public List<CallTask> findByCustomerGroupId(Long customerGroupId);

    /**
     * @Author: ly
     * @Date: 2018/7/31
     * @Description: 根据流程id查询
     */
    List<CallTask> findByCallFlowId(Long callFlowId);

}
