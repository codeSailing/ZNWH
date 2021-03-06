package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.CallFlow;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhum
 * @Date: 2018/7/11 10:12
 * @Description:
 */
public interface CallFlowService<CallFlow,Long> {
    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:18
     * @Description: 查询流程列表
     */
    void findAll(Map<String,Object> searchParams, PaginationMapLayui pagination);

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:27
     * @Description: 根据id查询
     */
    CallFlow findById(Long id);

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:27
     * @Description: 删除
     */
    ResponseData delete(List<CallFlow> callFlow);

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:29
     * @Description: 修改
     */
    ResponseData update(Map<String,Object> params) throws IOException;

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:45
     * @Description: 复制流程
     */
    ResponseData copy(java.lang.Long id);

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:48
     * @Description: 新增
     */
    ResponseData add(HttpServletRequest request, String callFlowInfo, String title, String descri,String XMLPATH,Long subjectId) throws IOException;

    /**
     * @Author: zhum
     * @Date: 2018/7/14 10:23
     * @Description: 查询所有
     */
    List<CallFlow> findAll();
}
