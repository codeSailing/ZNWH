package com.quickdone.znwh.service;

import com.quickdone.znwh.pojo.PaginationMapLayui;

import java.util.List;
import java.util.Map;

/**
 * 内容管理
 *
 * @Author: zhuLong
 * @Date: 2018/7/10 16:05
 */
public interface CallContentService<CallContent, Long> {
    //根据主题id查询内容
    public List<CallContent> findByContentSubjectId(Long contentSubjectId);

    //保存单个内容
    public CallContent save(CallContent callContent);

    //保存多个内容
    public List<CallContent> save(List<CallContent> contentList);

    //删除多个内容
    public void delete(List<CallContent> contentList);

    //根据内容id查询
    public CallContent findById(Long contentId);

    //分页查询内容
    public void findData(Map<String, Object> searchParams, PaginationMapLayui pagination);
}
