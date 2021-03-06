package com.quickdone.znwh.service;

import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * 内容主题管理
 *
 * @Author: zhuLong
 * @Date: 2018/7/10 15:12
 */
public interface CallContentSubjectService<CallContentSubject, Long> {
    //新增主题
    public CallContentSubject save(CallContentSubject callContentSubject);

    //根据主题名称和父节点id查询
    public CallContentSubject findByNameAndParentId(String name, Long pid);

    //根据主题id查询
    public CallContentSubject findById(Long contentSubjectId);

    //批量删除主题
    public ResponseData delete(String contentSubjectIds);

    //分页查询内容
    public void findData(Map<String, Object> searchParams, PaginationMapLayui pagination);

    //查询所有的主题
    public List<CallContentSubject> findAll();
}
