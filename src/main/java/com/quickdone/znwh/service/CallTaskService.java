package com.quickdone.znwh.service;

import com.quickdone.znwh.entity.CallTask;
import com.quickdone.znwh.vo.CallTaskVo;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhum
 * @Date: 2018/7/10 14:42
 * @Description:
 */
public interface CallTaskService<CallTask,Long> {

    /**
     * @Author: zhum
     * @Date: 2018/7/10 15:48
     * @Description: 保存
     */
    ResponseData save(CallTaskVo callTaskVo) throws ParseException;

    /**
     * @Author: zhum
     * @Date: 2018/7/10 16:13
     * @Description: 修改任务
     */
    ResponseData update(CallTaskVo callTaskVo) throws ParseException;

    /**
     * @Author: zhum
     * @Date: 2018/7/10 17:04
     * @Description: 根据id查询
     */
    CallTask findById(Long id);

    /**
     * @Author: zhum
     * @Date: 2018/7/10 17:04
     * @Description: 变计划
     */
    ResponseData modifyPlan(CallTask callTask, Integer planType, String executeBeginDate, String executeEndDate, String executeTime) throws ParseException;

    /**
     * @Author: zhum
     * @Date: 2018/7/10 17:05
     * @Description: 选流程
     */
    ResponseData chooseCallFollow(CallTask callTask, Long callFollowId);

    /**
     * @Author: zhum
     * @Date: 2018/7/10 17:05
     * @Description: 启用/禁用
     */
    ResponseData modifyType(List<CallTask> callTaskList, Integer type);

    /**
     * @Author: zhum
     * @Date: 2018/7/10 17:05
     * @Description: 删除
     */
    ResponseData delete(List<CallTask> callTask);

    /**
     * @Author: zhum
     * @Date: 2018/7/10 17:28
     * @Description: 分页查询任务列表
     */
    void findAll(Map<String,Object> searchParams, PaginationMapLayui pagination);

    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 获取任务信息
     */
    ResponseData getCallTaskInfo(CallTask callTask);


    void merge(CallTask callTask);

    /**
     * 根据任务类型外键查询
     */
    public List<CallTask> findByCallCategoryId(Long callcCategoryId);

    /**
     * 根据外呼主题外键查询
     */
    public List<CallTask> findByCallContentSubjectId(Long callContentSubjectId);

    /**
     * @Author: zhum
     * @Date: 2018/7/18 10:28
     * @Description: 保存/修改多个
     */
    List<CallTask> saveMore(List<CallTask> callTaskList);

    public List<CallTask> findByCallFlowId(Long callFlowId);

}
