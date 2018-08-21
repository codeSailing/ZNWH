package com.quickdone.znwh.controller;

import com.quickdone.znwh.entity.CallFlow;
import com.quickdone.znwh.entity.CallTask;
import com.quickdone.znwh.pojo.LayuiRequest;
import com.quickdone.znwh.pojo.LayuiResponseMap;
import com.quickdone.znwh.pojo.PaginationMapLayui;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CallFlowService;
import com.quickdone.znwh.service.CallTaskService;
import com.quickdone.znwh.utils.GetFilePath;
import com.quickdone.znwh.utils.StringUtils;
import com.quickdone.znwh.vo.UserVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhum
 * @Date: 2018/7/11 10:09
 * @Description:
 */
@Controller
@RequestMapping(value = "/callFlow")
public class CallFlowController {

    private static Logger logger = LoggerFactory.getLogger(CallFlowController.class);
    @Resource
    private CallFlowService callFlowService;
    @Resource
    private CallTaskService callTaskService;

    @Value("${XML_PATH}")
    private String XMLPATH;

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:12
     * @Description: 流程列表页面
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public ModelAndView list() {
        logger.info("调用CallFlowController==== list.do");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/callFlow/callFlow");
        return mv;
    }

    /**
     * @Author: zhum
     * @Date: 2018/7/11 9:39
     * @Description: 查询流程列表
     */
    @RequestMapping(value = "/data.do", method = RequestMethod.GET)
    @ResponseBody
    public LayuiResponseMap data(HttpServletRequest request) {
        logger.info("调用CallFlowController==== data.do");
        UserVo userVo = (UserVo) request.getSession(false).getAttribute("userVo");
        LayuiRequest layuiRequest = LayuiRequest.fromHttpRequest(request);
        Map<String, Object> searchParams = layuiRequest.getSearchParams();
        PaginationMapLayui pagination = PaginationMapLayui.formJQueryLayuiTableRequest(layuiRequest);
        callFlowService.findAll(searchParams,pagination);
        LayuiResponseMap dtResp = LayuiResponseMap.fromPagination(pagination);
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        if(dtResp.getData()!=null){
            dataList = (List<Map<String, Object>>) dtResp.getData();
            for (Map<String,Object> map:dataList) {
                if(userVo!=null){
                    map.put("userName",userVo.getAccountName());
                }
            }
        }
        return dtResp;
    }

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:46
     * @Description: 新增
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData add(HttpServletRequest request, String callFlowInfo,String title,String descri) throws IOException {
        logger.info("调用CallFlowController==== add.do,  callFlowInfo:=" + callFlowInfo);

        return callFlowService.add(request,callFlowInfo,title,descri,XMLPATH);
    }

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:12
     * @Description: 流程新增页面
     */
    @RequestMapping(value = "/addFlow.do", method = RequestMethod.GET)
    public ModelAndView addFlow() {
        logger.info("调用CallFlowController==== addFlow.do");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/callFlow/add");
        return mv;
    }

    /**
     * @Author: zhum
     * @Date: 2018/7/11 10:24
     * @Description: 删除
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData delete(String ids) {
        logger.info("调用CallFlowController==== delete.do,  params: id=" + ids);
        ResponseData responseData;
        if (!StringUtils.isNullOrEmpry(ids)) {
            String[] flowIds = ids.split(",");
            List<CallFlow> contentList = new ArrayList<CallFlow>();
            for (String id : flowIds) {
                CallFlow callFlow = (CallFlow) callFlowService.findById(Long.valueOf(id));
                if(callFlow != null){
                        contentList.add(callFlow);
                }
            }
            responseData = callFlowService.delete(contentList);
        } else {
            responseData = ResponseData.getErrorResponse("请选择需要删除的内容");
        }
        return responseData;
    }

    /**
     * @Author: ly
     * @Date: 2018/8/1
     * @Description: 根据ids获取流程是否可以被删除
     */
    @RequestMapping(value = "/getDelInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getDelInfo(String ids) {
        logger.info("调用CallFlowController==== getDelInfo.do,  params: id=" + ids);
        ResponseData responseData;
        if (!StringUtils.isNullOrEmpry(ids)) {
            String[] flowIds = ids.split(",");
            boolean flag = true;
            String notBeDelete = "";
            for (String id : flowIds) {
                CallFlow callFlow = (CallFlow) callFlowService.findById(Long.valueOf(id));
                if(callFlow != null){
                    List<CallTask> callTasks=callTaskService.findByCallFlowId(Long.valueOf(id));
                    if(!callTasks.isEmpty()){
                        flag=false;
                        notBeDelete += callFlow.getName() + ",";
                    }
                }
            }
            if (!flag) {
                String returnMsg="";
                returnMsg += "名称为 " + notBeDelete.substring(0, notBeDelete.length() - 1) + " 的流程已被使用，不允许删除。";
                responseData = ResponseData.getSuccessResponse(returnMsg);
            }else{
                responseData = ResponseData.getErrorResponse("可删除");
            }
        } else {
            responseData = ResponseData.getErrorResponse("请选择需要删除的内容");
        }
        return responseData;
    }


    /**
     * @Author: zhum
     * @Date: 2018/7/10 16:10
     * @Description: 修改
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData update(HttpServletRequest request,@RequestParam Map<String,Object> params) throws IOException {
        logger.info("调用CallFlowController==== update.do,  params: id=" + "id,updateParms:" + params);
        if (params != null) {
            return callFlowService.update(params);
        } else {
            return ResponseData.getErrorResponse("操作失败");
        }
    }

    /**
     * @Author: zhum
     * @Date: 2018/7/10 16:10
     * @Description: 复制
     */
    @RequestMapping(value = "/copy.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData copy(HttpServletRequest request, Long id) {
        logger.info("调用CallFlowController==== copy.do,  params: id=" + "id");
        //TODO 复制
        return callFlowService.copy(id);
    }

    /**
     * @Author: zhum
     * @Date: 2018/7/12 17:32
     * @Description: 获取xml资源文件路径
     */
    @RequestMapping(value = "/getXmlPath.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getXmlPath(HttpServletRequest request) {
        logger.info("调用CallFlowController==== getXmlPath.do");
        List<String> list = GetFilePath.getAllFile(XMLPATH);
        return ResponseData.getSuccessResponse(list);
    }

    @RequestMapping(value = "/tree.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData findTree(){
        ResponseData responseData;
        List<CallFlow> list = callFlowService.findAll();
        if(!list.isEmpty()){
            responseData = ResponseData.getSuccessResponse(list);
        }else{
            responseData = ResponseData.getErrorResponse("暂无主题");
        }
        return responseData;
    }

    /**
     * @Author: zhum
     * @Date: 2018/8/7 9:12
     * @Description: 跳转流程修改页面
     */
    @RequestMapping(value = "/updateFlow.do", method = RequestMethod.GET)
    public ModelAndView updateFlow(Long id){
        CallFlow callFlow = (CallFlow) callFlowService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/callFlow/editFlow");
        modelAndView.addObject("callFlow",callFlow);
        return modelAndView;
    }

    /**
     * @Author: zhum
     * @Date: 2018/8/7 9:12
     * @Description: 跳转流程详情页面
     */
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public ModelAndView detail(Long id){
        CallFlow callFlow = (CallFlow) callFlowService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/callFlow/detail");
        modelAndView.addObject("callFlow",callFlow);
        return modelAndView;
    }
}
