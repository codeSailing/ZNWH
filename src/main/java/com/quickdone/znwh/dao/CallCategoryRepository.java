package com.quickdone.znwh.dao;

import com.quickdone.znwh.entity.CallCategory;

import java.util.List;

/**
 * @Author: zhum
 * @Date: 2018/7/10 15:54
 * @Description:
 */
public interface CallCategoryRepository extends BaseRepository<CallCategory,Long>{
    /**
     * @Author: zhum
     * @Date: 2018/7/10 15:55
     * @Description: 根据id查询
     */
    CallCategory findById(Long id);

    /**
     * @Author: ly
     * @Date: 2018/7/11
     * @Description: 根据name查询
     */
    CallCategory findByName(String name);

    /**
     * @Author: ly
     * @Date: 2018/7/11
     * @Description: 根据parentId查询
     */

    public List<CallCategory> findByParentId(Long id);
}
