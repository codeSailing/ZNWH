package com.quickdone.znwh.controller;

import com.quickdone.znwh.entity.CallCategory;
import com.quickdone.znwh.entity.CallContentSubject;
import com.quickdone.znwh.entity.CallFlowNode;
import com.quickdone.znwh.entity.CustomerCallFlow;
import com.quickdone.znwh.pojo.LayuiRequest;
import com.quickdone.znwh.pojo.LayuiResponseMap;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CallCategoryService;
import com.quickdone.znwh.service.CallContentSubjectService;
import com.quickdone.znwh.service.CallFlowNodeService;
import com.quickdone.znwh.service.CustomerCallFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 每个客户的外呼流程记录,客户任务表
 */
@Controller
@RequestMapping(value = "/customerCallFlow")
public class CustomerCallFlowController {
    private static Logger logger = LoggerFactory.getLogger(CustomerCallFlowController.class);

    @Resource
    private CustomerCallFlowService customerCallFlowService;
    @Resource
    private CallContentSubjectService callContentSubjectService;
    @Resource
    private CallCategoryService callCategoryService;
    @Resource
    private CallFlowNodeService callFlowNodeService;

    @RequestMapping(value = "/showMsg.do", method = RequestMethod.GET)
    public ModelAndView showMsg(@RequestParam(required = false) Long callTaskId){
        logger.info("调用CallTaskController==== list.do");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("customerCallFlow/customerCallFlow");
        List<CallContentSubject> subjectslist = callContentSubjectService.findAll();
        List<CallCategory> callCategories=callCategoryService.findAll();
        mv.addObject("callCategories",callCategories);
        mv.addObject("subjectslist",subjectslist);
        if(callTaskId!=null){
            mv.addObject("callTaskId",callTaskId);
        }
        return mv;
    }


    /**
     * @Author: wxn
     * @Date: 2018/7/14 9:39
     * @Description: 查询任务列表
     */
    @RequestMapping(value = "/data.do", method = RequestMethod.GET)
    @ResponseBody
    public LayuiResponseMap data(HttpServletRequest request) {
        logger.info("调用CallTaskController==== data.do");
        LayuiRequest layuiRequest = LayuiRequest.fromHttpRequest(request);
        Map<String, Object> searchParams = layuiRequest.getSearchParams();
        PaginationMapLayui pagination = PaginationMapLayui.formJQueryLayuiTableRequest(layuiRequest);
        customerCallFlowService.findAll(searchParams,pagination);
        LayuiResponseMap dtResp = LayuiResponseMap.fromPagination(pagination);
        return dtResp;
    }

    /**
     * @Author: wxn
     * @Date: 2018/7/14 9:39
     * @Description: 查询节点内容
     */
    @RequestMapping(value = "/nodalMesg.do", method = RequestMethod.GET)
    @ResponseBody
    public List<CallFlowNode> nodalMesg(String telPhone, Integer taskId) {
       List<CallFlowNode> callFlowNodeList=callFlowNodeService.findByTelephoneAndTaskid(telPhone,taskId);
        return callFlowNodeList;
    }
}
