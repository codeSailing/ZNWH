package com.quickdone.znwh.controller;


import com.quickdone.znwh.entity.OutBoundTaskInfo;
import com.quickdone.znwh.pojo.LayuiRequest;
import com.quickdone.znwh.pojo.LayuiResponseMap;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.OutBoundTaskInfoService;
import com.quickdone.znwh.service.OutBoundTaskRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ly
 * @Date: 2018/8/23
 * @Description:任务客户外呼记录
 */
@Controller
@RequestMapping(value = "/outBoundTaskRecord")
public class OutBoundTaskRecordController {

    private static Logger logger = LoggerFactory.getLogger(OutBoundTaskRecordController.class);

    @Resource
    private OutBoundTaskRecordService outBoundTaskRecordService;

    @Resource
    private OutBoundTaskInfoService outBoundTaskInfoService;

    /**
     * @Author: ly
     * @Date: 2018/8/23
     * @Description: 任务客户外呼记录
     */
    @RequestMapping(value = "/showJsp.do", method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("OutBoundTaskRecordController==== showJsp.do");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("outBoundTaskRecord/list");
        return mv;
    }

    /**
     * @Author: ly
     * @Date: 2018/6/23
     * @Description: 查询任务客户外呼记录
     */
    @RequestMapping(value = "/data.do", method = RequestMethod.GET)
    @ResponseBody
    public LayuiResponseMap data(HttpServletRequest request) {
        logger.info("OutBoundTaskRecordController==== data.do");
        LayuiRequest layuiRequest = LayuiRequest.fromHttpRequest(request);
        Map<String, Object> searchParams = layuiRequest.getSearchParams();
        PaginationMapLayui pagination = PaginationMapLayui.formJQueryLayuiTableRequest(layuiRequest);
        outBoundTaskRecordService.findAll(searchParams,pagination);
        LayuiResponseMap dtResp = LayuiResponseMap.fromPagination(pagination);
        return dtResp;
    }

    /**
     * @Author: ly
     * @Date: 2018/8/24
     * @Description: 查询任务客户外呼记录详情
     */
    @RequestMapping(value = "/getTaskInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getTaskInfo(String recordId) {
        logger.info("OutBoundTaskRecordController==== getTaskInfo.do,  params:" +recordId);
        List<OutBoundTaskInfo> list= outBoundTaskInfoService.getOutBoundTaskInfo(Long.valueOf(recordId));
        List<String> listS=new ArrayList<String>();
        for(OutBoundTaskInfo s:list){
            String info=s.getInfo();
            String aa[]=info.split("\\|");
            for(int i=0;i<aa.length;i++){
                listS.add(aa[i]);
            }
        }
        return ResponseData.getSuccessResponse(listS);
    }
}
