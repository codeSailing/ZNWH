package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.CallCategory;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ly
 * @Date: 2018/7/11
 * @Description: 任务类别
 */
public interface CallCategoryService<CallCategory, Long> {
    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 添加
     */
    public CallCategory addCallCategory(CallCategory callCategory);

    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 根据id查询
     */
    public CallCategory findById(Long id);

    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 修改
     */
    public ResponseData updateCallCategory(CallCategory callCategory);

    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 删除
     */
    public ResponseData delCallCategory(String callCategoryIds);

    /**
     * @Auther: ly
     * @Date: 2018/7/11
     * @Description: 根据名称查询
     */
    public CallCategory findByName(String name);

    /**
     * @Author: ly
     * @Date: 2018/7/11
     * @Description: 分页查询
     */
    public void findAll(Map<String,Object> searchParams, PaginationMapLayui pagination);

    /**
     * @Author: ly
     * @Date: 2018/7/12
     * @Description: 根据ParentId查询
     */
    public List<CallCategory> findAll();
}
