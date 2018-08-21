package com.quickdone.znwh.controller;

import com.iflytek.mt_scylla.mt_scylla;
import com.quickdone.znwh.service.impl.OutcrySemanticservice;
import com.quickdone.znwh.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.File;
import java.io.FileOutputStream;

//
// * Created by dell on 2018/5/14.
//
@Controller
@RequestMapping(value = "outBound")
public class OutcrySemanticsController {
   private static final Logger logger = LoggerFactory
           .getLogger(OutcrySemanticsController.class);

    @Value("${nlp.path}")
    private String nlpAddress;

    @Value("${out.threshold}")
    private String scoreThreshold;

    @Value("${out.ismwebapi}")
    private String ismwebaipAddress;

    @Autowired
    private OutcrySemanticservice outcrySemanticservice;

    @RequestMapping(value = "/outBound.do", method = RequestMethod.GET)
    @ResponseBody
    public String outBound(String content, String iss_uid)
    {
        mt_scylla miat = new mt_scylla();
        int kk = miat.SCYMTInitializeEx(null);
        logger.info("输出初始化结果kk："+kk);
        logger.info("访问地址:/Outbound");
        logger.info( "content(输入框内容)=" + content + ",iss_uid(用户自定义id)=" + iss_uid );
        try {
            String voiceResult = content;
            if (StringUtils.isNullOrEmpry(voiceResult)) {
                throw new Exception("输入文本为空");
            }
            //调用讯飞语义私有云,返回办事指南
            String ruse =  outcrySemanticservice.firstAndNewIdToquery(voiceResult, iss_uid, nlpAddress, scoreThreshold, ismwebaipAddress,miat );
            if(ruse == null){
                ruse="NULL";                                //语义拒识或者分数太低
            }
            return ruse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("外呼系统后台语义接口出现异常");
        }
        return null;
    }


    @RequestMapping(value = "/synthesis.do", method = RequestMethod.GET)
    @ResponseBody
    public String synthesis(String content){
        mt_scylla miat = new mt_scylla();
        int kk = miat.SCYMTInitializeEx(null);
        logger.info("输出初始化结果kk："+kk);
        String wavPath="";
        String fileName="";
        try{
         wavPath=outcrySemanticservice.synthesis(content,nlpAddress,miat);
         String fName = wavPath.trim();
         fileName = fName.substring(fName.lastIndexOf("/")+1);
         /*String fileName = fName.substring(fName.lastIndexOf("\\")+1);*/
        }catch (Exception e){
            e.getMessage();
            logger.error("语音合成接口异常！");
            String string = e.toString();
            logger.error("String", "string = " + string);
            e.printStackTrace();
        }
        return fileName;
    }
    
    /**
     * 语音识别
     *
     * @return
     */
    @RequestMapping(value = "/startDistinguish.do", method = RequestMethod.GET)
    @ResponseBody
    public String StartDistinguish() {
        String result = "";
        // 初始化语音识别引擎
        mt_scylla miat = new mt_scylla();
        int initret = miat.SCYMTInitializeEx(null);
        if (initret != 0) {
            String error = "请检查IP地址是否正确、网络是否正常开启,错误码是" + initret;
            logger.error(error);
        }
        try {
            result = outcrySemanticservice.distinguish(miat);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("语音识别失败");
        }
        return result;
    }
}
