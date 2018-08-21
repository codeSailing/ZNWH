package com.quickdone.znwh.controller;

import com.quickdone.znwh.entity.CallRecord;
import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.service.CallRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: ly
 * @Date: 2018/7/13
 * @Description: 外呼记录
 */
@Controller
@RequestMapping(value = "/callRecord")
public class CallRecordController {
    private static Logger logger = LoggerFactory.getLogger(CallRecordController.class);

    @Resource
    private CallRecordService callRecordService;
    /**
     * @Author: ly
     * @Date: 2018/7/13
     * @Description: 新建外呼记录
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData add(CallRecord callRecord){
        logger.info("CallRecordController==== add.do,  params:" + callRecord.toString());
        callRecordService.addCallRecord(callRecord);
        return ResponseData.getSuccessResponse("保存成功！");
    }
}
